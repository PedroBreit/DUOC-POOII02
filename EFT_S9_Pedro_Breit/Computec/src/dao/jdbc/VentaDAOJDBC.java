package dao.jdbc;

import conexion.ConexionBD;
import dao.VentaDAO;
import modelo.Venta;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC de las operaciones sobre la tabla VENTA.
 * - La fecha/hora la asigna la BD con NOW().
 */
public class VentaDAOJDBC implements VentaDAO {

    private static final String SQL_INSERT =
        "INSERT INTO VENTA (fecha_hora, id_equipo, rut, precio_final, descuento_aplicado) VALUES (NOW(), ?, ?, ?, ?)";

    private static final String SQL_LISTAR =
        "SELECT id_venta, fecha_hora, id_equipo, rut, precio_final, descuento_aplicado FROM VENTA ORDER BY id_venta DESC";

    private static final String SQL_UPDATE =
        "UPDATE VENTA SET id_equipo=?, rut=?, precio_final=?, descuento_aplicado=? WHERE id_venta=?";

    private static final String SQL_DELETE =
        "DELETE FROM VENTA WHERE id_venta=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT id_venta, fecha_hora, id_equipo, rut, precio_final, descuento_aplicado FROM VENTA WHERE id_venta=?";

    @Override
    public boolean crear(Venta v) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, v.getIdEquipo());
            ps.setString(2, v.getRutCliente());
            ps.setInt(3, v.getPrecioFinal());
            ps.setString(4, v.getDescuentoAplicado());

            if (ps.executeUpdate() != 1) return false;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) v.setIdVenta(rs.getInt(1));
            }
            v.setFechaHora(LocalDateTime.now());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Venta> listarTodas() {
        List<Venta> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean actualizar(Venta v) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE)) {

            ps.setInt(1, v.getIdEquipo());
            ps.setString(2, v.getRutCliente());
            ps.setInt(3,v.getPrecioFinal());
            ps.setString(4, v.getDescuentoAplicado());
            ps.setInt(5, v.getIdVenta());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarPorId(int id) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Venta> buscarPorId(int id) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Convierte una fila de ResultSet en un objeto Venta.
     */
    private Venta mapear(ResultSet rs) throws SQLException {
        Venta v = new Venta();
        v.setIdVenta(rs.getInt("id_venta"));
        v.setIdEquipo(rs.getInt("id_equipo"));
        v.setRutCliente(rs.getString("rut"));
        Timestamp ts = rs.getTimestamp("fecha_hora");
        if (ts != null) v.setFechaHora(ts.toLocalDateTime());
        v.setPrecioFinal(rs.getInt("precio_final"));
        v.setDescuentoAplicado(rs.getString("descuento_aplicado"));
        return v;
    }

}


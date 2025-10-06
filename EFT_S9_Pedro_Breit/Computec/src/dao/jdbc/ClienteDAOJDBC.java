package dao.jdbc;

import conexion.ConexionBD;
import dao.ClienteDAO;
import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC del acceso a datos de Cliente.
 */
public class ClienteDAOJDBC implements ClienteDAO {

    private static final String SQL_INSERT =
        "INSERT INTO CLIENTE (rut, nombre, a_paterno, a_materno, direccion, comuna, correo, telefono) " +
        "VALUES (?,?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE =
        "UPDATE CLIENTE SET nombre=?, a_paterno=?, a_materno=?, direccion=?, comuna=?, correo=?, telefono=? " +
        "WHERE rut=?";

    private static final String SQL_DELETE =
        "DELETE FROM CLIENTE WHERE rut=?";

    private static final String SQL_FIND_BY_RUT =
        "SELECT rut, nombre, a_paterno, a_materno, direccion, comuna, correo, telefono " +
        "FROM CLIENTE WHERE rut=?";

    private static final String SQL_FIND_ALL =
        "SELECT rut, nombre, a_paterno, a_materno, direccion, comuna, correo, telefono FROM CLIENTE";

    @Override
    public boolean crear(Cliente c) {
        
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT)) {

            ps.setString(1, c.getRut());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellidoPaterno());  
            ps.setString(4, c.getApellidoMaterno());  
            ps.setString(5, c.getDireccion());
            ps.setString(6, c.getComuna());
            ps.setString(7, c.getCorreo());
            ps.setInt(8, c.getTelefono());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Cliente c) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidoPaterno());  
            ps.setString(3, c.getApellidoMaterno());  
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getComuna());
            ps.setString(6, c.getCorreo());
            ps.setInt(7, c.getTelefono());
            ps.setString(8, c.getRut());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarPorRut(String rut) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE)) {
            ps.setString(1, rut);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Cliente> buscarPorRut(String rut) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_BY_RUT)) {
            ps.setString(1, rut);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Mapea una fila del ResultSet a un objeto Cliente.o
     */
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setRut(rs.getString("rut"));
        c.setNombre(rs.getString("nombre"));
        c.setApellidoPaterno(rs.getString("a_paterno"));
        c.setApellidoMaterno(rs.getString("a_materno"));
        c.setDireccion(rs.getString("direccion"));
        c.setComuna(rs.getString("comuna"));
        c.setCorreo(rs.getString("correo"));
        c.setTelefono(rs.getInt("telefono"));
        return c;
    }
}

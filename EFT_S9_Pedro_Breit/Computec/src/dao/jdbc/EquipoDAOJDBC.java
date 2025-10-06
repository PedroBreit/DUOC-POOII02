package dao.jdbc;

import conexion.ConexionBD;
import dao.EquipoDAO;
import modelo.Desktop;
import modelo.Equipo;
import modelo.Laptop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO JDBC para la tabla EQUIPO y sus especializaciones (DESKTOP / LAPTOP).
 */
public class EquipoDAOJDBC implements EquipoDAO {

    // SQL
    private static final String SQL_INSERT_EQUIPO =
        "INSERT INTO EQUIPO (modelo, cpu, disco_gb, ram_gb, precio) VALUES (?,?,?,?,?)";

    private static final String SQL_INSERT_LAPTOP =
        "INSERT INTO LAPTOP (id_equipo, tam_pantalla_pulg, es_touch, puertos_usb) VALUES (?,?,?,?)";

    private static final String SQL_INSERT_DESKTOP =
        "INSERT INTO DESKTOP (id_equipo, potencia_fuente_w, placa) VALUES (?,?,?)";

    private static final String SQL_UPDATE_EQUIPO =
        "UPDATE EQUIPO SET modelo=?, cpu=?, disco_gb=?, ram_gb=?, precio=? WHERE id_equipo=?";

    private static final String SQL_UPDATE_LAPTOP =
        "UPDATE LAPTOP SET tam_pantalla_pulg=?, es_touch=?, puertos_usb=? WHERE id_equipo=?";

    private static final String SQL_UPDATE_DESKTOP =
        "UPDATE DESKTOP SET potencia_fuente_w=?, placa=? WHERE id_equipo=?";

    private static final String SQL_FIND_BY_ID =
        "SELECT e.id_equipo, e.modelo, e.cpu, e.disco_gb, e.ram_gb, e.precio, l.tam_pantalla_pulg, l.es_touch, l.puertos_usb, d.potencia_fuente_w, d.placa " +
            "FROM EQUIPO e LEFT JOIN LAPTOP l  ON l.id_equipo = e.id_equipo LEFT JOIN DESKTOP d ON d.id_equipo = e.id_equipo " +
            "WHERE e.id_equipo = ?";

    private static final String SQL_FIND_ALL =
        "SELECT e.id_equipo, e.modelo, e.cpu, e.disco_gb, e.ram_gb, e.precio, l.tam_pantalla_pulg, l.es_touch, l.puertos_usb, d.potencia_fuente_w, d.placa " +
            "FROM EQUIPO e LEFT JOIN LAPTOP l  ON l.id_equipo = e.id_equipo LEFT JOIN DESKTOP d ON d.id_equipo = e.id_equipo " +
            "ORDER BY e.id_equipo";

    private static final String SQL_DELETE =
        "DELETE FROM EQUIPO WHERE id_equipo = ?";

    
    @Override
    public boolean crearDesktop(Desktop d) {
        try (Connection cn = ConexionBD.getInstancia().getConexion()) {
            cn.setAutoCommit(false);

            try (PreparedStatement psEq = cn.prepareStatement(SQL_INSERT_EQUIPO, Statement.RETURN_GENERATED_KEYS)) {
                
                psEq.setString(1, d.getModelo());
                psEq.setString(2, d.getCpu());
                psEq.setInt(3, d.getDiscoGb());   
                psEq.setInt(4, d.getRamGb());
                psEq.setInt(5, d.getPrecio());
                
                if (psEq.executeUpdate() != 1) { cn.rollback(); return false; }

                int idGenerado;
                try (ResultSet rs = psEq.getGeneratedKeys()) {
                    if (!rs.next()) { cn.rollback(); return false; }
                    idGenerado = rs.getInt(1);
                }

                try (PreparedStatement psDx = cn.prepareStatement(SQL_INSERT_DESKTOP)) {
                    psDx.setInt(1, idGenerado);
                    psDx.setInt(2, d.getPotenciaFuenteW());
                    psDx.setString(3, d.getPlaca());
                    if (psDx.executeUpdate() != 1) { cn.rollback(); return false; }
                }

                d.setIdEquipo(idGenerado);
                cn.commit();
                return true;

            } catch (SQLException ex) {
                cn.rollback();
                ex.printStackTrace();
                return false;
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean crearLaptop(Laptop l) {
        try (Connection cn = ConexionBD.getInstancia().getConexion()) {
            cn.setAutoCommit(false);

            try (PreparedStatement psEq = cn.prepareStatement(SQL_INSERT_EQUIPO, Statement.RETURN_GENERATED_KEYS)) {
                
                psEq.setString(1, l.getModelo());
                psEq.setString(2, l.getCpu());
                psEq.setInt(3, l.getDiscoGb());
                psEq.setInt(4, l.getRamGb());
                psEq.setInt(5, l.getPrecio());
                
                if (psEq.executeUpdate() != 1) { cn.rollback(); return false; }

                int idGenerado;
                try (ResultSet rs = psEq.getGeneratedKeys()) {
                    if (!rs.next()) { cn.rollback(); return false; }
                    idGenerado = rs.getInt(1);
                }

                try (PreparedStatement psLp = cn.prepareStatement(SQL_INSERT_LAPTOP)) {
                    psLp.setInt(1, idGenerado);
                    psLp.setBigDecimal(2, java.math.BigDecimal.valueOf(l.getTamPantallaPulg()));
                    psLp.setString(3, l.isEsTouch() ? "S" : "N");
                    psLp.setInt(4, l.getPuertosUsb());
                    if (psLp.executeUpdate() != 1) { cn.rollback(); return false; }
                }

                l.setIdEquipo(idGenerado);
                cn.commit();
                return true;

            } catch (SQLException ex) {
                cn.rollback();
                ex.printStackTrace();
                return false;
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Equipo> buscarPorId(int idEquipo) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, idEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(mapearEquipo(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Equipo> listarTodos() {
        List<Equipo> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Equipo eq = mapearEquipo(rs);
                
                if (eq != null) lista.add(eq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean actualizarDesktop(Desktop d) {
        try (Connection cn = ConexionBD.getInstancia().getConexion()) {
            cn.setAutoCommit(false);

            try (PreparedStatement psE = cn.prepareStatement(SQL_UPDATE_EQUIPO);
                 PreparedStatement psD = cn.prepareStatement(SQL_UPDATE_DESKTOP)) {

                psE.setString(1, d.getModelo());
                psE.setString(2, d.getCpu());
                psE.setInt(3, d.getDiscoGb());
                psE.setInt(4, d.getRamGb());
                psE.setInt(5, d.getPrecio());
                psE.setInt(6, d.getIdEquipo());
                
                if (psE.executeUpdate() != 1) { cn.rollback(); return false; }

                psD.setInt(1, d.getPotenciaFuenteW());
                psD.setString(2, d.getPlaca());
                psD.setInt(3, d.getIdEquipo());
                
                if (psD.executeUpdate() != 1) { cn.rollback(); return false; }

                cn.commit();
                return true;

            } catch (SQLException ex) {
                cn.rollback(); ex.printStackTrace(); return false;
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean actualizarLaptop(Laptop l) {
        try (Connection cn = ConexionBD.getInstancia().getConexion()) {
            cn.setAutoCommit(false);

            try (PreparedStatement psE = cn.prepareStatement(SQL_UPDATE_EQUIPO);
                 PreparedStatement psL = cn.prepareStatement(SQL_UPDATE_LAPTOP)) {

                psE.setString(1, l.getModelo());
                psE.setString(2, l.getCpu());
                psE.setInt(3, l.getDiscoGb());
                psE.setInt(4, l.getRamGb());
                psE.setInt(5, l.getPrecio());
                psE.setInt(6, l.getIdEquipo());
                
                if (psE.executeUpdate() != 1) { cn.rollback(); return false; }

                psL.setBigDecimal(1, java.math.BigDecimal.valueOf(l.getTamPantallaPulg()));
                psL.setString(2, l.isEsTouch() ? "S" : "N");
                psL.setInt(3, l.getPuertosUsb());
                psL.setInt(4, l.getIdEquipo());
                
                if (psL.executeUpdate() != 1) { cn.rollback(); return false; }

                cn.commit();
                return true;

            } catch (SQLException ex) {
                cn.rollback(); ex.printStackTrace(); return false;
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    @Override
    public boolean eliminarPorId(int idEquipo) {
        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, idEquipo);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Convierte la fila resultante (EQUIPO + posible DESKTOP/LAPTOP) a un objeto.
     */
    private Equipo mapearEquipo(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id_equipo");
        String modelo = rs.getString("modelo");
        String cpu = rs.getString("cpu");
        int disco = rs.getInt("disco_gb");
        int ram = rs.getInt("ram_gb");
        int precio = rs.getInt("precio");

        java.math.BigDecimal bdTam = rs.getBigDecimal("tam_pantalla_pulg");
        Integer pot = (Integer) rs.getObject("potencia_fuente_w");

        if (bdTam != null) {
            Laptop l = new Laptop();
            l.setIdEquipo(id);
            l.setModelo(modelo);
            l.setCpu(cpu);
            l.setDiscoGb(disco);
            l.setRamGb(ram);
            l.setPrecio(precio);
            l.setTamPantallaPulg(bdTam.doubleValue());
            String esTouch = rs.getString("es_touch");
            l.setEsTouch("S".equalsIgnoreCase(esTouch));
            l.setPuertosUsb(rs.getInt("puertos_usb"));
            return l;

        } else if (pot != null) {
            Desktop d = new Desktop();
            d.setIdEquipo(id);
            d.setModelo(modelo);
            d.setCpu(cpu);
            d.setDiscoGb(disco);
            d.setRamGb(ram);
            d.setPrecio(precio);
            d.setPotenciaFuenteW(pot);
            d.setPlaca(rs.getString("placa"));
            return d;

        } else {
            Equipo e = new Equipo() {};
            e.setIdEquipo(id);
            e.setModelo(modelo);
            e.setCpu(cpu);
            e.setDiscoGb(disco);
            e.setRamGb(ram);
            e.setPrecio(precio);
            return e;
        }
    }
}


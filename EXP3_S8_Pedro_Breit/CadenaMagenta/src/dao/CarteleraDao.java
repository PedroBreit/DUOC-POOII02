package dao;

import modelo.Cartelera;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad para obtener la conexión con la base de datos.
 */
public class CarteleraDao {

    public boolean crear(Cartelera c) {
        String sql = "INSERT INTO Cartelera (titulo, director, año, duracion, genero) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDirector());
            ps.setInt(3, Integer.parseInt(c.getAnio()));
            ps.setInt(4, c.getDuracion() == null || c.getDuracion().isBlank() ? 0 : Integer.parseInt(c.getDuracion()));
            ps.setString(5, c.getGenero());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error crear(): " + e.getMessage());
            return false;
        }
    }

    public List<Cartelera> listar() {
        List<Cartelera> out = new ArrayList<>();
        String sql = "SELECT id, titulo, director, año, duracion, genero FROM Cartelera ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Cartelera(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getString("año"),
                    rs.getString("duracion"),
                    rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error listar(): " + e.getMessage());
        }
        return out;
    }

    public List<Cartelera> buscarPorNombre(String patron) {
        List<Cartelera> out = new ArrayList<>();
        String sql = "SELECT id, titulo, director, año, duracion, genero FROM Cartelera WHERE titulo LIKE ? ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + patron + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Cartelera(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getString("año"),
                        rs.getString("duracion"),
                        rs.getString("genero")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscarPorNombre(): " + e.getMessage());
        }
        return out;
    }

    public Cartelera obtenerPorId(int id) {
        String sql = "SELECT id, titulo, director, año, duracion, genero FROM Cartelera WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cartelera(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getString("año"),
                        rs.getString("duracion"),
                        rs.getString("genero")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obtenerPorId(): " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Cartelera c) {
        String sql = "UPDATE Cartelera SET titulo=?, director=?, año=?, duracion=?, genero=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setString(1, c.getTitulo());
            ps.setString(2, c.getDirector());
            ps.setInt(3, Integer.parseInt(c.getAnio()));
            ps.setInt(4, c.getDuracion() == null || c.getDuracion().isBlank() ? 0 : Integer.parseInt(c.getDuracion()));
            ps.setString(5, c.getGenero());
            ps.setInt(6, c.getId());
            boolean ok = ps.executeUpdate() == 1;
            conn.commit();
            return ok;
        } catch (SQLException e) {
            System.out.println("Error actualizar(): " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Cartelera WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setInt(1, id);
            boolean ok = ps.executeUpdate() == 1;
            conn.commit();
            return ok;
        } catch (SQLException e) {
            System.out.println("Error eliminar(): " + e.getMessage());
            return false;
        }
    }
}

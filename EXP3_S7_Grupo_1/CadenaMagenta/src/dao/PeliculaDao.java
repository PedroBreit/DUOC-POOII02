package dao;

import modelo.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDao {

    public boolean crear(Pelicula p) {
        String sql = "INSERT INTO Cartelera (titulo, director, año) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);) {

                ps.setString(1, p.getTitulo());
                ps.setString(2, p.getDirector());
                ps.setInt(3, Integer.parseInt(p.getAnio()));

                return ps.executeUpdate() == 1;
            
        } catch (SQLException e) {
            System.out.println("Error crear(): " + e.getMessage());
            return false;
        }
    }

    public List<Pelicula> listar() {
        List<Pelicula> out = new ArrayList();
        String sql = "SELECT id, titulo, director, año FROM Cartelera ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery(sql);
        ) {
            while(rs.next()) {
                out.add(new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getString("año")
                ));
            }
            
        } catch (SQLException e) {
            System.out.println("Error listar(): " + e.getMessage());
        }
        return out;
    }

    public List<Pelicula> buscarPorNombre(String patron) {
        List<Pelicula> out = new ArrayList();
        String sql = "SELECT id, titulo, director, año FROM Cartelera WHERE titulo LIKE ? ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, "%" + patron + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    out.add(new Pelicula(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("director"),
                            rs.getString("año")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error buscarPorNombre(): " + e.getMessage());
        }
        return out;
    }
    
    public Pelicula obtenerPorId(int id){
        String sql = "SELECT id, titulo, director, año FROM Cartelera Where id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1,id);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getString("año")
                    );
                }
            }
        } catch (SQLException e){
            System.out.println("Error obtenerPorId(): " + e.getMessage());
        }
        return null;
    }
    
    public boolean actualizar(Pelicula p){
        String sql = "UPDATE Cartelera SET titulo = ?, director = ?, año = ? WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            conn.setAutoCommit(false);
            
            ps.setString(1, p.getTitulo());
            ps.setString(2, p.getDirector());
            ps.setInt(3, Integer.parseInt(p.getAnio()));
            ps.setInt(4, p.getId());
            
            boolean ok = ps.executeUpdate() == 1;
            conn.commit();
            return ok;
            
        }catch(SQLException e){
            System.out.println("Error actualizar(): " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Cartelera WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);) {
            
                conn.setAutoCommit(false);
                
                ps.setInt(1, id);
                boolean ok = ps.executeUpdate() == 1;
                
                conn.commit();
                return ok;
        } catch (SQLException e){
                System.out.println("Error eliminar(): " + e.getMessage());
                return false;
        }
    }
}

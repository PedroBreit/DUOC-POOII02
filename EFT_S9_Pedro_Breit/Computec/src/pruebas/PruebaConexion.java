package pruebas;

import conexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Prueba simple de conexion a la base de datos.
 */
public class PruebaConexion {

    public static void main(String[] args) {
        System.out.println("=== PRUEBA CONEXION BD ===");
        try (Connection cn = ConexionBD.getInstancia().getConexion()) {
            System.out.println("Intentando abrir conexion...");
            if (cn == null || cn.isClosed()) {
                System.out.println("[FAIL] No se obtuvo conexion");
                return;
            }
            System.out.println("[OK] Conexion abierta");

            System.out.println("Ejecutando SELECT 1 de prueba...");
            try (PreparedStatement ps = cn.prepareStatement("SELECT 1");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("[OK] SELECT 1 funciono. Valor: " + rs.getInt(1));
                } else {
                    System.out.println("[FAIL] SELECT 1 no retorno filas");
                }
            }
        } catch (Exception e) {
            System.out.println("[FAIL] Error de conexion: " + e.getMessage());
        }
        System.out.println("=== FIN PRUEBA CONEXION BD ===");
    }
}

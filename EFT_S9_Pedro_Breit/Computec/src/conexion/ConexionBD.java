package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexión a la base de datos usando el patrón Singleton.
 */
public class ConexionBD {

    // única instancia de esta clase
    private static ConexionBD instancia;

    // la conexión activa a la base de datos
    private Connection conexion;

    // parámetros de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/Computec_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // constructor privado
    private ConexionBD() {}

    /**
     * Entrega la instancia única de la conexión.
     */
    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Devuelve la conexión activa. Si está cerrada o aún no existe, la crea.
     */
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    /**
     * Cierra la conexión si está abierta.
     * Esto es importante para liberar recursos.
     */
    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final java.lang.String URL = "jdbc:mysql://localhost:3306/Cine_DB";
    private static final java.lang.String USER = "root";
    private static final java.lang.String PASS = "";

    public static Connection getConnection() throws SQLException {
        try{
            return DriverManager.getConnection(URL, USER, PASS);
            //System.out.println("Conexion establecida con la base de datos");
        }catch (SQLException ex) {
            System.err.println("Error al conectar a la BD: " + ex.getMessage());
            throw ex;
        }
    }
}
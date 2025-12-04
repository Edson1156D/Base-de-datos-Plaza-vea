/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement; // <-- ¡Necesitas esta!
import java.sql.ResultSet;
/**
 *
 * @author KEVIN
 */
public class Conexion {

    private static final int PORT = 1433;

    private static final String DATABASE = "AlmacenDB"; 
    
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AlmacenDB;encrypt=false;trustServerCertificate=true;";
    
    private static final String USUARIO = "sa"; 
    private static final String CLAVE = "1234"; 

    public static java.sql.Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de SQL Server no encontrado. Asegúrate de tener 'mssql-jdbc.jar'.");
            throw new SQLException("Fallo al cargar el driver: " + e.getMessage());
        }
    }

    /**
     * Cierra los recursos de la base de datos.
     */
    public static void cerrar(ResultSet rs, Statement st, java.sql.Connection conn) {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}
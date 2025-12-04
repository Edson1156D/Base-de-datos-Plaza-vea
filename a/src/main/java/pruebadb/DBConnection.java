
package pruebadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AlmacenDB;encrypt=false;trustServerCertificate=true;";
    private final String USER = "sa";
    private final String PASSWORD = "1234";

    private DBConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver de SQL Server.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a SQL Server: " + e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("Error al reconectar: " + e.getMessage());
        }
        return connection;
    }
}
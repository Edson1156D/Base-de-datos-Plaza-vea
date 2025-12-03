package pruebadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    private final String URL = "jdbc:sqlserver://DESKTOP-G6B8HIA\\KEVIN:1433;databaseName=AlmacenDB;encrypt=false;";
    private final String USER = "sa";
    private final String PASSWORD = "1234"; // tu contraseña

    private DBConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver de SQL Server.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error al conectar a SQL Server:");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

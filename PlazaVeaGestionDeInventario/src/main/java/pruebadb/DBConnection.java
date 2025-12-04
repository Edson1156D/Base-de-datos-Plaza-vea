package pruebadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    // ⚠️ CAMBIA "TuBD" por el nombre REAL de tu base de datos
    private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AlmacenDB;encrypt=false;";
    private final String USER = "sa";
    private final String PASS = "1234";

    private DBConnection() {
        conectar();
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    private void conectar() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✔ Conectado correctamente a SQL Server.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar: " + e.getMessage());
            connection = null;
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.err.println("⚠ La conexión estaba cerrada, intentando reconectar...");
                conectar();
            }
        } catch (SQLException e) {
            System.err.println("⚠ Error detectando estado de conexión, reconectando...");
            conectar();
        }
        return connection;
    }
}

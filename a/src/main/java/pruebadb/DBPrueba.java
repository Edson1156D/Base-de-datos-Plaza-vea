
package pruebadb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBPrueba {

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("✔ Conexión exitosa a la base de datos!");

                // Obtener metadatos
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Servidor: " + meta.getDatabaseProductName());
                System.out.println("Versión: " + meta.getDatabaseProductVersion());
                System.out.println("Usuario conectado: " + meta.getUserName());
                System.out.println("----------------------------------------");

                // Listar tablas de SQL Server
                System.out.println("\n--- Tablas en la Base de Datos AlmacenDB ---");

                // Para SQL Server: schema= "dbo"
                ResultSet tables = meta.getTables(null, "dbo", "%", new String[]{"TABLE"});

                int count = 0;
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("  " + (++count) + ". " + tableName);
                }
                tables.close();

                if (count == 0) {
                    System.out.println("⚠ No se encontraron tablas. Verifica que existan en dbo.");
                } else {
                    System.out.println("\n✅ Total de tablas encontradas: " + count);
                }
            }

        } catch (SQLException e) {
            System.out.println("✖ Error al conectar a la base de datos:");
            e.printStackTrace();
        }
    }
}
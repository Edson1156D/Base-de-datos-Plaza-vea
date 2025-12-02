/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.sun.jdi.connect.spi.Connection;

/**
 *
 * @author KEVIN
 */
public class Conexion {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AlmacenDB;encrypt=false;";
    private static final String USER = "sa";
    private static final String PASS = "123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

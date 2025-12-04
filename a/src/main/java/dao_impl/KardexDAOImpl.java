/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao_impl;

import dao.KardexDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.KardexMovimiento;
import pruebadb.DBConnection;

public class KardexDAOImpl implements KardexDAO {

    @Override
    public List<KardexMovimiento> obtenerKardex(int idProducto) {
        List<KardexMovimiento> lista = new ArrayList<>();

        try {
            Connection con = DBConnection.getInstance().getConnection();
            CallableStatement cs = con.prepareCall("{ call sp_reporteKardex(?) }");
            cs.setInt(1, idProducto);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                KardexMovimiento km = new KardexMovimiento(
                        rs.getString("Movimiento"),
                        rs.getInt("cantidad"),
                        rs.getTimestamp("fecha")
                );
                lista.add(km);
            }

            rs.close();
            cs.close();

        } catch (Exception e) {
            System.out.println("Error en obtenerKardex: " + e.getMessage());
        }

        return lista;
    }
}

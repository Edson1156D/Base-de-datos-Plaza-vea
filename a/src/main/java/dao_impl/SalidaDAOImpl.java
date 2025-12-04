/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao_impl;
import dao.SalidaDAO;
import modelo.Salida;
import modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author KEVIN
 */
public class SalidaDAOImpl implements SalidaDAO {

    @Override
    public boolean registrar(Salida salida) {
        String sql = "INSERT INTO salida (idProducto, cantidad, fecha) VALUES (?, ?, ?)";
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, salida.getIdProducto());
            ps.setInt(2, salida.getCantidad());
            ps.setDate(3, salida.getFecha());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar salida: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Salida> listar() {
        List<Salida> lista = new ArrayList<>();
        String sql = "SELECT * FROM salida";
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Salida s = new Salida();
                s.setIdSalida(rs.getInt("idSalida"));
                s.setIdProducto(rs.getInt("idProducto"));
                s.setCantidad(rs.getInt("cantidad"));
                s.setFecha(rs.getDate("fecha"));
                lista.add(s);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar salidas: " + e.getMessage());
        }
        return lista;
    }
}


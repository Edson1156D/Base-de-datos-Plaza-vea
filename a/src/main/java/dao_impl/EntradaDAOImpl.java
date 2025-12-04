/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao_impl;
import dao.EntradaDAO;
import modelo.Entrada;
import modelo.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntradaDAOImpl implements EntradaDAO {

    private Connection cn;

    public EntradaDAOImpl() {
        try {
            cn = Conexion.obtenerConexion(); // tu método correcto
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar: " + e.getMessage());
        }
    }

    @Override
    public boolean registrar(Entrada entrada) {
        String sql = "INSERT INTO entrada (idProducto, idProveedor, cantidad, fecha) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, entrada.getIdProducto());
            ps.setInt(2, entrada.getIdProveedor());
            ps.setInt(3, entrada.getCantidad());
            ps.setDate(4, entrada.getFecha());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error registrar entrada: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Entrada> listar() {
        List<Entrada> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrada";

        try (Statement st = cn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Entrada e = new Entrada();

                e.setIdEntrada(rs.getInt("idEntrada"));
                e.setIdProducto(rs.getInt("idProducto"));
                e.setIdProveedor(rs.getInt("idProveedor"));
                e.setCantidad(rs.getInt("cantidad"));
                e.setFecha(rs.getDate("fecha"));

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error listar: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Entrada buscarPorId(int id) {
        Entrada entrada = null;

        String sql = "SELECT * FROM entrada WHERE idEntrada = ?";

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                entrada = new Entrada();

                entrada.setIdEntrada(rs.getInt("idEntrada"));
                entrada.setIdProducto(rs.getInt("idProducto"));
                entrada.setIdProveedor(rs.getInt("idProveedor"));
                entrada.setCantidad(rs.getInt("cantidad"));
                entrada.setFecha(rs.getDate("fecha"));
            }

        } catch (SQLException e) {
            System.out.println("Error buscar: " + e.getMessage());
        }

        return entrada;
    }
}
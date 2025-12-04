package dao_Impl;

import dao.TipoProductoDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.TipoProducto;
import pruebadb.DBConnection;

public class TipoProductoDAOImpl implements TipoProductoDAO {

    @Override
    public List<TipoProducto> listar() {
        List<TipoProducto> lista = new ArrayList<>();

        String sql = "SELECT idTipo, nombre FROM TipoProducto";

        try (Connection cn = DBConnection.getInstance().getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoProducto t = new TipoProducto();
                t.setIdTipo(rs.getInt("idTipo"));
                t.setNombre(rs.getString("nombre"));
                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error listar tipos: " + e.getMessage());
        }

        return lista;
    }
}

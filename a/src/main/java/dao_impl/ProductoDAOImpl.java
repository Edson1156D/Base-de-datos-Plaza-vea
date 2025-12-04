package dao_impl;

import dao.ProductoDAO;
import modelo.Producto;
import pruebadb.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public boolean registrarProducto(Producto p) {

        String sql = "INSERT INTO Producto (nombre, descripcion, idTipo, stock, precio, idProveedor, fecha_vencimiento) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdTipo());
            ps.setInt(4, p.getStock());
            ps.setDouble(5, p.getPrecio());
            ps.setInt(6, p.getIdProveedor());
            ps.setDate(7, new java.sql.Date(p.getFechaCaducidad().getTime()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizarProducto(Producto p) {

        String sql = "UPDATE Producto "
                   + "SET nombre=?, descripcion=?, idTipo=?, stock=?, precio=?, idProveedor=?, fecha_vencimiento=? "
                   + "WHERE idProducto=?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getIdTipo());
            ps.setInt(4, p.getStock());
            ps.setDouble(5, p.getPrecio());
            ps.setInt(6, p.getIdProveedor());
            ps.setDate(7, new java.sql.Date(p.getFechaCaducidad().getTime()));
            ps.setInt(8, p.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM Producto WHERE idProducto=?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        String sql = "SELECT * FROM Producto WHERE idProducto=?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapearProducto(rs);

        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto WHERE nombre LIKE ?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) lista.add(mapearProducto(rs));

        } catch (SQLException e) {
            System.err.println("Error al buscar por nombre: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Producto> filtrarPorPrecio(double precio) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto WHERE precio <= ? ORDER BY precio ASC";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, precio);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) lista.add(mapearProducto(rs));

        } catch (SQLException e) {
            System.err.println("Error al filtrar por precio: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Producto> filtrarPorStock() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto ORDER BY stock ASC";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) lista.add(mapearProducto(rs));

        } catch (SQLException e) {
            System.err.println("Error al filtrar por stock: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Producto> filtrarPorTipo(int idTipo) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto WHERE idTipo=?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idTipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) lista.add(mapearProducto(rs));

        } catch (SQLException e) {
            System.err.println("Error al filtrar por tipo: " + e.getMessage());
        }

        return lista;
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();

        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setIdTipo(rs.getInt("idTipo"));
        p.setStock(rs.getInt("stock"));
        p.setPrecio(rs.getDouble("precio"));
        p.setIdProveedor(rs.getInt("idProveedor"));          
        p.setFechaCaducidad(rs.getDate("fecha_vencimiento")); 

        return p;
    }
}

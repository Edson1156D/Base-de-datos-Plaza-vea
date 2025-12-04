package dao_impl;

import dao.ProveedorDAO;
import modelo.Proveedor;
import modelo.TipoProducto;
import pruebadb.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    @Override
    public boolean registrarProveedor(Proveedor p) {
        String sql = "INSERT INTO Proveedor (nombre, ruc, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error registrar proveedor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarProveedor(Proveedor p) {
        String sql = "UPDATE Proveedor SET nombre=?, ruc=?, telefono=?, direccion=? WHERE idProveedor=?";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getRuc());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getDireccion());
            ps.setInt(5, p.getIdProveedor());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarProveedor(int idProveedor) {
        String sql = "DELETE FROM Proveedor WHERE idProveedor=?";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error eliminar proveedor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Proveedor buscarPorId(int idProveedor) {
        String sql = "SELECT * FROM Proveedor WHERE idProveedor=?";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapProveedor(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error buscar proveedor: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapProveedor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error listar proveedores: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Proveedor> buscarPorNombre(String nombre) {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor WHERE nombre LIKE ?";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapProveedor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error buscar por nombre: " + e.getMessage());
        }

        return lista;
    }

    // ===========================================
    // RELACIÓN Proveedor - TipoProducto
    // ===========================================
    @Override
    public boolean asignarTipoAProveedor(int idProveedor, int idTipo) {
        String sql = "INSERT INTO ProveedorProductoTipo (idProveedor, idTipo) VALUES (?, ?)";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ps.setInt(2, idTipo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error asignar tipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarTipoDeProveedor(int idProveedor, int idTipo) {
        String sql = "DELETE FROM ProveedorProductoTipo WHERE idProveedor=? AND idTipo=?";

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ps.setInt(2, idTipo);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error eliminar tipo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<TipoProducto> listarTiposDeProveedor(int idProveedor) {
        List<TipoProducto> lista = new ArrayList<>();

        String sql = """
            SELECT tp.idTipo, tp.nombre
            FROM ProveedorProductoTipo ppt
            INNER JOIN TipoProducto tp ON tp.idTipo = ppt.idTipo
            WHERE ppt.idProveedor = ?
            """;

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TipoProducto t = new TipoProducto();
                t.setIdTipo(rs.getInt("idTipo"));
                t.setNombre(rs.getString("nombre"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Error listar tipos del proveedor: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Proveedor> filtrarPorTipo(int idTipo) {
        List<Proveedor> lista = new ArrayList<>();

        String sql = """
            SELECT p.*
            FROM Proveedor p
            INNER JOIN ProveedorProductoTipo ppt ON p.idProveedor = ppt.idProveedor
            WHERE ppt.idTipo = ?
            """;

        try (Connection con = DBConnection.getInstance().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapProveedor(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error filtrar por tipo: " + e.getMessage());
        }

        return lista;
    }

    private Proveedor mapProveedor(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();

        p.setIdProveedor(rs.getInt("idProveedor"));
        p.setNombre(rs.getString("nombre"));
        p.setRuc(rs.getString("ruc"));
        p.setTelefono(rs.getString("telefono"));
        p.setDireccion(rs.getString("direccion"));

        return p;
    }

    @Override
    public Proveedor buscarPorID(int id) {

        Proveedor p = null;
        String sql = "SELECT * FROM Proveedor WHERE idProveedor = ?";

        try (Connection cn = DBConnection.getInstance().getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Proveedor();
                    p.setIdProveedor(rs.getInt("idProveedor"));
                    p.setNombre(rs.getString("nombre"));
                    p.setRuc(rs.getString("ruc"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setDireccion(rs.getString("direccion"));
                }
            }

        } catch (Exception e) {
            System.out.println("Error buscar proveedor por ID: " + e.getMessage());
        }

        return p;
    }

}

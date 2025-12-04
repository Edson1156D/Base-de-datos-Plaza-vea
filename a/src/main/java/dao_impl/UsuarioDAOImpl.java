package dao_impl;

import dao.UsuarioDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import pruebadb.DBConnection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author KEVIN
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public Usuario buscarPorUsername(String username) {
        String sql = "{CALL sp_buscar_usuario_por_username(?)}";
        Usuario u = null;

        try (Connection conn = DBConnection.getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, username);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                u = mapUsuario(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public boolean insertar(Usuario usuario) {
        String sql = "{CALL sp_insert_usuario(?,?,?,?,?)}";

        try (Connection conn = DBConnection.getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, usuario.getUsername());
            cs.setString(2, usuario.getPasswordHash());
            cs.setString(3, usuario.getRol());
            cs.setString(4, usuario.getNombre());
            cs.setString(5, usuario.getEmail());

            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "{CALL sp_update_usuario_by_username(?,?,?,?,?)}";

        try (Connection conn = DBConnection.getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, usuario.getUsername());
            cs.setString(2, usuario.getRol());
            cs.setString(5, usuario.getEstado());

            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarPorUsuario(String username) {
        String sql = "{CALL sp_eliminar_usuario(?)}";

        try (Connection conn = DBConnection.getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, username);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_usuarios()}";

        try (Connection conn = DBConnection.getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                lista.add(mapUsuario(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    private Usuario mapUsuario(ResultSet rs) throws Exception {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRol(rs.getString("rol"));
        return u;
    }

    @Override
    public boolean actualizarPasswordPorUsername(String username, String passwordHash) {
        String sql = "{CALL sp_update_password_by_username(?,?)}";

        try (Connection conn = DBConnection.getInstance().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, username);
            cs.setString(2, passwordHash);
            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.UsuarioDAO;
import dao_impl.UsuarioDAOImpl;
import java.util.List;
import modelo.Usuario;
import util.HashUtil;

/**
 *
 * @author folli
 */
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    /**
     * Constructor por defecto: crea un UsuarioDAOImpl internamente
     */
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    /**
     * Constructor que recibe un DAO (útil para tests / inyección).
     */
    public UsuarioServiceImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return usuarioDAO.buscarPorUsername(username);
    }

    @Override
    public boolean validarCredenciales(String username, String passwordPlain) {

        Usuario u = usuarioDAO.buscarPorUsername(username);

        if (u == null) {
            return false;
        }

        // Convertir la contraseña ingresada a SHA-256
        String hashIngresado = HashUtil.sha256(passwordPlain);

        // Comparar con el hash almacenado en la BD
        return hashIngresado.toLowerCase().equals(u.getPasswordHash().toLowerCase());

    }

    @Override
    public boolean registrarUsuario(Usuario usuario, String passwordPlain) {
        // Generar hash
        String hash = HashUtil.sha256(passwordPlain);
        usuario.setPasswordHash(hash);

        return usuarioDAO.insertar(usuario);
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.actualizar(usuario);
    }

    @Override
    public boolean eliminarPorUsuario(String usuario) {
        return usuarioDAO.eliminarPorUsuario(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    @Override
    public boolean cambiarPassword(String username, String newPasswordPlain) {
        if (username == null || username.trim().isEmpty() || newPasswordPlain == null || newPasswordPlain.isBlank()) {
            return false;
        }
        String hash = HashUtil.sha256(newPasswordPlain);
        return usuarioDAO.actualizarPasswordPorUsername(username, hash);
    }
}

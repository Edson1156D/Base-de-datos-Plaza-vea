/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import modelo.Usuario;

/**
 *
 * @author folli
 */
public interface UsuarioService {

    Usuario buscarPorUsername(String username);

    /**
     * Valida las credenciales planas (password en texto) contra la contraseña
     * guardada (hash)
     *
     * @return true si coincide
     */
    boolean validarCredenciales(String username, String passwordPlain);

    boolean registrarUsuario(Usuario usuario, String passwordPlain);

    boolean actualizarUsuario(Usuario usuario);

    boolean eliminarPorUsuario(String usuario);
    
    boolean cambiarPassword(String username, String newPasswordPlain);

    List<Usuario> listar();
}

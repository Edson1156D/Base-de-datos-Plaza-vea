/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Usuario;

public interface UsuarioDAO {

    Usuario buscarPorUsername(String username);

    boolean insertar(Usuario usuario);

    boolean actualizar(Usuario usuario);
    
    boolean actualizarPasswordPorUsername(String username, String passwordHash);
    
    List<Usuario> listar();
    
    boolean eliminarPorUsuario(String usuario);
    
}

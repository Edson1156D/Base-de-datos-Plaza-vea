/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Usuario;

/**
 *
 * @author KEVIN
 */

public interface UsuarioDAO {
    Usuario login(String username, String password);
}
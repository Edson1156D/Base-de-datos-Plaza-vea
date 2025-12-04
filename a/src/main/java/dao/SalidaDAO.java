/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Salida;
import java.util.List;

public interface SalidaDAO {
    boolean registrar(Salida salida);
    List<Salida> listar();
}


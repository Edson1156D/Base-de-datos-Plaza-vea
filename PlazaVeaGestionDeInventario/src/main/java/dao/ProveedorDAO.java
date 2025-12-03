/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Proveedor;

public interface ProveedorDAO {

    boolean registrarProveedor(Proveedor proveedor);

    List<Proveedor> listarProveedores();

    boolean asignarTipo(int idProveedor, int idTipo);

    List<Proveedor> filtrarPorTipo(int idTipo);
}

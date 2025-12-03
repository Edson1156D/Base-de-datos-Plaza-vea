/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Producto;

public interface ProductoDAO {

    boolean registrarProducto(Producto p);

    List<Producto> listarProductos();

    List<Producto> filtrarPorTipo(int idTipo);

    Producto buscarPorId(int idProducto);
}

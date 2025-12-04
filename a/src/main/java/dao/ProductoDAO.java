package dao;

import java.util.List;
import modelo.Producto;

public interface ProductoDAO {

    boolean registrarProducto(Producto p);

    List<Producto> listarProductos();

    // --- AGREGA ESTAS DOS LÍNEAS NUEVAS ---
    boolean actualizarProducto(Producto p);
    boolean eliminarProducto(int idProducto);
    // --------------------------------------
List<Producto> buscarPorNombre(String nombre);  // Para btnBuscar
    List<Producto> filtrarPorStock();               // Para F.Stock (Ordena por stock)
    List<Producto> filtrarPorPrecio(double precio); // Para F.Precio (Menores al precio dado)
    List<Producto> filtrarPorTipo(int idTipo);      // Para btnFiltrar
   
    Producto buscarPorId(int idProducto);
}
package controlador;

import modelo.Producto;
import dao.ProductoDAO;
import dao_impl.ProductoDAOImpl;
import java.util.List; // <--- Importante

public class ProductoController {

    private ProductoDAO productoDao;

    public ProductoController() {
        this.productoDao = new ProductoDAOImpl();
    }

    public boolean guardarProducto(Producto p) {
        return productoDao.registrarProducto(p);
    }
    
    // --- AGREGA ESTE MÉTODO NUEVO ---
    public List<Producto> listarProductos() {
        return productoDao.listarProductos();
    }
    public boolean actualizarProducto(Producto p) {
        return productoDao.actualizarProducto(p);
    }

    public boolean eliminarProducto(int id) {
        return productoDao.eliminarProducto(id);
    }
    public List<Producto> buscarPorNombre(String nombre) {
        return productoDao.buscarPorNombre(nombre);
    }
    
    public List<Producto> filtrarPorStock() {
        return productoDao.filtrarPorStock();
    }
    
    public List<Producto> filtrarPorPrecio(double precio) {
        return productoDao.filtrarPorPrecio(precio);
    }
    
    public List<Producto> filtrarPorTipo(int idTipo) {
        return productoDao.filtrarPorTipo(idTipo);
    }
}
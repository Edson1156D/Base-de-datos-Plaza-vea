package dao;

import java.util.List;
import modelo.Proveedor;
import modelo.TipoProducto;

public interface ProveedorDAO {

    boolean registrarProveedor(Proveedor p);

    boolean actualizarProveedor(Proveedor p);

    boolean eliminarProveedor(int idProveedor);

    Proveedor buscarPorId(int idProveedor);

    List<Proveedor> listarProveedores();

    List<Proveedor> buscarPorNombre(String nombre);

    boolean asignarTipoAProveedor(int idProveedor, int idTipo);

    boolean eliminarTipoDeProveedor(int idProveedor, int idTipo);

    List<TipoProducto> listarTiposDeProveedor(int idProveedor);

    List<Proveedor> filtrarPorTipo(int idTipo);
    
    Proveedor buscarPorID(int id);
}

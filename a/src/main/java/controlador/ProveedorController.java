package controlador;

import dao.ProveedorDAO;
import dao_impl.ProveedorDAOImpl;
import java.util.List;
import modelo.Proveedor;
import modelo.TipoProducto;

public class ProveedorController {

    private final ProveedorDAO proveedorDAO;

    public ProveedorController() {
        this.proveedorDAO = new ProveedorDAOImpl();
    }

    public ProveedorController(ProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

    public boolean registrarProveedor(Proveedor p) {
        if (p == null) {
            return false;
        }
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            return false;
        }

        return proveedorDAO.registrarProveedor(p);
    }

    public boolean actualizarProveedor(Proveedor p) {
        if (p == null) {
            return false;
        }
        if (p.getIdProveedor() <= 0) {
            return false;
        }

        return proveedorDAO.actualizarProveedor(p);
    }

    public boolean eliminarProveedor(int idProveedor) {
        if (idProveedor <= 0) {
            return false;
        }

        return proveedorDAO.eliminarProveedor(idProveedor);
    }

    public Proveedor buscarPorId(int idProveedor) {
        if (idProveedor <= 0) {
            return null;
        }

        return proveedorDAO.buscarPorId(idProveedor);
    }

    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listarProveedores();
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        if (nombre == null) {
            nombre = "";
        }
        return proveedorDAO.buscarPorNombre(nombre);
    }

    public List<Proveedor> filtrarPorTipo(int idTipo) {
        return proveedorDAO.filtrarPorTipo(idTipo);
    }

    public boolean asignarTipo(int idProveedor, int idTipo) {
        return proveedorDAO.asignarTipoAProveedor(idProveedor, idTipo);
    }

    public boolean eliminarTipo(int idProveedor, int idTipo) {
        return proveedorDAO.eliminarTipoDeProveedor(idProveedor, idTipo);
    }

    public List<TipoProducto> listarTiposDeProveedor(int idProveedor) {
        return proveedorDAO.listarTiposDeProveedor(idProveedor);
    }

    public Proveedor buscarPorID(int id) {
        return buscarPorID(id);
    }

}

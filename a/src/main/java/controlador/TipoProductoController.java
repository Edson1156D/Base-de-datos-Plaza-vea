package controlador;

import dao.TipoProductoDAO;
import dao_Impl.TipoProductoDAOImpl;
import java.util.List;
import modelo.TipoProducto;

public class TipoProductoController {

    private final TipoProductoDAO dao;

    public TipoProductoController() {
        this.dao = new TipoProductoDAOImpl();
    }

    // Listar todos los tipos de producto
    public List<TipoProducto> listarTipos() {
        return dao.listar();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.KardexDAO;
import dao_impl.KardexDAOImpl;
import java.util.List;
import modelo.KardexMovimiento;

public class KardexController {

    private KardexDAO kardexDAO = new KardexDAOImpl();

    public List<KardexMovimiento> obtenerKardex(int idProducto) {
        return kardexDAO.obtenerKardex(idProducto);
    }
}

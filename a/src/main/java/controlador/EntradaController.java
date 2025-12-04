/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.EntradaDAO;
import dao_impl.EntradaDAOImpl;
import modelo.Entrada;
import java.util.List;
/**
 *
 * @author KEVIN
 */
public class EntradaController {
    private EntradaDAO dao;

    public EntradaController() {
        dao = new EntradaDAOImpl();
    }

    public boolean registrar(Entrada entrada) {
        return dao.registrar(entrada);
    }

    public List<Entrada> listar() {
        return dao.listar();
    }

    public Entrada buscar(int id) {
        return dao.buscarPorId(id);
    }
}

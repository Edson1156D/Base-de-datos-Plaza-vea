package dao_impl;

import dao.ReporteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Reporte;
import pruebadb.DBConnection;

public class ReporteDAOImpl implements ReporteDAO {

    @Override
    public boolean insertar(Reporte r) {
        String sql = "INSERT INTO Reportes (idProducto, fechaInicio, fechaFin) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, r.getIdProducto());
            ps.setString(2, r.getFechaInicio());
            ps.setString(3, r.getFechaFin());

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (Exception e) {
            System.out.println("Error insertando reporte: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Reporte> listar() {
        List<Reporte> lista = new ArrayList<>();

        String sql = "SELECT * FROM Reportes ORDER BY idReporte DESC";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reporte r = new Reporte();
                r.setIdReporte(rs.getInt("idReporte"));
                r.setIdProducto(rs.getInt("idProducto"));
                r.setFechaInicio(rs.getString("fechaInicio"));
                r.setFechaFin(rs.getString("fechaFin"));
                lista.add(r);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error listando reportes: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Reporte> listarPorFechas(String inicio, String fin) {
        List<Reporte> lista = new ArrayList<>();

        String sql = "SELECT * FROM Reportes WHERE fechaInicio >= ? AND fechaFin <= ? ORDER BY idReporte DESC";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, inicio);
            ps.setString(2, fin);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reporte r = new Reporte();
                r.setIdReporte(rs.getInt("idReporte"));
                r.setIdProducto(rs.getInt("idProducto"));
                r.setFechaInicio(rs.getString("fechaInicio"));
                r.setFechaFin(rs.getString("fechaFin"));
                lista.add(r);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error listando por fechas: " + e.getMessage());
        }

        return lista;
    }

}

package modelo;

import java.util.Date;

public class Salida {

    private int idSalida;
    private int idProducto;
    private int cantidad;
    private Date fecha;

    public Salida() {
    }

    public Salida(int idSalida, int idProducto, int cantidad, Date fecha) {
        this.idSalida = idSalida;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

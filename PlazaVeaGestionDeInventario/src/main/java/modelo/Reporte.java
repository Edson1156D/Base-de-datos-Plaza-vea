/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author KEVIN
 */
public class Reporte {

        private int idReporte;
        private int idProducto;
        private String fechaInicio;
        private String fechaFin;

        public Reporte() {
        }

        public Reporte(int idProducto, String fechaInicio, String fechaFin) {
            this.idProducto = idProducto;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

        public int getIdReporte() {
            return idReporte;
        }

        public void setIdReporte(int idReporte) {
            this.idReporte = idReporte;
        }

        public int getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(int idProducto) {
            this.idProducto = idProducto;
        }

        public String getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getFechaFin() {
            return fechaFin;
        }

        public void setFechaFin(String fechaFin) {
            this.fechaFin = fechaFin;
        }
    }


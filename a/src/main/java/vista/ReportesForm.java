/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import dao.KardexDAO;
import dao.ReporteDAO;
import dao_impl.KardexDAOImpl;
import dao_impl.ReporteDAOImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.KardexMovimiento;
import modelo.Reporte;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LAB-USR-LNORTE
 */
public class ReportesForm extends javax.swing.JFrame {

    /**
     * Creates new form ReportesForm
     */
    public ReportesForm() {
        initComponents();
    }

    private void limpiar() {
        // Limpia los campos
        jTextField1.setText(""); // ID Producto
        jTextField2.setText(""); // Fecha Inicio
        jTextField3.setText(""); // Fecha Fin

        // Limpia la tabla de reportes
        javax.swing.table.DefaultTableModel model
                = (javax.swing.table.DefaultTableModel) TablaReportes.getModel();

        model.setRowCount(0);  // Borra todas las filas
    }

    private void listarKardex() {

        String idTexto = jTextField1.getText().trim();

        if (idTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID de producto.");
            return;
        }

        int idProducto = Integer.parseInt(idTexto);

        KardexDAO dao = new KardexDAOImpl();
        List<KardexMovimiento> lista = dao.obtenerKardex(idProducto);

        // Modelo de tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Movimiento");
        model.addColumn("Cantidad");
        model.addColumn("Fecha");

        for (KardexMovimiento k : lista) {
            model.addRow(new Object[]{
                k.getMovimiento(),
                k.getCantidad(),
                k.getFecha()
            });
        }

        TablaReportes.setModel(model);
    }

    private void exportarExcel() {

        if (TablaReportes.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para exportar.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte en Excel");
        fileChooser.setSelectedFile(new File("ReporteKardex.xlsx"));

        int result = fileChooser.showSaveDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Reporte");

            // Encabezados
            Row header = sheet.createRow(0);
            for (int i = 0; i < TablaReportes.getColumnCount(); i++) {
                header.createCell(i).setCellValue(TablaReportes.getColumnName(i));
            }

            // Filas de datos
            for (int row = 0; row < TablaReportes.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);

                for (int col = 0; col < TablaReportes.getColumnCount(); col++) {
                    Object data = TablaReportes.getValueAt(row, col);
                    excelRow.createCell(col).setCellValue(data == null ? "" : data.toString());
                }
            }

            // Ajustar tamaño
            for (int i = 0; i < TablaReportes.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();

            JOptionPane.showMessageDialog(this, "Archivo Excel generado:\n" + filePath);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage());
        }
    }

    private void agregarReporte() {

        String idProductoText = jTextField1.getText().trim();
        String fechaInicio = jTextField2.getText().trim();
        String fechaFin = jTextField3.getText().trim();

        if (idProductoText.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        try {
            int idProducto = Integer.parseInt(idProductoText);

            Reporte r = new Reporte(idProducto, fechaInicio, fechaFin);

            ReporteDAO dao = new ReporteDAOImpl();

            if (dao.insertar(r)) {
                JOptionPane.showMessageDialog(this, "Reporte agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el reporte.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID Producto debe ser un número.");
        }
    }

    private void listarReportes() {
        try {
            ReporteDAO dao = new ReporteDAOImpl();
            java.util.List<Reporte> lista = dao.listar();

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
            model.addColumn("ID Reporte");
            model.addColumn("ID Producto");
            model.addColumn("Fecha Inicio");
            model.addColumn("Fecha Fin");
            

            for (Reporte r : lista) {
                model.addRow(new Object[]{
                    r.getIdReporte(),
                    r.getIdProducto(),
                    r.getFechaInicio(),
                    r.getFechaFin(),
                });
            }

            TablaReportes.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al listar reportes: " + e.getMessage());
        }
    }

    private void listarReportesPorFechas() {

        String inicio = jTextField2.getText().trim();
        String fin = jTextField3.getText().trim();

        if (inicio.isEmpty() || fin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese fecha inicio y fecha fin.");
            return;
        }

        ReporteDAO dao = new ReporteDAOImpl();
        List<Reporte> lista = dao.listarPorFechas(inicio, fin);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Reporte");
        model.addColumn("ID Producto");
        model.addColumn("Fecha Inicio");
        model.addColumn("Fecha Fin");
        
        
        
        model.addColumn("Proovedor");

        for (Reporte r : lista) {
            model.addRow(new Object[]{
                r.getIdReporte(),
                r.getIdProducto(),
                r.getFechaInicio(),
                r.getFechaFin()
            });
        }

        TablaReportes.setModel(model);

    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TablaEntradas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        btnAgregarReporte = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaReportes = new javax.swing.JTable();
        btnGenerarReporte = new javax.swing.JButton();
        btnListarReportes = new javax.swing.JButton();
        btnFiltrarFechas = new javax.swing.JButton();

        TablaEntradas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TablaEntradas);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel1.setText("Modulo de gestion de Reportes");

        jLabel7.setText("ID Producto:");

        jLabel8.setText("Fecha Inicio:");

        jLabel9.setText("Fecha fin: ");

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnListar.setText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnAgregarReporte.setText("AgregarReporte");
        btnAgregarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarReporteActionPerformed(evt);
            }
        });

        TablaReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(TablaReportes);

        btnGenerarReporte.setText("Generar Reporte");
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporteActionPerformed(evt);
            }
        });

        btnListarReportes.setText("Listar Reportes");
        btnListarReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarReportesActionPerformed(evt);
            }
        });

        btnFiltrarFechas.setText("Filtrar por fechas");
        btnFiltrarFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarFechasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(btnAgregarReporte)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListar)
                        .addGap(112, 112, 112))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(btnGenerarReporte)
                        .addGap(18, 18, 18)
                        .addComponent(btnListarReportes)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(177, 177, 177)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnFiltrarFechas)
                        .addGap(134, 134, 134))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnFiltrarFechas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiar)
                            .addComponent(btnListar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGenerarReporte)
                            .addComponent(btnListarReportes)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarReporte)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarReporteActionPerformed
        agregarReporte();
    }//GEN-LAST:event_btnAgregarReporteActionPerformed

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        exportarExcel();
    }//GEN-LAST:event_btnGenerarReporteActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        listarKardex();
    }//GEN-LAST:event_btnListarActionPerformed

    private void btnListarReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarReportesActionPerformed
        listarReportes();
    }//GEN-LAST:event_btnListarReportesActionPerformed

    private void btnFiltrarFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarFechasActionPerformed
       listarReportesPorFechas();
    }//GEN-LAST:event_btnFiltrarFechasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaEntradas;
    private javax.swing.JTable TablaReportes;
    private javax.swing.JButton btnAgregarReporte;
    private javax.swing.JButton btnFiltrarFechas;
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnListarReportes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

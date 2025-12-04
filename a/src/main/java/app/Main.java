/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import vista.ProductosForm; // 

public class Main {

    public static void main(String[] args) {
        // Esta instrucción inicia la ventana de manera segura
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crea una nueva instancia de tu formulario y la hace visible
                new ProductosForm().setVisible(true);
            }
        });
    }

}
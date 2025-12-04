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
package vista;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la app. Levanta la ventana principal.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}

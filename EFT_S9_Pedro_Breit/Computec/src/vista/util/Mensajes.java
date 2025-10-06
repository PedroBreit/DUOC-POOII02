package vista.util;

import javax.swing.*;

/**
 * Utilidad para mostrar mensajes al usuario.
 * 
 * Métodos estáticos sencillos para:
 * - Info (azul)
 * - Error (rojo)
 * - Confirmación (sí/no)
 * - Advertencia (amarillo)
 */
public class Mensajes {

    /**
     * Muestra un mensaje de información.
     */
    public static void info(String m) {
        JOptionPane.showMessageDialog(null, m, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de error.
     */
    public static void error(String m) {
        JOptionPane.showMessageDialog(null, m, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de advertencia.
     */
    public static void advertencia(String m) {
        JOptionPane.showMessageDialog(null, m, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un diálogo de confirmación con opciones Sí / No.
     */
    public static boolean confirmar(String m) {
        return JOptionPane.showConfirmDialog(
                null, m, "Confirmar", JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }
}

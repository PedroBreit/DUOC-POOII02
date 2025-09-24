package vista.Dialogo;

import javax.swing.*;

public class Dialogos {
    public static void info(String m) { JOptionPane.showMessageDialog(null, m, "Información", JOptionPane.INFORMATION_MESSAGE); }
    public static void warn(String m) { JOptionPane.showMessageDialog(null, m, "Atención", JOptionPane.WARNING_MESSAGE); }
    public static void error(String m) { JOptionPane.showMessageDialog(null, m, "Error", JOptionPane.ERROR_MESSAGE); }

    public static boolean confirmar(String titulo, String detalle) {
        int r = JOptionPane.showConfirmDialog(null, detalle, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        return r == JOptionPane.OK_OPTION;
    }
}

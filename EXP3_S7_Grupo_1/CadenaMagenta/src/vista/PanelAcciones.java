package vista;

import javax.swing.*;
import java.awt.*;

public class PanelAcciones extends JPanel {
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnModificar = new JButton("Modificar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    public PanelAcciones() {
        super(new FlowLayout(FlowLayout.LEFT, 14, 10));
        setBorder(BorderFactory.createEmptyBorder(6, 12, 12, 12));

        for (JButton b : new JButton[]{btnAgregar, btnModificar, btnEliminar, btnLimpiar}) {
            b.setMargin(new Insets(6, 14, 6, 14));
            b.setIconTextGap(8);
        }

        add(btnAgregar);
        add(btnModificar);
        add(btnEliminar);
        add(btnLimpiar);
    }

    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnModificar() { return btnModificar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
}

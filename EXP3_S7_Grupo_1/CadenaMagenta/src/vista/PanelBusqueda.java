package vista;

import javax.swing.*;
import java.awt.*;

public class PanelBusqueda extends JPanel {
    private final JTextField txtCriterio = new JTextField(30);
    private final JRadioButton rbId = new JRadioButton("ID");
    private final JRadioButton rbTitulo = new JRadioButton("Título", true);
    private final JButton btnBuscar = new JButton("Buscar");

    public PanelBusqueda() {
        super(new FlowLayout(FlowLayout.LEFT, 16, 10));
        setBorder(BorderFactory.createEmptyBorder(8, 12, 4, 12));

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbId);
        grupo.add(rbTitulo);

        txtCriterio.setMargin(new Insets(4, 8, 4, 8));
        btnBuscar.setMargin(new Insets(6, 12, 6, 12));
        rbId.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        rbTitulo.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));

        add(new JLabel("ID o Título:"));
        add(txtCriterio);
        add(rbId);
        add(rbTitulo);
        add(btnBuscar);
    }

    public JTextField getTxtCriterio() { return txtCriterio; }
    public JRadioButton getRbId() { return rbId; }
    public JRadioButton getRbTitulo() { return rbTitulo; }
    public JButton getBtnBuscar() { return btnBuscar; }
}

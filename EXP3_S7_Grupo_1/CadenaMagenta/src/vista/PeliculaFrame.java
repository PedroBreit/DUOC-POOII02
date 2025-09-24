package vista;

import controlador.PeliculaControlador;

import javax.swing.*;
import java.awt.*;

public class PeliculaFrame extends JFrame {

    private final PanelBusqueda panelBusqueda = new PanelBusqueda();
    private final PanelTabla panelTabla = new PanelTabla();
    private final PanelAcciones panelAcciones = new PanelAcciones();

    public PeliculaFrame() {
        setTitle("Cartelera Cine Magenta - S7");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(panelBusqueda, BorderLayout.NORTH);
        root.add(panelTabla, BorderLayout.CENTER);
        root.add(panelAcciones, BorderLayout.SOUTH);
        setContentPane(root);

        new PeliculaControlador(this); 
    }

    public PanelBusqueda getPanelBusqueda() { return panelBusqueda; }
    public PanelTabla getPanelTabla() { return panelTabla; }
    public PanelAcciones getPanelAcciones() { return panelAcciones; }
}

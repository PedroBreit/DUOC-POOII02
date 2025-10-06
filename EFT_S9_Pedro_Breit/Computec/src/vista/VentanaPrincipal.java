package vista;

import vista.paneles.PanelClientes;
import vista.paneles.PanelEquipos;
import vista.paneles.PanelReportes;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal con pestañas para Clientes, Equipos, Ventas y Reportes.
 */
public class VentanaPrincipal extends JFrame {
    
    private final PanelReportes panelReportes = new PanelReportes();
    private final PanelEquipos panelEquipos = new PanelEquipos(() -> panelReportes.recargar());

    public VentanaPrincipal() {
        super("Computec – Ventas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Clientes", new PanelClientes());
        tabs.addTab("Venta de equipos", panelEquipos);
        tabs.addTab("Registros", panelReportes);

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }
}

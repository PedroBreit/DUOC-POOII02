package vista;

import modelo.Pelicula;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class PanelTabla extends JPanel {

    private final JTable tabla = new JTable();
    private final PeliculaTableModel modelo = new PeliculaTableModel();

    public PanelTabla() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));

        tabla.setModel(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(225, 225, 225));
        tabla.setIntercellSpacing(new Dimension(1, 1));
        tabla.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabla);
        
        Border sombra = BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210,210,210), 1, true),
                BorderFactory.createEmptyBorder(4,4,4,4)
        );
        scroll.setBorder(sombra);
        
        add(scroll, BorderLayout.CENTER);
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        modelo.setDatos(peliculas);
    }

    public Pelicula getSeleccion() {
        int i = tabla.getSelectedRow();
        if (i < 0) return null;
        return modelo.getPeliculaAt(i);
    }

    public JTable getTabla() { return tabla; }
    public PeliculaTableModel getModelo() { return modelo; }
}

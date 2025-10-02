package vista;

import modelo.Cartelera;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CarteleraTableModel extends AbstractTableModel {
    private final String[] columnas = {"ID","Título","Director","Año","Duración","Género"};
    private List<Cartelera> datos = new ArrayList<>();

    public void setDatos(List<Cartelera> nuevas) {
        this.datos = (nuevas != null) ? nuevas : new ArrayList<>();
        fireTableDataChanged();
    }

    public Cartelera getPeliculaAt(int fila) {
        if (fila < 0 || fila >= datos.size()) return null;
        return datos.get(fila);
    }

    @Override public int getRowCount() { return datos.size(); }
    @Override public int getColumnCount() { return columnas.length; }
    @Override public String getColumnName(int c) { return columnas[c]; }
    @Override public boolean isCellEditable(int r, int c) { return false; }

    @Override
    public Object getValueAt(int fila, int col) {
        Cartelera x = datos.get(fila);
        return switch (col) {
            case 0 -> x.getId();
            case 1 -> x.getTitulo();
            case 2 -> x.getDirector();
            case 3 -> x.getAnio();
            case 4 -> x.getDuracion();
            case 5 -> x.getGenero();
            default -> "";
        };
    }
}

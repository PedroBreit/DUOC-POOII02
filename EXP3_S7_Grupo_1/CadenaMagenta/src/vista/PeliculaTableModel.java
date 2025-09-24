package vista;

import modelo.Pelicula;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PeliculaTableModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Título", "Director", "Año"};
    private List<Pelicula> datos = new ArrayList<>();

    public void setDatos(List<Pelicula> nuevas) {
        this.datos = nuevas != null ? nuevas : new ArrayList<>();
        fireTableDataChanged();
    }

    public Pelicula getPeliculaAt(int fila) {
        if (fila < 0 || fila >= datos.size()) return null;
        return datos.get(fila);
    }

    @Override public int getRowCount() { return datos.size(); }
    @Override public int getColumnCount() { return columnas.length; }
    @Override public String getColumnName(int column) { return columnas[column]; }
    @Override public boolean isCellEditable(int row, int column) { return false; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pelicula p = datos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getId();
            case 1 -> p.getTitulo();
            case 2 -> p.getDirector();
            case 3 -> p.getAnio();
            default -> "";
        };
    }
}

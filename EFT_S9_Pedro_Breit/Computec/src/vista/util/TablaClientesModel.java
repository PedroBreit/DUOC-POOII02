package vista.util;

import modelo.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Modelo de tabla para mostrar clientes en JTable.
 * 
 * Columnas: RUT, Nombre, Apellido Paterno, Apellido Materno,
 * Dirección, Comuna, Correo y Teléfono.
 */
public class TablaClientesModel extends AbstractTableModel {

    private final String[] cols = {
            "RUT", "Nombre", "Apellido Paterno", "Apellido Materno",
            "Dirección", "Comuna", "Correo", "Teléfono"
    };
    private List<Cliente> datos;

    public TablaClientesModel(List<Cliente> datos) {
        this.datos = datos;
    }

    public void setDatos(List<Cliente> datos) {
        this.datos = datos;
        fireTableDataChanged(); // refresca la tabla al actualizar datos
    }

    public Cliente getClienteAt(int row) {
        return datos.get(row);
    }

    @Override
    public int getRowCount() {
        return datos == null ? 0 : datos.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        Cliente x = datos.get(r);
        return switch (c) {
            case 0 -> x.getRut();
            case 1 -> x.getNombre();
            case 2 -> x.getApellidoPaterno();
            case 3 -> x.getApellidoMaterno();
            case 4 -> x.getDireccion();
            case 5 -> x.getComuna();
            case 6 -> x.getCorreo();
            case 7 -> x.getTelefono();
            default -> "";
        };
    }
}

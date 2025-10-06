package vista.paneles;

import dao.EquipoDAO;
import dao.jdbc.EquipoDAOJDBC;
import modelo.Desktop;
import modelo.Equipo;
import modelo.Laptop;
import vista.util.Mensajes;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PanelEquipos extends JPanel {
    
    private final Runnable refrescarReportes;

    // Filtros
    private final JComboBox<String> cbFiltroTipo = new JComboBox<>(new String[]{"TODOS","DESKTOP","LAPTOP"});
    private final JTextField txtBuscar = new JTextField(16);
    private final JTextField txtPrecioMin = new JTextField(6);
    private final JTextField txtPrecioMax = new JTextField(6);
    private final JButton btnLimpiarFiltros = new JButton("Limpiar filtros");

    // Tabla
    private final JTable tabla = new JTable();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{
                    "ID","Tipo","Modelo","CPU","Disco(GB)","RAM(GB)","Precio","Pantalla/Watts","Touch/USB o Placa"
            }, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
        @Override public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) { case 0,4,5,6 -> Integer.class; default -> String.class; };
        }
    };
    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);

    // Acciones
    private final JButton btnAgregar = new JButton("Agregar equipo");
    private final JButton btnEliminar = new JButton("Eliminar seleccionado");
    private final JButton btnNuevaVenta = new JButton("Nueva venta");

    private final EquipoDAO dao = new EquipoDAOJDBC();

    public PanelEquipos(Runnable refrescarReportes) {
        this.refrescarReportes = refrescarReportes;
        setLayout(new BorderLayout(8,8));

        // Barra superior: filtros
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        filtros.add(new JLabel("Tipo:"));
        filtros.add(cbFiltroTipo);
        filtros.add(new JLabel("Buscar (Modelo/CPU):"));
        filtros.add(txtBuscar);
        filtros.add(new JLabel("Precio:"));
        filtros.add(txtPrecioMin);
        filtros.add(new JLabel("a"));
        filtros.add(txtPrecioMax);
        filtros.add(btnLimpiarFiltros);

        // Barra de acciones
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        acciones.add(btnAgregar);
        acciones.add(btnEliminar);
        acciones.add(btnNuevaVenta);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(filtros, BorderLayout.NORTH);
        norte.add(acciones, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // Tabla
        tabla.setModel(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowSorter(sorter);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Acciones
        btnAgregar.addActionListener(e -> abrirDialogoAgregar());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnNuevaVenta.addActionListener(e -> abrirDialogoVenta());
        cbFiltroTipo.addActionListener(e -> aplicarFiltros());
        btnLimpiarFiltros.addActionListener(e -> limpiarFiltros());

        // Filtrado para el texto
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltros(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltros(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltros(); }
        });
        // Filtrado cuando cambian los precios
        DocumentListener dlPrecio = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { aplicarFiltros(); }
            public void removeUpdate(DocumentEvent e) { aplicarFiltros(); }
            public void changedUpdate(DocumentEvent e) { aplicarFiltros(); }
        };
        txtPrecioMin.getDocument().addDocumentListener(dlPrecio);
        txtPrecioMax.getDocument().addDocumentListener(dlPrecio);

        cargarTabla();
        aplicarFiltros();
    }

    private void abrirDialogoAgregar() {
        DialogEquipoNuevo dlg = new DialogEquipoNuevo(SwingUtilities.getWindowAncestor(this), resultado -> {
            if (resultado) { cargarTabla(); aplicarFiltros(); }
        });
        dlg.setVisible(true);
    }

    private void abrirDialogoVenta() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { Mensajes.error("Selecciona un equipo de la tabla."); return; }
        int viewRow = tabla.convertRowIndexToModel(fila);
        int id = (int) modelo.getValueAt(viewRow, 0);

        // Tomamos los datos visibles
        String tipo = String.valueOf(modelo.getValueAt(viewRow, 1));
        String mod  = String.valueOf(modelo.getValueAt(viewRow, 2));
        String cpu  = String.valueOf(modelo.getValueAt(viewRow, 3));
        int discoGb = (int) modelo.getValueAt(viewRow, 4);
        int ram = (int) modelo.getValueAt(viewRow, 5);
        int precio = (int) modelo.getValueAt(viewRow, 6);
        Object extra1 = modelo.getValueAt(viewRow, 7);
        Object extra2 = modelo.getValueAt(viewRow, 8);

        DialogNuevaVenta dlg = new DialogNuevaVenta(
                SwingUtilities.getWindowAncestor(this),
                id, tipo, mod, cpu, discoGb, ram, precio,
                String.valueOf(extra1), String.valueOf(extra2),
                ok -> { 
                    if (ok) {
                        Mensajes.info("Venta registrada correctamente.");
                        cargarTabla();
                        aplicarFiltros();
                        if (refrescarReportes != null) refrescarReportes.run();
                    }
                
                }
        );
        dlg.setVisible(true);
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { Mensajes.error("Selecciona un equipo en la tabla."); return; }
        int viewRow = tabla.convertRowIndexToModel(fila);
        int id = (int) modelo.getValueAt(viewRow, 0);
        if (!Mensajes.confirmar("¿Eliminar equipo id " + id + "?")) return;
        boolean ok = dao.eliminarPorId(id);
        if (ok) { Mensajes.info("Equipo eliminado."); cargarTabla(); aplicarFiltros(); }
        else Mensajes.error("No se pudo eliminar.");
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Equipo> lista = dao.listarTodos();
        for (Equipo e : lista) {
            String tipo = (e instanceof Laptop) ? "LAPTOP" : (e instanceof Desktop) ? "DESKTOP" : "EQUIPO";

            // Columnas dinámicas (cambian según tipo):
            // - Col 7: Laptop = pulgadas pantalla | Desktop = Watts fuente
            // - Col 8: Laptop = "Touch/USB:n"     | Desktop = Placa
            Object col7 = "", col8 = "";

            if (e instanceof Laptop l) {
                col7 = l.getTamPantallaPulg();
                col8 = (l.isEsTouch() ? "Touch" : "No touch") + " / USB:" + l.getPuertosUsb();
            } else if (e instanceof Desktop d) {
                col7 = d.getPotenciaFuenteW();
                col8 = d.getPlaca();
            }

            modelo.addRow(new Object[]{
                    e.getIdEquipo(), tipo, e.getModelo(), e.getCpu(),
                    e.getDiscoGb(),
                    e.getRamGb(), e.getPrecio(),
                    col7, col8
            });
        }
    }

    private void limpiarFiltros() {
        cbFiltroTipo.setSelectedIndex(0);
        txtBuscar.setText("");
        txtPrecioMin.setText("");
        txtPrecioMax.setText("");
        aplicarFiltros();
    }

    private void aplicarFiltros() {
        List<RowFilter<DefaultTableModel, Object>> filtros = new ArrayList<>();

        // 1) Filtro por tipo
        String tipoSel = (String) cbFiltroTipo.getSelectedItem();
        if (!"TODOS".equals(tipoSel)) {
            filtros.add(RowFilter.regexFilter("^" + Pattern.quote(tipoSel) + "$", 1));
        }

        // 2) Filtro por texto en Modelo o CPU
        String texto = txtBuscar.getText().trim();
        if (!texto.isEmpty()) {
            RowFilter<DefaultTableModel, Object> fModelo = RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 2);
            RowFilter<DefaultTableModel, Object> fCpu = RowFilter.regexFilter("(?i)" + Pattern.quote(texto), 3);
            filtros.add(RowFilter.orFilter(List.of(fModelo, fCpu)));
        }

        // 3) Filtro por rango de precio
        Integer min = parseEntero(txtPrecioMin.getText().trim());
        Integer max = parseEntero(txtPrecioMax.getText().trim());
        if (min != null || max != null) {
            filtros.add(new RowFilter<>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    Object val = entry.getValue(6);
                    int precio = (val instanceof Number) ? ((Number) val).intValue() : Integer.parseInt(String.valueOf(val));
                    if (min != null && precio < min) return false;
                    if (max != null && precio > max) return false;
                    return true;
                }
            });
        }

        sorter.setRowFilter(filtros.isEmpty() ? null : RowFilter.andFilter(filtros));
    }

    private Integer parseEntero(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }
}

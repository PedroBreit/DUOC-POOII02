package vista.paneles;

import conexion.ConexionBD;
import vista.util.Mensajes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Panel de Reportes:
 */
public class PanelReportes extends JPanel {

    // Filtros
    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"TODOS","DESKTOP","LAPTOP"});
    private final JTextField txtDesde = new JTextField(10);   // dd-MM-yyyy
    private final JTextField txtHasta = new JTextField(10);   // dd-MM-yyyy
    private final JTextField txtCliente = new JTextField(16); // nombre o RUT
    private final JButton btnFiltrar = new JButton("Filtrar");
    private final JLabel lblResumen = new JLabel("Ventas: 0 | Total: $0");

    // Tabla
    private final JTable tabla = new JTable();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Fecha","Modelo","Cliente","Teléfono","Correo","Precio","Descuento","Tipo"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    // Formateadores
    private final NumberFormat nf = NumberFormat.getInstance(new Locale("es","CL"));
    private final DateTimeFormatter dfSalida = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // visualización
    private final DateTimeFormatter dfEntrada = DateTimeFormatter.ofPattern("dd-MM-yyyy");      // entrada filtro

    public PanelReportes() {
        setLayout(new BorderLayout(8,8));

        // Panel de filtros
        JPanel filtros = new JPanel();
        filtros.setLayout(new BoxLayout(filtros, BoxLayout.Y_AXIS));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        fila1.add(new JLabel("Tipo:")); fila1.add(cbTipo);
        fila1.add(new JLabel("Desde (dd-MM-yyyy):")); txtDesde.setColumns(10); fila1.add(txtDesde);
        fila1.add(new JLabel("Hasta (dd-MM-yyyy):")); txtHasta.setColumns(10); fila1.add(txtHasta);
        fila1.add(new JLabel("Cliente (nombre o RUT):")); txtCliente.setColumns(16); fila1.add(txtCliente);

        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fila2.add(btnFiltrar);
        fila2.add(lblResumen);

        filtros.add(fila1);
        filtros.add(fila2);
        add(filtros, BorderLayout.NORTH);

        // Tabla
        tabla.setModel(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Eventos
        btnFiltrar.addActionListener(e -> cargarTabla());

        // Ayudas
        txtDesde.setToolTipText("Ej: 01-01-2025");
        txtHasta.setToolTipText("Ej: 31-12-2025");
        txtCliente.setToolTipText("Busca por nombre completo o RUT (coincidencia parcial)");

        cargarTabla();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);

        String tipoSel = (String) cbTipo.getSelectedItem();
        String desdeStr = txtDesde.getText().trim();
        String hastaStr = txtHasta.getText().trim();
        String clienteStr = txtCliente.getText().trim();

        // SELECT
        StringBuilder sql = new StringBuilder(
            "SELECT v.fecha_hora, e.modelo, " +
            "CONCAT(c.nombre, ' ', c.a_paterno, ' ', c.a_materno) AS nombreCompleto, c.telefono, c.correo, v.precio_final, v.descuento_aplicado, " +
            "CASE WHEN l.id_equipo IS NOT NULL THEN 'LAPTOP' " +
            "WHEN d.id_equipo IS NOT NULL THEN 'DESKTOP' ELSE 'EQUIPO' END AS tipo " +
            "FROM VENTA v " +
            "JOIN CLIENTE c ON c.rut = v.rut " +
            "JOIN EQUIPO e ON e.id_equipo = v.id_equipo " +
            "LEFT JOIN LAPTOP l ON l.id_equipo = e.id_equipo " +
            "LEFT JOIN DESKTOP d ON d.id_equipo = e.id_equipo "
        );

        List<Object> params = new ArrayList<>();
        List<String> where = new ArrayList<>();

        // Filtro por tipo
        if ("DESKTOP".equals(tipoSel)) where.add("d.id_equipo IS NOT NULL");
        else if ("LAPTOP".equals(tipoSel)) where.add("l.id_equipo IS NOT NULL");

        // Filtro por fechas
        if (!desdeStr.isBlank()) {
            java.sql.Date d = parseFechaEntrada(desdeStr);
            if (d == null) { Mensajes.error("Fecha 'Desde' inválida. Usa dd-MM-yyyy."); return; }
            where.add("DATE(v.fecha_hora) >= ?");
            params.add(d);
        }
        if (!hastaStr.isBlank()) {
            java.sql.Date h = parseFechaEntrada(hastaStr);
            if (h == null) { Mensajes.error("Fecha 'Hasta' inválida. Usa dd-MM-yyyy."); return; }
            where.add("DATE(v.fecha_hora) <= ?");
            params.add(h);
        }

        // Filtro por cliente
        if (!clienteStr.isBlank()) {
            where.add("(CONCAT(c.nombre, ' ', c.a_paterno, ' ', c.a_materno) LIKE ? OR c.rut LIKE ?)");
            String like = "%" + clienteStr + "%";
            params.add(like);
            params.add(like);
        }

        if (!where.isEmpty()) sql.append("WHERE ").append(String.join(" AND ", where)).append(" ");
        sql.append("ORDER BY v.fecha_hora DESC");

        int cant = 0; long total = 0;

        try (Connection cn = ConexionBD.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            // Asignar parámetros
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof java.sql.Date) ps.setDate(i+1, (java.sql.Date) p);
                else ps.setString(i+1, String.valueOf(p));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp(1);
                    String fechaStr = "";
                    if (ts != null) {
                        LocalDateTime ldt = ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        fechaStr = dfSalida.format(ldt);
                    }

                    String modeloEq = rs.getString(2);
                    String nombreComp = rs.getString(3);
                    int fono = rs.getInt(4);
                    String correo = rs.getString(5);
                    int precioFinal = rs.getInt(6);
                    String descuento = rs.getString(7);
                    String tipo = rs.getString(8);

                    String precioFmt = "$" + nf.format(precioFinal);

                    modelo.addRow(new Object[]{
                            fechaStr, modeloEq, nombreComp, fono, correo, precioFmt, descuento, tipo
                    });

                    cant++;
                    total += precioFinal;
                }
            }

            lblResumen.setText("Ventas: " + cant + " | Total: $" + nf.format(total));

        } catch (SQLException e) {
            Mensajes.error("Error al cargar reporte: " + e.getMessage());
        }
    }

    public void recargar() {
        cargarTabla();
    }
    
    /**
     * Parsea fecha en formato dd-MM-yyyy. Devuelve java.sql.Date o null si no es válida.
     */
    private java.sql.Date parseFechaEntrada(String texto) {
        try {
            LocalDate d = LocalDate.parse(texto, dfEntrada);
            return java.sql.Date.valueOf(d);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}

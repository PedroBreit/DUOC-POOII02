package vista.paneles;

import patrones.command.AgregarVentaDetalladaComando;
import patrones.command.GestorComandos;
import patrones.decorator.*;
import servicio.VentaServicio;
import vista.util.Mensajes;

import dao.ClienteDAO;
import dao.jdbc.ClienteDAOJDBC;
import modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Diálogo para confirmar una venta.
 *
 * Patrones:
 * - Command: el botón Confirmar ejecuta AgregarVentaComando (Invoker + Receiver).
 * - Decorator: calcula "Precio final" encadenando descuentos seleccionados.
 */
public class DialogNuevaVenta extends JDialog {

    // Datos de equipo
    private final JLabel lblId = new JLabel();
    private final JLabel lblTipo = new JLabel();
    private final JLabel lblModelo = new JLabel();
    private final JLabel lblCpu = new JLabel();
    private final JLabel lblDisco = new JLabel();
    private final JLabel lblRam = new JLabel();
    private final JLabel lblPrecioBase = new JLabel();
    private final JLabel lblExtra1 = new JLabel();
    private final JLabel lblExtra2 = new JLabel();

    // Entrada
    private final JTextField txtRut = new JTextField(14);
    private final JLabel lblClienteInfo = new JLabel(" ");

    // Descuentos
    private final JComboBox<String> cbDescuento = new JComboBox<>(new String[]{
            "Sin descuento",
            "10% de descuento",
            "20% de descuento",
            "Monto fijo $10.000",
            "Monto fijo $20.000"
    });
    private final JCheckBox chkTopeMin = new JCheckBox("Aplicar tope mínimo");
    private final JTextField txtTopeMin = new JTextField("500000", 8);

    private final JLabel lblPrecioFinal = new JLabel("$0");

    // Botones
    private final JButton btnConfirmar = new JButton("Confirmar venta");
    private final JButton btnSalir = new JButton("Salir");

    // Datos internos
    private final int idEquipo;
    private final int precioBase;
    private final Consumer<Boolean> onClose;

    // Command
    private final GestorComandos invocador = new GestorComandos();
    private final VentaServicio ventaServicio = new VentaServicio();

    // DAO para validar RUT y mostrar nombre
    private final ClienteDAO clienteDAO = new ClienteDAOJDBC();

    public DialogNuevaVenta(Window owner, int idEquipo, String tipo, String modelo, String cpu, int discoGb, int ramGb, int precio, String extra1, String extra2, Consumer<Boolean> onClose) {
        super(owner, "Nueva venta", ModalityType.APPLICATION_MODAL);
        this.idEquipo = idEquipo;
        this.precioBase = precio;
        this.onClose = onClose;

        setSize(560, 540);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setContentPane(root);

        // Panel datos equipo
        JPanel datos = new JPanel(new GridLayout(0,2,6,6));
        datos.setBorder(BorderFactory.createTitledBorder("Datos del equipo"));

        lblId.setText(String.valueOf(idEquipo));
        lblTipo.setText(tipo);
        lblModelo.setText(modelo);
        lblCpu.setText(cpu);
        lblDisco.setText(discoGb + " GB");
        lblRam.setText(ramGb + " GB");
        lblPrecioBase.setText("$" + precioBase);
        lblExtra1.setText(extra1 == null ? "" : extra1);
        lblExtra2.setText(extra2 == null ? "" : extra2);

        datos.add(new JLabel("ID:")); datos.add(lblId);
        datos.add(new JLabel("Tipo:")); datos.add(lblTipo);
        datos.add(new JLabel("Modelo:")); datos.add(lblModelo);
        datos.add(new JLabel("CPU:")); datos.add(lblCpu);
        datos.add(new JLabel("Disco (GB):")); datos.add(lblDisco);
        datos.add(new JLabel("RAM:")); datos.add(lblRam);
        datos.add(new JLabel("Precio base:")); datos.add(lblPrecioBase);
        datos.add(new JLabel("Detalle 1:")); datos.add(lblExtra1);
        datos.add(new JLabel("Detalle 2:")); datos.add(lblExtra2);

        // Panel venta
        JPanel venta = new JPanel(new GridBagLayout());
        venta.setBorder(BorderFactory.createTitledBorder("Datos de la venta"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0; gc.gridy = 0; venta.add(new JLabel("RUT cliente:"), gc);
        gc.gridx = 1; txtRut.setColumns(14); venta.add(txtRut, gc);

        gc.gridx = 0; gc.gridy = 1; venta.add(new JLabel("Cliente:"), gc);
        gc.gridx = 1; venta.add(lblClienteInfo, gc);

        gc.gridx = 0; gc.gridy = 2; venta.add(new JLabel("Descuento:"), gc);
        gc.gridx = 1; venta.add(cbDescuento, gc);

        gc.gridx = 0; gc.gridy = 3; venta.add(chkTopeMin, gc);
        gc.gridx = 1; txtTopeMin.setColumns(8); venta.add(txtTopeMin, gc);

        gc.gridx = 0; gc.gridy = 4; venta.add(new JLabel("Precio final:"), gc);
        gc.gridx = 1; lblPrecioFinal.setFont(lblPrecioFinal.getFont().deriveFont(Font.BOLD, 14f));
        venta.add(lblPrecioFinal, gc);

        // Botones
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.add(btnSalir);
        sur.add(btnConfirmar);

        root.add(datos, BorderLayout.NORTH);
        root.add(venta, BorderLayout.CENTER);
        root.add(sur, BorderLayout.SOUTH);

        // Acciones
        btnSalir.addActionListener(e -> { if (onClose != null) onClose.accept(false); dispose(); });
        btnConfirmar.addActionListener(e -> confirmarVenta());
        cbDescuento.addActionListener(e -> recalcularPrecio());
        chkTopeMin.addActionListener(e -> recalcularPrecio());
        txtTopeMin.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { recalcularPrecio(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { recalcularPrecio(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { recalcularPrecio(); }
        });

        // Validación del RUT: muestra el nombre si existe
        txtRut.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { buscarCliente(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { buscarCliente(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { buscarCliente(); }
        });

        // Estado inicial
        recalcularPrecio();
    }

    // Decorator: construye la cadena de descuentos según selección
    private Descuento construirDescuento() {
        Descuento d = new SinDescuento();
        String sel = (String) cbDescuento.getSelectedItem();
        if ("10% de descuento".equals(sel)) {
            d = new DescuentoPorcentaje(d, 10);
        } else if ("20% de descuento".equals(sel)) {
            d = new DescuentoPorcentaje(d, 20);
        } else if ("Monto fijo $10.000".equals(sel)) {
            d = new DescuentoMontoFijo(d, 10000);
        } else if ("Monto fijo $00.000".equals(sel)) {
            d = new DescuentoMontoFijo(d, 20000);
        }

        if (chkTopeMin.isSelected()) {
            int minimo = parseEnteroPositivo(txtTopeMin.getText().trim(), 0);
            d = new DescuentoTopeMinimo(d, minimo);
        }
        return d;
    }

    private void recalcularPrecio() {
        Descuento d = construirDescuento();
        int finalCalc = d.aplicar(precioBase);
        lblPrecioFinal.setText("$" + finalCalc);
    }

    private int parseEnteroPositivo(String s, int defecto) {
        try { int v = Integer.parseInt(s); return Math.max(0, v); }
        catch (Exception e) { return defecto; }
    }

    // Búsqueda simple del cliente por RUT para mostrar nombre y validar
    private void buscarCliente() {
        String rut = txtRut.getText().trim();
        if (rut.isBlank()) {
            lblClienteInfo.setText(" ");
            return;
        }
        Optional<Cliente> oc = clienteDAO.buscarPorRut(rut);
        if (oc.isPresent()) {
            Cliente c = oc.get();
            lblClienteInfo.setText(c.getNombre() + " " + c.getApellidoPaterno() + " " + c.getApellidoMaterno());
        } else {
            lblClienteInfo.setText("No encontrado");
        }
    }

    private void confirmarVenta() {
    String rut = txtRut.getText().trim();
    if (rut.isBlank()) { Mensajes.error("Ingresa el RUT del cliente."); return; }

    Descuento d = construirDescuento();
    int finalCalc = d.aplicar(precioBase);
    String etiqueta = (String) cbDescuento.getSelectedItem();
    if (chkTopeMin.isSelected()) {
        int minimo = parseEnteroPositivo(txtTopeMin.getText().trim(), 0);
        etiqueta += " + Tope " + minimo;
    }

    VentaServicio servicio = new VentaServicio();
    boolean ok = invocador.ejecutar(
        new AgregarVentaDetalladaComando(servicio, rut, idEquipo, finalCalc, etiqueta)
    );

    if (ok) {
        Mensajes.info("Venta registrada. Precio final: $" + finalCalc + " | Descuento: " + etiqueta);
        if (onClose != null) onClose.accept(true);
        dispose();
    } else {
        String causa = servicio.getUltimoError();
        Mensajes.error("No se pudo registrar la venta.\nDetalle: " + (causa == null ? "(sin detalle)" : causa));
    }
}

}

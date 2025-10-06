package vista.paneles;

import dao.ClienteDAO;
import dao.jdbc.ClienteDAOJDBC;
import modelo.Cliente;
import vista.util.Mensajes;
import vista.util.TablaClientesModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel de gestión de clientes: crear, actualizar, eliminar, listar.
 */
public class PanelClientes extends JPanel {

    private final JTextField txtRut = new JTextField(12);
    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtAP = new JTextField(14);
    private final JTextField txtAM = new JTextField(14);
    private final JTextField txtDir = new JTextField(20);
    private final JTextField txtComuna = new JTextField(14);
    private final JTextField txtCorreo = new JTextField(20);
    private final JTextField txtFono = new JTextField(12);

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");
    private final JButton btnBuscar = new JButton("Buscar por RUT");

    private final JTable tabla = new JTable();
    private TablaClientesModel modeloTabla;

    private final ClienteDAO dao = new ClienteDAOJDBC();

    public PanelClientes() {
        setLayout(new BorderLayout(8,8));

        JPanel form = new JPanel(new GridLayout(4, 4, 6, 6));
        form.setBorder(BorderFactory.createTitledBorder("Datos del cliente"));

        form.add(new JLabel("RUT:")); form.add(txtRut);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Ap. Paterno:")); form.add(txtAP);
        form.add(new JLabel("Ap. Materno:")); form.add(txtAM);
        form.add(new JLabel("Dirección:")); form.add(txtDir);
        form.add(new JLabel("Comuna:")); form.add(txtComuna);
        form.add(new JLabel("Correo:")); form.add(txtCorreo);
        form.add(new JLabel("Teléfono (+56):")); form.add(txtFono);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(btnGuardar); botones.add(btnActualizar);
        botones.add(btnEliminar); botones.add(btnLimpiar);
        botones.add(btnBuscar);

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.CENTER);
        norte.add(botones, BorderLayout.SOUTH);
        add(norte, BorderLayout.NORTH);

        // Tabla al centro
        modeloTabla = new TablaClientesModel(dao.listarTodos());
        tabla.setModel(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Acciones
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnBuscar.addActionListener(e -> buscarPorRut());

        // Al seleccionar una fila, carga los datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                Cliente c = modeloTabla.getClienteAt(tabla.getSelectedRow());
                cargarEnFormulario(c);
            }
        });
    }

    private void cargarEnFormulario(Cliente c) {
        txtRut.setText(c.getRut());
        txtNombre.setText(c.getNombre());
        txtAP.setText(c.getApellidoPaterno());
        txtAM.setText(c.getApellidoMaterno());
        txtDir.setText(c.getDireccion());
        txtComuna.setText(c.getComuna());
        txtCorreo.setText(c.getCorreo());
        txtFono.setText(String.valueOf(c.getTelefono()));
    }

    private Cliente construir() {
        // Validaciones básicas
        if (txtRut.getText().isBlank() || txtNombre.getText().isBlank() ||
            txtCorreo.getText().isBlank() || txtFono.getText().isBlank()) {
            Mensajes.error("Completa RUT, Nombre, Correo y Teléfono.");
            return null;
        }
        int fono;
        try {
            fono = Integer.parseInt(txtFono.getText().trim());
        } catch (NumberFormatException ex) {
            Mensajes.error("Teléfono debe ser número entero.");
            return null;
        }

        return new Cliente(
                txtRut.getText().trim(),
                txtNombre.getText().trim(),
                txtAP.getText().trim(),
                txtAM.getText().trim(),
                txtDir.getText().trim(),
                txtComuna.getText().trim(),
                txtCorreo.getText().trim(),
                fono
        );
    }

    private void guardar() {
        Cliente c = construir();
        if (c == null) return;
        boolean ok = dao.crear(c);
        if (ok) {
            Mensajes.info("Cliente guardado.");
            refrescarTabla();
            limpiar();
        } else {
            Mensajes.error("No se pudo guardar. Verifica si el RUT ya existe.");
        }
    }

    private void actualizar() {
        Cliente c = construir();
        if (c == null) return;
        boolean ok = dao.actualizar(c);
        if (ok) {
            Mensajes.info("Cliente actualizado.");
            refrescarTabla();
        } else {
            Mensajes.error("No se pudo actualizar.");
        }
    }

    private void eliminar() {
        String rut = txtRut.getText().trim();
        if (rut.isBlank()) { Mensajes.error("Indica el RUT a eliminar."); return; }
        if (!Mensajes.confirmar("¿Eliminar cliente " + rut + "?")) return;

        boolean ok = dao.eliminarPorRut(rut);
        if (ok) {
            Mensajes.info("Cliente eliminado.");
            refrescarTabla();
            limpiar();
        } else {
            Mensajes.error("No se pudo eliminar (puede tener ventas asociadas).");
        }
    }

    private void buscarPorRut() {
        String rut = txtRut.getText().trim();
        if (rut.isBlank()) { Mensajes.error("Escribe un RUT para buscar."); return; }
        dao.buscarPorRut(rut).ifPresentOrElse(this::cargarEnFormulario,
                () -> Mensajes.info("No se encontró cliente con RUT " + rut));
    }

    private void refrescarTabla() {
        List<Cliente> lista = dao.listarTodos();
        modeloTabla.setDatos(lista);
    }

    private void limpiar() {
        txtRut.setText(""); txtNombre.setText(""); txtAP.setText(""); txtAM.setText("");
        txtDir.setText(""); txtComuna.setText(""); txtCorreo.setText(""); txtFono.setText("");
        tabla.clearSelection();
    }
}


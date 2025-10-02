package controlador;

import vista.CarteleraVista;
import vista.dialogo.Dialogos;
import dao.CarteleraDao;
import modelo.Cartelera;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarteleraControlador {

    private final CarteleraVista ui;
    private final CarteleraDao dao = new CarteleraDao();
    private List<Cartelera> cache = new ArrayList<>();

    public CarteleraControlador(CarteleraVista vista) {
        this.ui = vista;
        conectarEventos();
        cargarCarteleraCompleta();
    }

    private void conectarEventos() {
        ui.getBtnBuscar().addActionListener(e -> onBuscar());
        ui.getBtnAplicarFiltros().addActionListener(e -> onAplicarFiltros());
        ui.getBtnAgregar().addActionListener(e -> onAgregar());
        ui.getBtnModificar().addActionListener(e -> onModificar());
        ui.getBtnEliminar().addActionListener(e -> onEliminar());
        ui.getBtnLimpiar().addActionListener(e -> onLimpiar());
    }

    private void cargarCarteleraCompleta() {
        cache = safe(dao.listar());
        ui.setPeliculas(cache);
    }

    private void onBuscar() {
        String criterio = text(ui.getTxtCriterio().getText()).trim();
        if (ui.getRbId().isSelected()) {
            if (criterio.isEmpty()) { Dialogos.warn("Ingresa un ID para buscar."); return; }
            try {
                int id = Integer.parseInt(criterio);
                Cartelera c = dao.obtenerPorId(id);
                ui.setPeliculas(c != null ? List.of(c) : List.of());
                if (c == null) Dialogos.info("No se encontró una película con ID " + id + ".");
            } catch (NumberFormatException ex) {
                Dialogos.warn("El ID debe ser numérico.");
            }
            return;
        }
        ui.setPeliculas(dao.buscarPorNombre(criterio));
    }

    private void onAplicarFiltros() {
        String genero = ui.getGeneroSeleccionado();
        Integer desde = ui.getAnioDesde();
        Integer hasta = ui.getAnioHasta();
        if (desde != null && hasta != null && desde > hasta) {
            Dialogos.warn("El año 'desde' no puede ser mayor que 'hasta'.");
            return;
        }
        List<Cartelera> filtrada = cache.stream()
            .filter(c -> genero == null || genero.equalsIgnoreCase(text(c.getGenero())))
            .filter(c -> {
                int anio = parseInt(text(c.getAnio()));
                if (desde != null && anio < desde) return false;
                if (hasta != null && anio > hasta) return false;
                return true;
            })
            .collect(Collectors.toList());
        ui.setPeliculas(filtrada);
    }

    private void onAgregar() {
        Cartelera datos = mostrarFormulario(null);
        if (datos == null) return;
        if (!validarObligatorios(datos) || !validarAnioNumerico(datos.getAnio())) return;
        if (dao.crear(datos)) { Dialogos.info("Película agregada."); cargarCarteleraCompleta(); }
        else Dialogos.error("No se pudo agregar. Revisa consola por detalles.");
    }

    private void onModificar() {
        Cartelera sel = ui.getSeleccion();
        if (sel == null) { Dialogos.warn("Selecciona una película de la tabla."); return; }
        Cartelera editada = mostrarFormulario(sel);
        if (editada == null) return;
        if (!validarObligatorios(editada) || !validarAnioNumerico(editada.getAnio())) return;
        if (dao.actualizar(editada)) { Dialogos.info("Película modificada."); cargarCarteleraCompleta(); }
        else Dialogos.error("No se pudo modificar.");
    }

    private void onEliminar() {
        Cartelera sel = ui.getSeleccion();
        if (sel == null) { Dialogos.warn("Selecciona una película de la tabla."); return; }
        if (!Dialogos.confirmar("Confirmar eliminación",
                "¿Eliminar la película ID " + sel.getId() + "?\n" + sel.getTitulo())) return;
        if (dao.eliminar(sel.getId())) { Dialogos.info("Película eliminada."); cargarCarteleraCompleta(); }
        else Dialogos.error("No se pudo eliminar.");
    }

    private void onLimpiar() {
        ui.getTxtCriterio().setText("");
        ui.setGeneroSeleccionado(null);
        ui.setAnioDesde(1900);
        ui.setAnioHasta(2026);
        ui.getTabla().clearSelection();
        cargarCarteleraCompleta();
    }

    // ---- Formulario embebido (Agregar/Modificar) con JOptionPane ----
    private Cartelera mostrarFormulario(Cartelera base) {
        JTextField txtTitulo   = new JTextField(25);
        JTextField txtDirector = new JTextField(25);
        JTextField txtAnio     = new JTextField(8);
        JSpinner spDuracion    = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        JComboBox<String> cbGenero = new JComboBox<>(new String[]{
            "", "Acción", "Comedia", "Drama", "Terror", "Ciencia Ficción", "Animación",
            "Aventura", "Fantasía", "Romance", "Thriller", "Crimen", "Guerra"
        });

        Integer id = null;
        if (base != null) {
            id = base.getId();
            txtTitulo.setText(text(base.getTitulo()));
            txtDirector.setText(text(base.getDirector()));
            txtAnio.setText(text(base.getAnio()));
            spDuracion.setValue(parseInt(text(base.getDuracion())));
            cbGenero.setSelectedItem(text(base.getGenero()));
        }

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,8,6,8);
        c.anchor = GridBagConstraints.WEST;
        int y=0;

        if (id != null) {
            JTextField txtId = new JTextField(String.valueOf(id), 6);
            txtId.setEnabled(false);
            addRow(form, c, y++, new JLabel("ID:"), txtId);
        }
        addRow(form, c, y++, new JLabel("Título:"),   txtTitulo);
        addRow(form, c, y++, new JLabel("Director:"), txtDirector);
        addRow(form, c, y++, new JLabel("Año:"),      txtAnio);
        addRow(form, c, y++, new JLabel("Duración (min):"), spDuracion);
        addRow(form, c, y++, new JLabel("Género:"),   cbGenero);

        String tituloDlg = (id == null) ? "Agregar película" : "Modificar película";
        int r = JOptionPane.showConfirmDialog(ui, form, tituloDlg,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return null;

        Cartelera out = new Cartelera();
        if (id != null) out.setId(id);
        out.setTitulo(txtTitulo.getText().trim());
        out.setDirector(txtDirector.getText().trim());
        out.setAnio(txtAnio.getText().trim());
        out.setDuracion(String.valueOf((Integer) spDuracion.getValue()));
        String g = (String) cbGenero.getSelectedItem();
        out.setGenero(g == null ? "" : g.trim());
        return out;
    }

    private static void addRow(JPanel p, GridBagConstraints c, int y, JComponent l, JComponent f) {
        c.gridx=0; c.gridy=y; c.weightx=0; c.fill=GridBagConstraints.NONE; p.add(l,c);
        c.gridx=1; c.gridy=y; c.weightx=1; c.fill=GridBagConstraints.HORIZONTAL; p.add(f,c);
    }

    // ---- Validaciones / util ----
    private boolean validarObligatorios(Cartelera c) {
        if (isBlank(c.getTitulo()) || isBlank(c.getDirector()) || isBlank(c.getAnio())) {
            Dialogos.warn("Completa Título, Director y Año."); return false;
        }
        return true;
    }
    private boolean validarAnioNumerico(String anioStr) {
        try { Integer.parseInt(text(anioStr)); return true; }
        catch (Exception e) { Dialogos.warn("El Año debe ser numérico (ej: 2024)."); return false; }
    }

    private static String text(String s) { return s == null ? "" : s; }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static int parseInt(String s) { try { return Integer.parseInt(s.trim()); } catch(Exception e){ return 0; } }
    private static <T> List<T> safe(List<T> in) { return in == null ? new ArrayList<>() : in; }
}

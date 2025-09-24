package controlador;

import vista.Dialogo.Dialogos;
import vista.Dialogo.DialogoModificarPelicula;
import vista.Dialogo.DialogoAgregarPelicula;
import dao.PeliculaDao;
import modelo.Pelicula;
import vista.*;

import java.util.List;

public class PeliculaControlador {

    private final PeliculaFrame frame;
    private final PanelBusqueda uiBuscar;
    private final PanelTabla uiTabla;
    private final PanelAcciones uiAcciones;
    private final PeliculaDao dao = new PeliculaDao();

    public PeliculaControlador(PeliculaFrame frame) {
        this.frame = frame;
        this.uiBuscar = frame.getPanelBusqueda();
        this.uiTabla = frame.getPanelTabla();
        this.uiAcciones = frame.getPanelAcciones();

        conectarEventos();
        cargarCarteleraCompleta(); // al iniciar
    }

    private void conectarEventos() {
        uiBuscar.getBtnBuscar().addActionListener(e -> onBuscar());

        uiAcciones.getBtnAgregar().addActionListener(e -> onAgregar());
        uiAcciones.getBtnModificar().addActionListener(e -> onModificar());
        uiAcciones.getBtnEliminar().addActionListener(e -> onEliminar());
        uiAcciones.getBtnLimpiar().addActionListener(e -> onLimpiar());
    }

    private void cargarCarteleraCompleta() {
        List<Pelicula> lista = dao.listar();
        uiTabla.setPeliculas(lista);
    }

    private void onBuscar() {
        String criterio = uiBuscar.getTxtCriterio().getText() == null ? "" : uiBuscar.getTxtCriterio().getText().trim();

        if (uiBuscar.getRbId().isSelected()) {
            if (criterio.isEmpty()) {
                Dialogos.warn("Ingresa un ID para buscar.");
                return;
            }
            try {
                int id = Integer.parseInt(criterio);
                Pelicula p = dao.obtenerPorId(id);
                if (p != null) uiTabla.setPeliculas(List.of(p)); else uiTabla.setPeliculas(List.of());
            } catch (NumberFormatException ex) {
                Dialogos.warn("El ID debe ser numérico.");
            }
        } else {
            uiTabla.setPeliculas(dao.buscarPorNombre(criterio));
        }
    }

    private void onAgregar() {
        DialogoAgregarPelicula dlg = new DialogoAgregarPelicula(frame);
        dlg.getBtnAgregar().addActionListener(e -> {
            Pelicula p = dlg.construirPelicula();
            if (!validarObligatorios(p) || !validarAnioNumerico(p.getAnio())) return;
            boolean ok = dao.crear(p);
            if (ok) {
                Dialogos.info("Película agregada.");
                dlg.dispose();
                cargarCarteleraCompleta();
            } else {
                Dialogos.error("No se pudo agregar. Revisa consola por detalles.");
            }
        });
        dlg.setVisible(true);
    }

    private void onModificar() {
        Pelicula sel = uiTabla.getSeleccion();
        if (sel == null) { Dialogos.warn("Selecciona una película de la tabla."); return; }

        DialogoModificarPelicula dlg = new DialogoModificarPelicula(frame);
        dlg.cargar(sel);
        dlg.getBtnModificar().addActionListener(e -> {
            Pelicula editada = dlg.construirEditada();
            if (!validarObligatorios(editada) || !validarAnioNumerico(editada.getAnio())) return;
            boolean ok = dao.actualizar(editada);
            if (ok) {
                Dialogos.info("Película modificada.");
                dlg.dispose();
                cargarCarteleraCompleta();
            } else {
                Dialogos.error("No se pudo modificar.");
            }
        });
        dlg.setVisible(true);
    }

    private void onEliminar() {
        Pelicula sel = uiTabla.getSeleccion();
        if (sel == null) { Dialogos.warn("Selecciona una película de la tabla."); return; }

        boolean confirmar = Dialogos.confirmar("Confirmar eliminación",
                "¿Eliminar la película ID " + sel.getId() + "?\n" + sel.getTitulo());
        if (confirmar) {
            boolean ok = dao.eliminar(sel.getId());
            if (ok) {
                Dialogos.info("Película eliminada.");
                cargarCarteleraCompleta();
            } else {
                Dialogos.error("No se pudo eliminar.");
            }
        }
    }

    private void onLimpiar() {
        uiBuscar.getTxtCriterio().setText("");
        cargarCarteleraCompleta();
        uiTabla.getTabla().clearSelection();
    }

    private boolean validarObligatorios(Pelicula p) {
        if (p.getTitulo() == null || p.getTitulo().isBlank()
                || p.getDirector() == null || p.getDirector().isBlank()
                || p.getAnio() == null || p.getAnio().isBlank()) {
            Dialogos.warn("Completa Título, Director y Año.");
            return false;
        }
        return true;
    }

    private boolean validarAnioNumerico(String anioStr) {
        try { Integer.parseInt(anioStr.trim()); return true; }
        catch (Exception e) { Dialogos.warn("El Año debe ser numérico (ej: 2024)."); return false; }
    }
}

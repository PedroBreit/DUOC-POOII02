package pruebas;

import dao.EquipoDAO;
import dao.jdbc.EquipoDAOJDBC;
import modelo.Desktop;
import modelo.Equipo;
import modelo.Laptop;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Prueba CRUD de Equipo: crea un Desktop y una Laptop, lee, actualiza, lista y elimina.
 */
public class PruebaEquipo {

    private static final EquipoDAO equipoDAO = new EquipoDAOJDBC();
    private static final Random RND = new Random();

    public static void main(String[] args) {
        System.out.println("=== PRUEBA EQUIPO CRUD ===");

        // Desktop
        int idDesktop = probarDesktop();
        // Laptop
        int idLaptop = probarLaptop();

        // Listar todos
        System.out.println("Listando equipos (conteo)...");
        List<Equipo> todos = equipoDAO.listarTodos();
        System.out.println("[OK] Total equipos en BD: " + (todos == null ? 0 : todos.size()));

        // Eliminar creados
        if (idDesktop != -1) {
            System.out.println("Eliminando Desktop id " + idDesktop + "...");
            boolean ok = equipoDAO.eliminarPorId(idDesktop);
            System.out.println(ok ? "[OK] Desktop eliminado" : "[FAIL] No se pudo eliminar Desktop");
        }
        if (idLaptop != -1) {
            System.out.println("Eliminando Laptop id " + idLaptop + "...");
            boolean ok = equipoDAO.eliminarPorId(idLaptop);
            System.out.println(ok ? "[OK] Laptop eliminada" : "[FAIL] No se pudo eliminar Laptop");
        }

        System.out.println("=== FIN PRUEBA EQUIPO CRUD ===");
    }

    private static int probarDesktop() {
        System.out.println("-- Desktop: crear, leer, actualizar --");
        Desktop d = new Desktop();
        d.setModelo("DesktopPrueba-" + RND.nextInt(900));
        d.setCpu("Ryzen 5 5600");
        d.setDiscoGb(512);
        d.setRamGb(16);
        d.setPrecio(799990);
        d.setPotenciaFuenteW(650);
        d.setPlaca("ATX");

        System.out.println("Creando Desktop...");
        boolean ok = equipoDAO.crearDesktop(d);
        System.out.println(ok ? "[OK] Desktop creado id=" + d.getIdEquipo() : "[FAIL] No se pudo crear Desktop");
        if (!ok) return -1;

        System.out.println("Buscando Desktop por id...");
        Optional<Equipo> op = equipoDAO.buscarPorId(d.getIdEquipo());
        System.out.println(op.isPresent() ? "[OK] Encontrado " + op.get().getModelo() : "[FAIL] No encontrado");

        System.out.println("Actualizando RAM y precio...");
        d.setRamGb(32);
        d.setPrecio(749990);
        boolean upd = equipoDAO.actualizarDesktop(d);
        System.out.println(upd ? "[OK] Desktop actualizado" : "[FAIL] No se pudo actualizar");

        return d.getIdEquipo();
    }

    private static int probarLaptop() {
        System.out.println("-- Laptop: crear, leer, actualizar --");
        Laptop l = new Laptop();
        l.setModelo("LaptopPrueba-" + RND.nextInt(900));
        l.setCpu("Intel i5 1240P");
        l.setDiscoGb(512);
        l.setRamGb(16);
        l.setPrecio(899990);
        l.setTamPantallaPulg(14.0);
        l.setEsTouch(false);
        l.setPuertosUsb(3);

        System.out.println("Creando Laptop...");
        boolean ok = equipoDAO.crearLaptop(l);
        System.out.println(ok ? "[OK] Laptop creada id=" + l.getIdEquipo() : "[FAIL] No se pudo crear Laptop");
        if (!ok) return -1;

        System.out.println("Buscando Laptop por id...");
        Optional<Equipo> op = equipoDAO.buscarPorId(l.getIdEquipo());
        System.out.println(op.isPresent() ? "[OK] Encontrada " + op.get().getModelo() : "[FAIL] No encontrada");

        System.out.println("Actualizando disco y precio...");
        l.setDiscoGb(1024);
        l.setPrecio(859990);
        boolean upd = equipoDAO.actualizarLaptop(l);
        System.out.println(upd ? "[OK] Laptop actualizada" : "[FAIL] No se pudo actualizar");

        return l.getIdEquipo();
    }
}

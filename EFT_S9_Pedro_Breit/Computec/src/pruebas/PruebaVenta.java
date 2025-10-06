package pruebas;

import dao.ClienteDAO;
import dao.EquipoDAO;
import dao.VentaDAO;
import dao.jdbc.ClienteDAOJDBC;
import dao.jdbc.EquipoDAOJDBC;
import dao.jdbc.VentaDAOJDBC;
import modelo.Cliente;
import modelo.Desktop;
import modelo.Equipo;
import modelo.Venta;
import servicio.VentaServicio;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Prueba CRUD de Venta considerando:
 *  - precio_final
 *  - descuento_aplicado
 *
 * Flujo:
 *  1) Asegura cliente y equipo temporales
 *  2) Crea venta usando VentaServicio.registrarVentaDetallada(...)
 *  3) Lee por id
 *  4) Actualiza
 *  5) Lista ventas
 *  6) Elimina venta creada
 *  7) Limpieza de datos temporales
 */
public class PruebaVenta {

    private static final ClienteDAO clienteDAO = new ClienteDAOJDBC();
    private static final EquipoDAO  equipoDAO  = new EquipoDAOJDBC();
    private static final VentaDAO   ventaDAO   = new VentaDAOJDBC();
    private static final VentaServicio ventaSrv = new VentaServicio();
    private static final Random RND = new Random();

    public static void main(String[] args) {
        System.out.println("=== PRUEBA VENTA CRUD (con precio_final y descuento_aplicado) ===");

        // 1) Prepara datos de prueba
        String rut = generarRutPrueba();
        asegurarCliente(rut);
        int idEquipo1 = crearEquipoTemporal("DeskTmpA-");
        int idEquipo2 = crearEquipoTemporal("DeskTmpB-"); // para probar update cambiando equipo

        if (idEquipo1 == -1) {
            System.out.println("[FAIL] No fue posible crear equipo temporal");
            System.out.println("=== FIN PRUEBA VENTA CRUD ===");
            return;
        }

        // 2) Crear venta detallada
        System.out.println("Creando venta con descuento 10% ...");
        int precioBase = 700000;               // referencia de precio que usaras en la GUI
        int precioFinal = aplicarDescuentoPorcentaje(precioBase, 10);
        String etiqueta = "10% de descuento";

        Venta v = new Venta();
        v.setRutCliente(rut);
        v.setIdEquipo(idEquipo1);
        v.setPrecioFinal(precioFinal);
        v.setDescuentoAplicado(etiqueta);

        boolean okCrear = ventaSrv.registrarVentaDetallada(
                v.getRutCliente(), v.getIdEquipo(), v.getPrecioFinal(), v.getDescuentoAplicado()
        );
        System.out.println(okCrear ? "[OK] Venta creada" : "[FAIL] No se pudo crear venta");
        if (!okCrear) {
            limpiar(rut, idEquipo1, idEquipo2, null);
            System.out.println("=== FIN PRUEBA VENTA CRUD ===");
            return;
        }

        // obtener id_venta creada para continuar pruebas
        Integer idVentaCreada = obtenerUltimaVentaId(rut, idEquipo1);
        if (idVentaCreada == null) {
            System.out.println("[FAIL] No se pudo obtener id_venta creada");
            limpiar(rut, idEquipo1, idEquipo2, null);
            System.out.println("=== FIN PRUEBA VENTA CRUD ===");
            return;
        }
        v.setIdVenta(idVentaCreada);
        System.out.println("[OK] id_venta=" + idVentaCreada);

        // 3) Leer por id
        System.out.println("Buscando venta por id ...");
        Optional<Venta> op = ventaDAO.buscarPorId(idVentaCreada);
        System.out.println(op.isPresent() ? "[OK] Venta encontrada" : "[FAIL] Venta no encontrada");

        // 4) Actualizar: cambiar equipo, cambiar descuento y precio_final
        if (idEquipo2 != -1) {
            System.out.println("Actualizando venta: cambiar equipo y aplicar descuento monto fijo 20000 ...");
            v.setIdEquipo(idEquipo2);
            v.setPrecioFinal(aplicarDescuentoMontoFijo(precioBase, 20000));
            v.setDescuentoAplicado("Monto fijo $20000");

            boolean okUpd = ventaDAO.actualizar(v);
            System.out.println(okUpd ? "[OK] Venta actualizada" : "[FAIL] No se pudo actualizar la venta");
        } else {
            System.out.println("[WARN] No hay segundo equipo temporal. Se omite update de cambio de equipo.");
        }

        // 5) Listar todas (conteo)
        System.out.println("Listando ventas (conteo general) ...");
        List<Venta> ventas = ventaDAO.listarTodas();
        System.out.println("[OK] Ventas en BD: " + (ventas == null ? 0 : ventas.size()));

        // 6) Eliminar venta
        System.out.println("Eliminando venta creada ...");
        boolean okDel = ventaDAO.eliminarPorId(idVentaCreada);
        System.out.println(okDel ? "[OK] Venta eliminada" : "[FAIL] No se pudo eliminar la venta");

        // 7) Limpieza
        limpiar(rut, idEquipo1, idEquipo2, idVentaCreada);

        System.out.println("=== FIN PRUEBA VENTA CRUD ===");
    }

    private static String generarRutPrueba() {
        int base = 100000 + RND.nextInt(899999);
        return "97." + base + "-9";
    }

    private static void asegurarCliente(String rut) {
        try {
            Optional<Cliente> op = clienteDAO.buscarPorRut(rut);
            if (op.isEmpty()) {
                Cliente c = new Cliente(rut, "Auto", "Gen", "Prueba", "Calle X 100", "Santiago", "auto@mail.com", 900000000);
                boolean ok = clienteDAO.crear(c);
                System.out.println(ok ? "[OK] Cliente de prueba creado" : "[FAIL] No se pudo crear cliente de prueba");
            } else {
                System.out.println("[OK] Cliente de prueba ya existe");
            }
        } catch (Exception e) {
            System.out.println("[FAIL] Error asegurando cliente: " + e.getMessage());
        }
    }

    private static int crearEquipoTemporal(String prefijoModelo) {
        try {
            Desktop d = new Desktop();
            d.setModelo(prefijoModelo + RND.nextInt(900));
            d.setCpu("Ryzen 5 5600");
            d.setDiscoGb(512);
            d.setRamGb(16);
            d.setPrecio(700000);
            d.setPotenciaFuenteW(600);
            d.setPlaca("ATX");
            boolean ok = equipoDAO.crearDesktop(d);
            System.out.println(ok ? "[OK] Equipo temporal creado id=" + d.getIdEquipo() :
                                   "[FAIL] No se pudo crear equipo temporal");
            return ok ? d.getIdEquipo() : -1;
        } catch (Exception e) {
            System.out.println("[FAIL] Error creando equipo temporal: " + e.getMessage());
            return -1;
        }
    }

    private static Integer obtenerUltimaVentaId(String rut, int idEquipo) {
        try {
            List<Venta> lista = ventaDAO.listarTodas();
            if (lista == null) return null;
            for (Venta v : lista) {
                if (rut.equals(v.getRutCliente()) && v.getIdEquipo() == idEquipo) {
                    return v.getIdVenta();
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    private static void limpiar(String rut, int idEquipo1, int idEquipo2, Integer idVenta) {
        try {
            // Eliminar venta si sigue existiendo
            if (idVenta != null) {
                ventaDAO.eliminarPorId(idVenta);
            }
            // Eliminar equipos temporales
            if (idEquipo1 != -1) equipoDAO.eliminarPorId(idEquipo1);
            if (idEquipo2 != -1) equipoDAO.eliminarPorId(idEquipo2);
            // Eliminar cliente temporal
            boolean okc = clienteDAO.eliminarPorRut(rut);
            System.out.println(okc ? "[OK] Cliente de prueba eliminado" :
                                     "[WARN] No se pudo eliminar cliente de prueba");
        } catch (Exception e) {
            System.out.println("[WARN] Error en limpieza: " + e.getMessage());
        }
    }

    private static int aplicarDescuentoPorcentaje(int precioBase, int porcentaje) {
        if (porcentaje < 0) porcentaje = 0;
        if (porcentaje > 100) porcentaje = 100;
        int rebaja = precioBase * porcentaje / 100;
        int res = precioBase - rebaja;
        return Math.max(0, res);
    }

    private static int aplicarDescuentoMontoFijo(int precioBase, int monto) {
        if (monto < 0) monto = 0;
        int res = precioBase - monto;
        return Math.max(0, res);
    }
}


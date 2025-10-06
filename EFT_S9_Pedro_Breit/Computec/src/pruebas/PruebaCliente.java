package pruebas;

import dao.ClienteDAO;
import dao.jdbc.ClienteDAOJDBC;
import modelo.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Prueba CRUD de Cliente: crear, leer, actualizar, listar y eliminar.
 */
public class PruebaCliente {

    private static final ClienteDAO clienteDAO = new ClienteDAOJDBC();
    private static final Random RND = new Random();

    public static void main(String[] args) {
        System.out.println("=== PRUEBA CLIENTE CRUD ===");

        String rut = generarRutPrueba();
        Cliente c = new Cliente(
                rut,
                "NombrePrueba",
                "Paterno",
                "Materno",
                "Direccion X 123",
                "Santiago",
                "correo@prueba.com",
                912345678
        );

        // CREATE
        System.out.println("Creando cliente con RUT: " + rut);
        boolean creado = clienteDAO.crear(c);
        System.out.println(creado ? "[OK] Cliente creado" : "[FAIL] No se pudo crear");

        // READ
        System.out.println("Buscando cliente por RUT...");
        Optional<Cliente> op = clienteDAO.buscarPorRut(rut);
        System.out.println(op.isPresent() ? "[OK] Cliente encontrado" : "[FAIL] No encontrado");

        // UPDATE
        System.out.println("Actualizando correo y telefono...");
        c.setCorreo("nuevo@correo.com");
        c.setTelefono(900000000);
        boolean actualizado = clienteDAO.actualizar(c);
        System.out.println(actualizado ? "[OK] Cliente actualizado" : "[FAIL] No se pudo actualizar");

        // LIST ALL
        System.out.println("Listando todos los clientes (conteo)...");
        List<Cliente> lista = clienteDAO.listarTodos();
        System.out.println("[OK] Total clientes en BD: " + (lista == null ? 0 : lista.size()));

        // DELETE
        System.out.println("Eliminando cliente de prueba...");
        boolean eliminado = clienteDAO.eliminarPorRut(rut);
        System.out.println(eliminado ? "[OK] Cliente eliminado" : "[FAIL] No se pudo eliminar");

        System.out.println("=== FIN PRUEBA CLIENTE CRUD ===");
    }

    private static String generarRutPrueba() {
        int base = 100000 + RND.nextInt(899999);
        return "99." + base + "-9";
    }
}

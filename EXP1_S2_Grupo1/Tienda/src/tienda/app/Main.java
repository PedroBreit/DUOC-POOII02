package tienda.app;

import tienda.model.Producto;
import tienda.decorator.*;
import tienda.command.*;

/**
 * Clase de entrada principal.
 * Demuestra el uso combinado de los patrones Singleton,
 * Decorator y Command en el sistema de pedidos.
 */

public class Main {
    public static void main(String[] args) {
        Producto camisa = new Producto("Camisa Oxford", "camisas", 20000);
        Producto jeans  = new Producto("Jeans Slim", "pantalones", 30000);

        // Definición de políticas de descuento (Decorator acumula porcentajes)
        Component politicaCamisa = new Descuento10(
                new DescuentoCategoria("camisas", 0.20, new PoliticaBase())
        );
        Component politicaJeans = new Descuento10(new PoliticaBase());

        Carrito carrito = new Carrito();
        Invocador inv = new Invocador();

        // Se crean y encolan los comandos
        inv.agregar(new AgregarProductoCommand(carrito, camisa, politicaCamisa));
        inv.agregar(new AgregarProductoCommand(carrito, jeans, politicaJeans));
        inv.agregar(new EliminarProductoCommand(carrito, "Jeans Slim"));

        // Ejecución de todos los comandos
        inv.ejecutarTodos();
    }
}

package tienda.app;

import tienda.decorator.*;
import tienda.singleton.*;
import tienda.command.*;

public class Main {
    public static void main(String[] args) {
        // Singleton
        DiscountManager gestorDescuentos = DiscountManager.getInstance();

        // Producto base
        Componente producto1 = new Producto("Camisa", 20000);

        // Decorator aplicado (10% + categoría 20%)
        Componente productoConDescuento = new Descuento10(new DescuentoCategoria(producto1));

        System.out.println(productoConDescuento.getDescripcion());
        System.out.println("Precio final: $" + productoConDescuento.getPrecio());

        // Command
        Carrito carrito = new Carrito();
        Command agregar = new AgregarProductoCommand(carrito, productoConDescuento);
        Command eliminar = new EliminarProductoCommand(carrito, productoConDescuento);

        Invocador invocador = new Invocador();
        invocador.agregarComando(agregar);
        invocador.agregarComando(eliminar);

        invocador.ejecutarComandos();
    }
}
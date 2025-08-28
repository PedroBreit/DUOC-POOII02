package tienda.vista;

import tienda.modelo.Pedido;
import tienda.modelo.Producto;

/* Lista lo que hay en el carrito y el total sin descuento. */
public class VistaCarrito {
    public void mostrarCarrito(Pedido pedido) {
    System.out.println("=== CARRITO de " + pedido.getUsuario().getNombre() + " ===");
    int i = 1;
    for (Producto p : pedido.getProductos()) {
        System.out.printf("%d) %s | %s | $%.0f%n", i++, p.getNombre(), p.getCategoria(), p.getPrecio());
    }
    System.out.println();
}

    public void mostrarTotalSinDescuento(double total) {
        System.out.printf("Total sin descuento: $%.0f%n%n", total);
    }
}

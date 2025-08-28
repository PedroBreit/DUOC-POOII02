package tienda.controlador;

import tienda.modelo.Pedido;
import tienda.modelo.Producto;

/* Control simple para no hablar directo con Pedido desde la vista. */
public class Productos {
    private final Pedido pedido;

    public Productos(Pedido pedido) {
        this.pedido = pedido;
    }

    public void agregarProducto(Producto producto) {
        pedido.agregarProducto(producto);
    }

    public void eliminarProducto(Producto producto) {
        pedido.eliminarProducto(producto);
    }
}

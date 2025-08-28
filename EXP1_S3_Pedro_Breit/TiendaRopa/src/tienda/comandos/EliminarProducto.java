package tienda.comandos;

import tienda.controlador.Productos;
import tienda.modelo.Producto;

/* Quita un producto. */
public class EliminarProducto implements Command {
    private final Productos ctrl;
    private final Producto producto;

    public EliminarProducto(Productos ctrl, Producto producto) {
        this.ctrl = ctrl;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        ctrl.eliminarProducto(producto);
        System.out.println("Eliminado del carrito: " + producto.getNombre());
    }
}

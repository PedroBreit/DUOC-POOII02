package tienda.comandos;

import tienda.controlador.Productos;
import tienda.modelo.Producto;

/* Suma un producto al pedido. */
public class AgregarProducto implements Command {
    private final Productos ctrl;
    private final Producto producto;

    public AgregarProducto(Productos ctrl, Producto producto) {
        this.ctrl = ctrl;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        ctrl.agregarProducto(producto);
        System.out.println("Agregado al carrito: " + producto.getNombre());
    }
}

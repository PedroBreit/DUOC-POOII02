package tienda.command;

/**
 * Comando concreto que encapsula la acción de eliminar
 * un producto del carrito por su nombre.
 */

public class EliminarProductoCommand implements Command {
    private final Carrito carrito;
    private final String nombreProducto;

    public EliminarProductoCommand(Carrito carrito, String nombreProducto) {
        this.carrito = carrito;
        this.nombreProducto = nombreProducto;
    }

    @Override
    public void Ejecutar() {
        carrito.eliminarPorNombre(nombreProducto);
    }
}

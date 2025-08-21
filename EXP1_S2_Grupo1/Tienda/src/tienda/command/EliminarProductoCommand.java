package tienda.command;

import tienda.decorator.Componente;

public class EliminarProductoCommand implements Command {
    private Carrito carrito;
    private Componente producto;

    public EliminarProductoCommand(Carrito carrito, Componente producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        carrito.eliminarProducto(producto);
    }
}
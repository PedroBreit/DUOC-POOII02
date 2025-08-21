package tienda.command;

import tienda.decorator.Componente;

public class AgregarProductoCommand implements Command {
    private Carrito carrito;
    private Componente producto;

    public AgregarProductoCommand(Carrito carrito, Componente producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        carrito.agregarProducto(producto);
    }
}
package tienda.command;

import tienda.decorator.Component;
import tienda.model.Producto;
import tienda.singleton.DiscountManager;

/**
 * Comando concreto que encapsula la acción de agregar
 * un producto al carrito aplicando los descuentos.
 */

public class AgregarProductoCommand implements Command {
    private final Carrito carrito;
    private final Producto producto;
    private final Component politica;

    public AgregarProductoCommand(Carrito carrito, Producto producto, Component politica) {
        this.carrito = carrito;
        this.producto = producto;
        this.politica = politica;
    }

    @Override
    public void Ejecutar() {
        double precioFinal = DiscountManager.getInstance()
                .calcularPrecioFinal(producto, politica);
        carrito.agregar(producto, precioFinal);
    }
}

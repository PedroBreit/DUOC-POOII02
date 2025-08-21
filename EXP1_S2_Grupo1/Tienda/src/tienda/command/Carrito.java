package tienda.command;

import tienda.decorator.Componente;

public class Carrito {
    public void agregarProducto(Componente producto) {
        System.out.println("Producto agregado: " + producto.getDescripcion());
    }

    public void eliminarProducto(Componente producto) {
        System.out.println("Producto eliminado: " + producto.getDescripcion());
    }
}
package tienda.command;

import tienda.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Receptor del patrón Command.
 * Representa el carrito de compras, donde se agregan o eliminan productos.
 */

public class Carrito {
    public static class Linea {
        public final Producto producto;
        public final double precioFinal;

        public Linea(Producto producto, double precioFinal) {
            this.producto = producto;
            this.precioFinal = precioFinal;
        }
    }

    private final List<Linea> lineas = new ArrayList<>();

    public void agregar(Producto p, double precioFinal) {
        lineas.add(new Linea(p, precioFinal));
        System.out.println("Agregado: " + p.getNombre() + " -> $" + String.format("%.2f", precioFinal));
    }

    public void eliminarPorNombre(String nombre) {
        lineas.removeIf(l -> l.producto.getNombre().equalsIgnoreCase(nombre));
        System.out.println("Eliminado: " + nombre);
    }

    public List<Linea> getLineas() { return lineas; }
}

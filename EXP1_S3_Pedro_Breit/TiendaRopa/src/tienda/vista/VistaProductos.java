package tienda.vista;

import tienda.modelo.Producto;

import java.util.List;

/* Muestra catálogo. */
public class VistaProductos {
    public void mostrarCatalogo(List<Producto> productos) {
        System.out.println("=== CATÁLOGO ===");
        for (int i = 0; i < productos.size(); i++) {
            var p = productos.get(i);
            System.out.printf("%d) %s | %s | $%.0f%n", i + 1, p.getNombre(), p.getCategoria(), p.getPrecio());
        }
        System.out.println();
    }
}


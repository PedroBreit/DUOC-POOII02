package tienda.vista;

/* Imprime el precio final con el nombre del producto. */
public class VistaDescuentos {
    public void mostrarPrecioFinal(String nombreProducto, double precioFinal) {
        System.out.printf("Precio final de '%s': $%.0f%n", nombreProducto, precioFinal);
    }
}

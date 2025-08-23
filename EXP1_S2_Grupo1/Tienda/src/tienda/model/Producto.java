package tienda.model;

/**
 * Clase del modelo que representa un Producto de la tienda.
 * Contiene los atributos básicos: nombre, categoría y precio base.
 * Es el objeto sobre el cual se aplican los descuentos.
 */

public class Producto {
    private final String nombre;
    private final String categoria;
    private final double precio;

    public Producto(String nombre, String categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
}
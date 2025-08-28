package tienda.modelo;

public class Producto implements Componente {
    private final String nombre;
    private final double precio;
    private final Categoria categoria;

    public Producto(String nombre, double precio, Categoria categoria) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("nombre vacío");
        if (precio < 0) throw new IllegalArgumentException("precio >= 0");
        if (categoria == null) throw new IllegalArgumentException("categoria null");
        this.nombre = nombre.trim();
        this.precio = precio;
        this.categoria = categoria;
    }

    @Override public double getPrecio()  { return precio; }
    @Override public String getNombre()  { return nombre; }
    @Override public Categoria getCategoria() { return categoria; }
}

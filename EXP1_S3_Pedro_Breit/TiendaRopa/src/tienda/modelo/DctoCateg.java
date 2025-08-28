package tienda.modelo;

/* Descuento solo si la categoría calza. Si no, deja el precio igual. */
public class DctoCateg extends Decorador {
    private final Categoria categoriaObjetivo;
    private final double porcentaje; // 0..1

    public DctoCateg(Componente componente, Categoria categoriaObjetivo, double porcentaje) {
        super(componente);
        if (categoriaObjetivo == null) throw new IllegalArgumentException("categoriaObjetivo null");
        if (porcentaje < 0 || porcentaje > 1) throw new IllegalArgumentException("porcentaje 0..1");
        this.categoriaObjetivo = categoriaObjetivo;
        this.porcentaje = porcentaje;
    }

    @Override
    public double getPrecio() {
        if (componente.getCategoria() == categoriaObjetivo) {
            return componente.getPrecio() * (1 - porcentaje);
        }
        return componente.getPrecio();
    }
}

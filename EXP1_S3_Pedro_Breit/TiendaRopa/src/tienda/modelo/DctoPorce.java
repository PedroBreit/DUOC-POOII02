package tienda.modelo;

/* Descuento porcentual directo. */
public class DctoPorce extends Decorador {
    private final double porcentaje; // 0..1

    public DctoPorce(Componente componente, double porcentaje) {
        super(componente);
        if (porcentaje < 0 || porcentaje > 1) throw new IllegalArgumentException("porcentaje 0..1");
        this.porcentaje = porcentaje;
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() * (1 - porcentaje);
    }
}
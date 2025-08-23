package tienda.decorator;

import tienda.model.Producto;

/**
 * Decorador concreto que añade un descuento adicional
 * si el producto pertenece a una categoría específica.
 */

public class DescuentoCategoria extends Decorator {
    private final String categoriaObjetivo;
    private final double porcentajeCategoria;

    public DescuentoCategoria(String categoriaObjetivo, double porcentajeCategoria, Component politica) {
        super(politica);
        this.categoriaObjetivo = categoriaObjetivo;
        this.porcentajeCategoria = porcentajeCategoria;
    }

    @Override
    public double getPorcentaje(Producto p) {
        double base = super.getPorcentaje(p);
        if (p.getCategoria().equalsIgnoreCase(categoriaObjetivo)) {
            return base + porcentajeCategoria;
        }
        return base;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + " + (int)(porcentajeCategoria * 100) + "% " + categoriaObjetivo;
    }
}

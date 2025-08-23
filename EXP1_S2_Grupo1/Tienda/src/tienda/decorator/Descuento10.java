package tienda.decorator;

import tienda.model.Producto;

/**
 * Decorador concreto que añade un 10% de descuento
 * a la política recibida como base.
 */

public class Descuento10 extends Decorator {
    public Descuento10(Component politica) {
        super(politica);
    }

    @Override
    public double getPorcentaje(Producto p) {
        return super.getPorcentaje(p) + 0.10;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + 10%";
    }
}

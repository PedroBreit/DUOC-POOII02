package tienda.decorator;

import tienda.model.Producto;

/**
 * Implementa la interfaz Component y contiene una referencia
 * a otra política. Sirve como base para los decoradores concretos.
 */

public abstract class Decorator implements Component {
    protected final Component politica;

    protected Decorator(Component politica) {
        this.politica = politica;
    }

    @Override
    public double getPorcentaje(Producto p) {
        return politica.getPorcentaje(p);
    }

    @Override
    public String getDescripcion() {
        return politica.getDescripcion();
    }
}

package patrones.decorator;

/**
 * Decorador abstracto del patrón Decorator.
 */

public abstract class DescuentoDecorator implements Descuento {

    protected final Descuento envoltorio;

    protected DescuentoDecorator(Descuento envoltorio) {
        this.envoltorio = envoltorio;
    }
    
    @Override
    public int aplicar(int precioBase) {
        return envoltorio.aplicar(precioBase);
    }
}

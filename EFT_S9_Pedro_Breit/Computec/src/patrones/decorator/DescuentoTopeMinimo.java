package patrones.decorator;

/**
 * Decorador concreto que asegura que el precio final nunca baje de un mínimo definido.
 */
public class DescuentoTopeMinimo extends DescuentoDecorator {

    private final int minimo;

    /**
     * Constructor: recibe el descuento base a envolver y el mínimo permitido.
     * 
     * Math.max asegura que el mínimo nunca sea negativo.
     */
    public DescuentoTopeMinimo(Descuento envoltorio, int minimo) {
        super(envoltorio);
        this.minimo = Math.max(0, minimo);
    }

    @Override
    public int aplicar(int precioBase) {
        // Aplicamos primero los descuentos anteriores
        int calc = super.aplicar(precioBase);

        // Si el precio calculado está bajo el mínimo, devolvemos el mínimo
        return Math.max(minimo, calc);
    }
}

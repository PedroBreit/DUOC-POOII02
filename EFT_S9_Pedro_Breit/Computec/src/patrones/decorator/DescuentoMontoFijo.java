package patrones.decorator;

/**
 * Decorador concreto que aplica un descuento de monto fijo.
 * 
 * - El monto se corrige para que nunca sea negativo.
 * - Primero aplica el descuento que envuelve,
 *   y luego le resta el monto fijo.
 */
public class DescuentoMontoFijo extends DescuentoDecorator {

    private final int monto; 
    
    /**
     * Constructor: recibe el descuento base que envuelve y
     * el monto fijo a restar.
     * 
     * Math.max asegura que el monto nunca sea menor a 0.
     */
    public DescuentoMontoFijo(Descuento envoltorio, int monto) {
        super(envoltorio);
        this.monto = Math.max(0, monto);
    }

    @Override
    public int aplicar(int precioBase) {
        // Primero aplicamos el descuento base
        int base = super.aplicar(precioBase);

        // Luego restamos el monto fijo, asegurando que nunca quede negativo
        return Math.max(0, base - monto);
    }
}

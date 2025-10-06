package patrones.decorator;

/**
 * Decorador concreto que aplica un porcentaje de descuento.
 */
public class DescuentoPorcentaje extends DescuentoDecorator {

    private final int porcentaje;

    /**
     * Constructor: recibe el descuento que envuelve y el porcentaje a aplicar.
     * 
     * Math.max y Math.min aseguran que el porcentaje nunca
     * sea menor que 0 ni mayor que 100.
     */
    public DescuentoPorcentaje(Descuento envoltorio, int porcentaje) {
        super(envoltorio);
        this.porcentaje = Math.max(0, Math.min(100, porcentaje));
    }

    @Override
    public int aplicar(int precioBase) {
        
        // Primero aplicamos el descuento base
        int base = super.aplicar(precioBase);

        // Calculamos el monto de rebaja según el porcentaje
        int rebaja = (base * porcentaje) / 100;

        // Retornamos el precio ya rebajado, asegurando que nunca sea negativo
        return Math.max(0, base - rebaja);
    }
}

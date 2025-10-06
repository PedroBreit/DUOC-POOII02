package patrones.decorator;

/**
 * Componente base del patrón Decorator.
 * Esta interfaz define el contrato para aplicar descuentos
 * sobre un precio base.
 */
public interface Descuento {

    int aplicar(int precioBase);
}

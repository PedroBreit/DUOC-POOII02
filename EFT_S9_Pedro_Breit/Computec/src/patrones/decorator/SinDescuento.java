package patrones.decorator;

/**
 * Implementación base del componente Descuento.
 * 
 * Este es el "precio sin descuento", es decir, devuelve
 * el mismo precio que recibe.
 * 
 * Forma parte del patrón Decorator:
 * - Es el punto de partida.
 * - A partir de aquí se pueden encadenar decoradores
 *   para ir modificando el precio.
 */
public class SinDescuento implements Descuento {

    @Override
    public int aplicar(int precioBase) {
        return Math.max(0, precioBase);
    }
}

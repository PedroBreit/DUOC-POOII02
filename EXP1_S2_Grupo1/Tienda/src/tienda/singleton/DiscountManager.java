package tienda.singleton;

import tienda.model.Producto;
import tienda.decorator.Component;

/**
 * Clase Singleton que centraliza la aplicación de los descuentos.
 * Garantiza que exista solo una instancia global que controla
 * cómo se calculan los precios finales a partir de las políticas de descuento.
 */

public class DiscountManager {
    private static DiscountManager instancia;

    // Límite máximo de descuento acumulado (75%)
    private static final double MAX_DESCUENTO = 0.75;

    private DiscountManager() {}

    public static DiscountManager getInstance() {
        if (instancia == null) {
            instancia = new DiscountManager();
        }
        return instancia;
    }

    // Calcula el precio final aplicando un porcentaje de descuento al producto.
    public double calcularPrecioFinal(Producto p, Component politica) {
        double porcentaje = politica.getPorcentaje(p);
        if (porcentaje < 0) porcentaje = 0;
        if (porcentaje > MAX_DESCUENTO) porcentaje = MAX_DESCUENTO;

        double precioBase = p.getPrecio();
        return precioBase * (1.0 - porcentaje);
    }
}

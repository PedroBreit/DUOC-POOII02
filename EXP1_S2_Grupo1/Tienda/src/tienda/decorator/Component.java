package tienda.decorator;

import tienda.model.Producto;

/**
 * Interfaz que define el contrato
 * de las políticas de descuento. Su responsabilidad es devolver
 * el porcentaje bruto de descuento y una descripción de la regla.
 */

public interface Component {
    double getPorcentaje(Producto p);
    String getDescripcion();
}

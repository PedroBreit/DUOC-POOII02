package tienda.decorator;

import tienda.model.Producto;

/**
 * Política base: no aplica ningún descuento.
 * Representa el caso por defecto antes de agregar decoradores.
 */

public class PoliticaBase implements Component {
    @Override
    public double getPorcentaje(Producto p) { return 0.0; }

    @Override
    public String getDescripcion() { return "Sin descuento"; }
}

package patrones.command;

import servicio.VentaServicio;

/**
 * Comando concreto: eliminar una venta.
 * Este comando encapsula la acción de eliminar una venta del sistema. 
 */
public class EliminarVentaComando implements Comando {

    private final VentaServicio ventaServicio;
    private final int idVenta;

    public EliminarVentaComando(VentaServicio ventaServicio, int idVenta) {
        this.ventaServicio = ventaServicio;
        this.idVenta = idVenta;
    }

    @Override
    public boolean ejecutar() {
        return ventaServicio.eliminarVenta(idVenta);
    }
}

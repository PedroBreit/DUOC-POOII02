package patrones.command;

import servicio.VentaServicio;

/**
 * Comando concreto: registrar una venta.
 * Este comando encapsula toda la información necesaria para registrar una venta.
 * De esta forma, cuando se llama a ejecutar(),
 * el comando delega la acción al servicio de ventas.
 */
public class AgregarVentaDetalladaComando implements Comando {

    private final VentaServicio ventaServicio;
    private final String rutCliente;
    private final int idEquipo;
    private final int precioFinal;
    private final String descuento;
    


    public AgregarVentaDetalladaComando(VentaServicio ventaServicio, String rutCliente, int idEquipo, int precioFinal, String descuento) {
        this.ventaServicio = ventaServicio;
        this.rutCliente = rutCliente;
        this.idEquipo = idEquipo;
        this.precioFinal = precioFinal;
        this.descuento = descuento;
    }

    @Override
    public boolean ejecutar() {
        return ventaServicio.registrarVentaDetallada(rutCliente, idEquipo, precioFinal, descuento);
    }
}

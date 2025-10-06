package servicio;

import dao.VentaDAO;
import dao.jdbc.VentaDAOJDBC;
import modelo.Venta;

/**
 * Servicio de dominio para ventas.
 * 
 * Aquí aplicamos el patrón Command:
 * - Este servicio actúa como Receiver.
 * - Los comandos (AgregarVentaComando, EliminarVentaComando) delegan en este servicio la ejecución real.
 */
public class VentaServicio {
    
    private final VentaDAO ventaDAO = new VentaDAOJDBC();
    
    private String ultimoError;
    public String getUltimoError() { return ultimoError; }
    
    /**
     * Registra una venta.
     */
    public boolean registrarVentaDetallada(String rutCliente, int idEquipo, int precioFinal, String descuento) {
        Venta v = new Venta();
        v.setRutCliente(rutCliente);
        v.setIdEquipo(idEquipo);
        v.setPrecioFinal(precioFinal);
        v.setDescuentoAplicado(descuento == null ? "Sin descuento" : descuento);

        boolean ok = ventaDAO.crear(v);
        return ok;
    }

    /**
     * Elimina una venta por su id.
     */
    public boolean eliminarVenta(int idVenta) {
        if (idVenta <= 0) {
            System.out.println("Id de venta inválido.");
            return false;
        }
        return ventaDAO.eliminarPorId(idVenta);
    }
}

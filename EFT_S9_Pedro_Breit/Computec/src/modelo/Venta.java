package modelo;

import java.time.LocalDateTime;

public class Venta {
    private Integer idVenta;
    private String rutCliente;
    private Integer idEquipo;
    private LocalDateTime fechaHora;
    private int precioFinal;
    private String descuentoAplicado;

    public Integer getIdVenta() { return idVenta; }
    public void setIdVenta(Integer idVenta) { this.idVenta = idVenta; }

    public String getRutCliente() { return rutCliente; }
    public void setRutCliente(String rutCliente) { this.rutCliente = rutCliente; }

    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public int getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(int precioFinal) { this.precioFinal = precioFinal; }
    
    public String getDescuentoAplicado() { return descuentoAplicado; }
    public void setDescuentoAplicado(String descuentoAplicado){this.descuentoAplicado = descuentoAplicado;}
}

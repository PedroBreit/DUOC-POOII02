package tienda.controlador;

import tienda.modelo.Pedido;

/* Saca totales y expone lo justo del pedido. */
public class Pedidos {
    private final Pedido pedido;

    public Pedidos(Pedido pedido) {
        this.pedido = pedido;
    }

    public double totalSinDescuento() {
        return pedido.totalSinDescuento();
    }

    public Pedido getPedido() {
        return pedido;
    }
}

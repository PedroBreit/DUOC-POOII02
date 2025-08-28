package tienda.comandos;

import tienda.controlador.Descuentos;
import tienda.modelo.Componente;
import tienda.modelo.DctoPorce;

/* Aplica un % directo al producto y muestra el total. */
public class AplicarDctoPorce implements Command {
    private final Descuentos controlador;
    private final Componente productoBase;
    private final double porcentaje; // 0..1
    private final java.util.function.Consumer<Double> callbackResultado;

    public AplicarDctoPorce(Descuentos controlador,
                            Componente productoBase,
                            double porcentaje,
                            java.util.function.Consumer<Double> callbackResultado) {
        this.controlador = controlador;
        this.productoBase = productoBase;
        this.porcentaje = porcentaje;
        this.callbackResultado = callbackResultado;
    }

    @Override
    public void ejecutar() {
        Componente conDescuento = new DctoPorce(productoBase, this.porcentaje);
        double total = controlador.aplicarDescuento(conDescuento);
        callbackResultado.accept(total);
    }
}

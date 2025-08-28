package tienda.comandos;

import tienda.controlador.Descuentos;
import tienda.modelo.Categoria;
import tienda.modelo.Componente;
import tienda.modelo.DctoCateg;

/* Igual que AplicarDctoPorce, pero se activa solo si la categoría coincide. */
public class AplicarDctoCateg implements Command {
    private final Descuentos controlador;
    private final Componente productoBase;
    private final Categoria categoriaObjetivo;
    private final double porcentaje;
    private final java.util.function.Consumer<Double> callbackResultado;

    public AplicarDctoCateg(Descuentos controlador,
                            Componente productoBase,
                            Categoria categoriaObjetivo,
                            double porcentaje,
                            java.util.function.Consumer<Double> callbackResultado) {
        this.controlador = controlador;
        this.productoBase = productoBase;
        this.categoriaObjetivo = categoriaObjetivo;
        this.porcentaje = porcentaje;
        this.callbackResultado = callbackResultado;
    }

    @Override
    public void ejecutar() {
        Componente conDescuento = new DctoCateg(productoBase, this.categoriaObjetivo, this.porcentaje);
        double total = controlador.aplicarDescuento(conDescuento);
        callbackResultado.accept(total);
    }
}

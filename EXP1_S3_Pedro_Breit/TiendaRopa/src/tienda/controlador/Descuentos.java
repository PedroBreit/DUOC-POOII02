package tienda.controlador;

import tienda.modelo.Componente;
import tienda.modelo.DiscountManager;

/* Router de descuentos: manda todo al Singleton para que cierre el precio. */
public class Descuentos {
    private final DiscountManager manager = DiscountManager.getInstance();

    public double aplicarDescuento(Componente componenteDecorado) {
        return manager.aplicarDescuento(componenteDecorado);
    }
}


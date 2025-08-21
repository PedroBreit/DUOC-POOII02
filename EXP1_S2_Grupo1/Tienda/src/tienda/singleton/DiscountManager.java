package tienda.singleton;

public class DiscountManager {
    private static DiscountManager instancia;

    private DiscountManager() {}

    public static DiscountManager getInstance() {
        if (instancia == null) {
            instancia = new DiscountManager();
        }
        return instancia;
    }

    public double aplicarDescuento(double precio, double descuento) {
        return precio - (precio * descuento);
    }
}
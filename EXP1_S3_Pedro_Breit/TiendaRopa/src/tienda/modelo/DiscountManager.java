package tienda.modelo;

/* 
 * Singleton simple. La idea es que todos pasen por acá para concretar el precio
 * después de poner los decoradores que quieran.
 */
public class DiscountManager {
    private static DiscountManager instancia;

    private DiscountManager() { }

    public static DiscountManager getInstance() {
        if (instancia == null) {
            instancia = new DiscountManager();
        }
        return instancia;
    }

    /* Si el componente ya viene decorado, acá solo cerramos la cuenta. */
    public double aplicarDescuento(Componente componente) {
        if (componente == null) throw new IllegalArgumentException("componente null");
        return componente.getPrecio();
    }
}
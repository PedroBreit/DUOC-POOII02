package discountmanager;

public class DiscountManager {

    // 2. Instancia privada, estática y final
    private static final DiscountManager instance = new DiscountManager();

    // 1. Constructor privado para evitar instanciación externa
    private DiscountManager() {}

    // 3. Método estático para obtener la única instancia
    public static DiscountManager getInstance() {
        return instance;
    }
}

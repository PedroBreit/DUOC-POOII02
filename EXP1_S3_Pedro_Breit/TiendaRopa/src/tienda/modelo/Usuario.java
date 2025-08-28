package tienda.modelo;

/* Usuario mínimo para la demo. Por ahora solo nombre. */
public class Usuario {
    private final String nombre;

    public Usuario(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("nombre vacío");
        this.nombre = nombre.trim();
    }

    public String getNombre() { return nombre; }
}

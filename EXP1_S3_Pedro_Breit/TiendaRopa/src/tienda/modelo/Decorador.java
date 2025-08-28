package tienda.modelo;

/* Clase base para los descuentos. */
public abstract class Decorador implements Componente {
    protected final Componente componente;

    protected Decorador(Componente componente) {
        if (componente == null) throw new IllegalArgumentException("componente no puede ser null");
        this.componente = componente;
    }

    @Override public double getPrecio()     { return componente.getPrecio(); }
    @Override public String getNombre()     { return componente.getNombre(); }
    @Override public Categoria getCategoria(){ return componente.getCategoria(); }
}

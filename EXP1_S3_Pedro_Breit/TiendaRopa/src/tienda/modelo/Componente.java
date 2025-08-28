package tienda.modelo;

/* Contrato base para que los decoradores se puedan encadenar. */
public interface Componente {
    double getPrecio();
    String getNombre();
    Categoria getCategoria();
}

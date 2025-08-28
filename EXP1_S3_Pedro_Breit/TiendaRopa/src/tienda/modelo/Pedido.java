package tienda.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Carrito: guarda productos y saca el total. */
public class Pedido {
    
    private final Usuario usuario;
    private final List<Producto> productos = new ArrayList<>();

    public Pedido(Usuario usuario) {           
        if (usuario == null) throw new IllegalArgumentException("usuario null");
        this.usuario = usuario;
    }
    
    public Usuario getUsuario() {            
        return usuario;
    }
    
    public void agregarProducto(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("producto null");
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    public double totalSinDescuento() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }
}

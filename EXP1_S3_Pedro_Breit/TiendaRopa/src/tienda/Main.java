package tienda;

import tienda.comandos.*;
import tienda.controlador.*;
import tienda.modelo.*;
import tienda.vista.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // ==== Setup básico ====
        Usuario usuario = new Usuario("Pedro");
        Pedido pedido = new Pedido(usuario);

        Producto p1 = new Producto("Polera básica", 12990, Categoria.POLERAS);
        Producto p2 = new Producto("Pantalón cargo", 25990, Categoria.PANTALONES);
        Producto p3 = new Producto("Zapatillas urbanas", 39990, Categoria.ZAPATOS);

        // Vistas
        VistaProductos vistaProductos = new VistaProductos();
        VistaCarrito vistaCarrito = new VistaCarrito();
        VistaDescuentos vistaDesc = new VistaDescuentos();

        // Controladores
        Productos ctrlProductos = new Productos(pedido);
        Pedidos ctrlPedidos = new Pedidos(pedido);
        Descuentos ctrlDescuentos = new Descuentos();

        // Catálogo
        vistaProductos.mostrarCatalogo(List.of(p1, p2, p3));

        // Carrito (commands)
        new AgregarProducto(ctrlProductos, p1).ejecutar();
        new AgregarProducto(ctrlProductos, p2).ejecutar();
        vistaCarrito.mostrarCarrito(pedido);
        vistaCarrito.mostrarTotalSinDescuento(ctrlPedidos.totalSinDescuento());

        // Descuentos (commands con callback -> imprime en vista)
        new AplicarDctoPorce( ctrlDescuentos, p1, 0.10, total -> vistaDesc.mostrarPrecioFinal(p1.getNombre(), total)).ejecutar();

        new AplicarDctoCateg(ctrlDescuentos, p2, Categoria.PANTALONES, 0.20,total -> vistaDesc.mostrarPrecioFinal(p2.getNombre(), total)).ejecutar();

        // Un tercero sin descuento de categoría
        new AplicarDctoPorce(ctrlDescuentos, p3, 0.10, total -> vistaDesc.mostrarPrecioFinal(p3.getNombre(), total)).ejecutar();
    }
}

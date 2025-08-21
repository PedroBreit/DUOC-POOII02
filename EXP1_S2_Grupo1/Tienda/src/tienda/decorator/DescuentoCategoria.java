package tienda.decorator;

public class DescuentoCategoria extends DescuentoDecorator {
    public DescuentoCategoria(Componente componente) {
        super(componente);
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() * 0.80;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " + Descuento Categoría 20%";
    }
}
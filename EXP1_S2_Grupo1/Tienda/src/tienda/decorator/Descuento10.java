package tienda.decorator;

public class Descuento10 extends DescuentoDecorator {
    public Descuento10(Componente componente) {
        super(componente);
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() * 0.90;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " + Descuento 10%";
    }
}
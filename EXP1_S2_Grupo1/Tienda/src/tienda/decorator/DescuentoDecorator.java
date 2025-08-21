package tienda.decorator;

public abstract class DescuentoDecorator implements Componente {
    protected Componente componente;

    public DescuentoDecorator(Componente componente) {
        this.componente = componente;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion();
    }
}
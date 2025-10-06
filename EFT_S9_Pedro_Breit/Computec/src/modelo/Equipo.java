package modelo;

/**
 * Clase abstracta que representa un equipo (computador) genérico.
 * 
 * Esta clase se basa en la tabla EQUIPO y es la "madre" de Desktop y Laptop.
 * Al ser abstracta, no se puede instanciar directamente (solo a través de sus hijos).
 **/
public abstract class Equipo {
    protected Integer idEquipo;
    protected String modelo;
    protected String cpu;
    protected int discoGb;
    protected int ramGb;
    protected int precio;

    // Getters y setters
    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }

    public int getDiscoGb() { return discoGb; }
    public void setDiscoGb(int discoGb) { this.discoGb = discoGb; }

    public int getRamGb() { return ramGb; }
    public void setRamGb(int ramGb) { this.ramGb = ramGb; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }
}


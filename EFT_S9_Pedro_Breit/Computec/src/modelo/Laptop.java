package modelo;

/**
 * Representa un equipo de tipo Laptop.
 * Hereda los atributos comunes de Equipo y agrega sus propios campos
 */
public class Laptop extends Equipo {
    private double tamPantallaPulg;
    private boolean esTouch;
    private int puertosUsb;

    public double getTamPantallaPulg() { return tamPantallaPulg; }
    public void setTamPantallaPulg(double tamPantallaPulg) { this.tamPantallaPulg = tamPantallaPulg; }

    public boolean isEsTouch() { return esTouch; }
    public void setEsTouch(boolean esTouch) { this.esTouch = esTouch; }

    public int getPuertosUsb() { return puertosUsb; }
    public void setPuertosUsb(int puertosUsb) { this.puertosUsb = puertosUsb; }
}

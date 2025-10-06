package modelo;

/**
 * Representa un equipo de tipo Desktop (escritorio).
 * Hereda los atributos comunes de Equipo y agrega sus propios campos
 */
public class Desktop extends Equipo {
    private int potenciaFuenteW;
    private String placa;

    public int getPotenciaFuenteW() { return potenciaFuenteW; }
    public void setPotenciaFuenteW(int potenciaFuenteW) { this.potenciaFuenteW = potenciaFuenteW; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
}

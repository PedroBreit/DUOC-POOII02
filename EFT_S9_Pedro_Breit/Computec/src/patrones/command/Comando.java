package patrones.command;

/**
 * Interfaz base del patrón Command.
 * El patrón Command nos permite encapsular una acción dentro de un objeto.
 */
public interface Comando {
    boolean ejecutar();
}
package patrones.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker del patrón Command.
 * 
 * Este gestor se encarga de:
 * - Ejecutar un comando recibido.
 * - Guardar en un historial los comandos ejecutados.
 */
public class GestorComandos {

    private final Deque<Comando> historial = new ArrayDeque<>();

    public boolean ejecutar(Comando cmd) {
        boolean ok = cmd.ejecutar();
        if (ok) historial.push(cmd);
        return ok;
    }

    /**
     * Retorna el total de comandos ejecutados con éxito.
     */
    public int getTotalEjecutados() {
        return historial.size();
    }
}

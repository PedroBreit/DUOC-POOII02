package tienda.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Invocador del patrón Command.
 * Contiene una lista de comandos y los ejecuta en orden.
 */

public class Invocador {
    private final List<Command> cola = new ArrayList<>();

    public void agregar(Command c) { cola.add(c); }

    public void ejecutarTodos() {
        for (Command c : cola) c.Ejecutar();
        cola.clear();
    }
}

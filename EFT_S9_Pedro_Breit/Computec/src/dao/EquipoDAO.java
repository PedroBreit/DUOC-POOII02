package dao;

import modelo.Equipo;
import modelo.Desktop;
import modelo.Laptop;

import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos para equipos.
 * 
 * - Un equipo siempre es Desktop o Laptop.
 * - Por eso hay métodos separados para crear/actualizar cada tipo.
 */
public interface EquipoDAO {

    boolean crearDesktop(Desktop d);
    boolean crearLaptop(Laptop l);

    boolean actualizarDesktop(Desktop d);
    boolean actualizarLaptop(Laptop l);


    Optional<Equipo> buscarPorId(int idEquipo);
    
    List<Equipo> listarTodos();

    boolean eliminarPorId(int idEquipo);
}

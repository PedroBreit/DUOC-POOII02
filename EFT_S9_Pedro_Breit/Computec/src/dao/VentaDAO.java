package dao;

import modelo.Venta;
import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos para la tabla VENTA.
 */
public interface VentaDAO {

    boolean crear(Venta v);
    
    Optional<Venta> buscarPorId(int id);

    List<Venta> listarTodas();

    boolean actualizar(Venta v);
    
    boolean eliminarPorId(int id); 
}

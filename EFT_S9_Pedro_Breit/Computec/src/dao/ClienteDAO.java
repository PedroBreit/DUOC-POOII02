package dao;

import modelo.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteDAO {
    // Create
    boolean crear(Cliente c);
    
    // Update
    boolean actualizar(Cliente c);
    
    // Delete
    boolean eliminarPorRut(String rut);
    
    //Read
    Optional<Cliente> buscarPorRut(String rut);
    List<Cliente> listarTodos();
}

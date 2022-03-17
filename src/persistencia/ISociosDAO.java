package persistencia;

import entidades.Socio;
import java.util.List;

public interface ISociosDAO {
    public boolean agregar(Socio socio);
    public boolean actualizar(Socio socio);
    public boolean eliminar(Long idSocio);
    public Socio consultar(Long idSocio);
    public List<Socio> consultarTodos();
}

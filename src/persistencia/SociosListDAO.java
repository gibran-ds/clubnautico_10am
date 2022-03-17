
package persistencia;

import entidades.Socio;
import java.util.ArrayList;
import java.util.List;

public class SociosListDAO implements ISociosDAO {

    private final List<Socio> socios;
    
    public SociosListDAO() {
        this.socios = new ArrayList<>();
    }
    
    @Override
    public boolean agregar(Socio socio) {
        socio.setIdSocio((long)(this.socios.size()+1));
        this.socios.add(socio);
        return true;
    }

    @Override
    public boolean actualizar(Socio socio) {
        int indice = this.socios.indexOf(socio);
        if(indice == -1){
            return false;
        }
        Socio socioGuardado = this.socios.get(indice);
        socioGuardado.setNombre(socio.getNombre());
        socioGuardado.setCurp(socio.getCurp());
        return true;
    }

    @Override
    public boolean eliminar(Long idSocio) {
        return this.socios.remove(new Socio(idSocio));
    }

    @Override
    public Socio consultar(Long idSocio) {
        int indice = this.socios.indexOf(new Socio(idSocio));
        if(indice == -1){
            return null;
        }
        Socio socioGuardado = this.socios.get(indice);
        return socioGuardado;
    }

    @Override
    public List<Socio> consultarTodos() {
        return this.socios;
    }
    
}

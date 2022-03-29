
package apirest;

import entidades.Socio;
import java.util.LinkedList;
import java.util.List;

public class SociosDummyAPIRest implements ISociosAPIRest {
    
    private List<Socio> socios;

    public SociosDummyAPIRest() {
        this.socios = new LinkedList<>();
        this.socios.add(new Socio(1L, "John", "ASD1515654"));
        this.socios.add(new Socio(2L, "Steve", "STE848745"));
        this.socios.add(new Socio(3L, "Bruce", "BRE541205"));
    }

    @Override
    public List<Socio> obtenerSocios() {
        return this.socios;
    }
}

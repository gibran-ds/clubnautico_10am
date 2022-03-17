package entidades;

import java.util.Objects;

public class Socio {
    private Long idSocio;
    private String nombre;
    private String curp;

    public Socio() {
    }

    public Socio(Long idSocio) {
        this.idSocio = idSocio;
    }

    public Socio(String nombre, String curp) {
        this.nombre = nombre;
        this.curp = curp;
    }

    public Socio(Long idSocio, String nombre, String curp) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.curp = curp;
    }

    public Long getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Long idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idSocio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Socio other = (Socio) obj;
        return Objects.equals(this.idSocio, other.idSocio);
    }

    @Override
    public String toString() {
        return "Socio{" + "idSocio=" + idSocio + ", nombre=" + nombre + ", curp=" + curp + '}';
    }        
}

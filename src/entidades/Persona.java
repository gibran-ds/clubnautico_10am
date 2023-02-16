package entidades;

public class Persona {
    private Long idPersona;
    private String nombre;
    private String correo;

    public Persona(Long idPersona, String nombre) {
        this.idPersona = idPersona;
        this.nombre = nombre;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
}

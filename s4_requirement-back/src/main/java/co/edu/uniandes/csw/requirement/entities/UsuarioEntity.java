package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

 /**
 *
 * Atributos de la clase
 */
    private String tipo;
    private String usuario;
    private String contrasena;

   

//Relacion con proyecto
    //@PodamExclude
    //@ManyToOne
    //private ProyectoEntity proyecto;
    
    
    //TODO: MATEO FALTA RELACION CON PROYECTO, ES MANY TO MANY.
    public UsuarioEntity() {

    }

    
//Getter and Setters
    
    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }    
    
    //Getter y Setter de proyecto
}

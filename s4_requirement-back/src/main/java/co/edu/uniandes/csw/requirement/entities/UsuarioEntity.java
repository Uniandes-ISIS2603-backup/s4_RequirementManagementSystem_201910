package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;


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
    
    @PodamExclude
    @ManyToMany
    private List<ProyectoEntity> proyectos = new ArrayList<ProyectoEntity>();

   
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

    public List<ProyectoEntity> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<ProyectoEntity> organizacion) {
        this.proyectos = organizacion;
    }
    
}

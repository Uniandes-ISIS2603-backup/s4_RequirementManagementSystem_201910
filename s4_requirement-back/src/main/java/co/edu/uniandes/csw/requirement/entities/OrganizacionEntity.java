package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class OrganizacionEntity extends BaseEntity implements Serializable {

    /**
     * Atributos
     */
    private String sector;
    private String nombre;

    /**
     * Relacion con stakeholders
     */
    @PodamExclude
    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<StakeHolderEntity> stakeholders = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<ProyectoEntity> proyectos = new ArrayList<ProyectoEntity>();
    /**
     * Relacion con stakeholders
     */
    @PodamExclude
    @ManyToOne
    private ProyectoEntity proyecto;

//construcctor vacio
    public OrganizacionEntity() {
    }

//Getters y Setters
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<StakeHolderEntity> getStakeHolders() {
        return stakeholders;
    }

    public void setStakeHolders(List<StakeHolderEntity> lista) {
        stakeholders = lista;
    }

    public ProyectoEntity getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

    public List<ProyectoEntity> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<ProyectoEntity> organizacion) {
        this.proyectos = organizacion;
    }
}

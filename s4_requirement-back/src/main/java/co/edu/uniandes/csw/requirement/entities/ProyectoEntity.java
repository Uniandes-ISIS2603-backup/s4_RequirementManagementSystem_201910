/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Alvarez, Jorge Esguerra, David Manosalva
 */
@Entity
public class ProyectoEntity extends BaseEntity implements Serializable {

    /**
     * Atributos de clase proyecto, se hereda id de baseEntity
     */
    private String nombre, descripcion;

    @PodamExclude
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ObjetivoEntity> objetivos = new ArrayList<ObjetivoEntity>();

    /*
        @PodamExclude
    @ManyToMany(mappedBy= "proyectos")
    private List<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();
     */
    @PodamExclude
    @ManyToMany(mappedBy = "proyectos")
    private List<OrganizacionEntity> organizaciones = new ArrayList<OrganizacionEntity>();

    @PodamExclude
    @ManyToMany(mappedBy = "proyectos")
    private List<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the objetivos
     */
    public List<ObjetivoEntity> getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(List<ObjetivoEntity> objetivos) {
        this.objetivos = objetivos;
    }

    public List<OrganizacionEntity> getOrganizaciones() {
        return organizaciones;
    }

    public void setOrganizaciones(List<OrganizacionEntity> organizacion) {
        this.organizaciones = organizacion;
    }

    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }
    
}

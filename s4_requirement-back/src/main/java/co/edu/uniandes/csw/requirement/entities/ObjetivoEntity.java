/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author davidmanosalva
 */
@Entity
public class ObjetivoEntity extends BaseEntity implements Serializable {

    private String descripcion;
    private Integer importancia;
    private Integer estabilidad;
    private String comentarios;
    private String autor;
    private List<String> fuentes;

    @PodamExclude
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CambioEntity> cambios = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AprobacionEntity> aprobaciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RequisitoEntity> requisitos;

//    @PodamExclude
//    @OneToOne(mappedBy = "autorObjetivo", fetch = FetchType.LAZY)
//    private StakeHolderEntity autor;
//
//    @PodamExclude
//    @OneToMany(mappedBy = "fuenteObjetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<StakeHolderEntity> fuentes = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private ProyectoEntity proyecto;

    /**
     * Constructor vacio para implementacion REST
     */
    public ObjetivoEntity() {

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
     * @return the importancia
     */
    public Integer getImportancia() {
        return importancia;
    }

    /**
     * @param importancia the importancia to set
     */
    public void setImportancia(Integer importancia) {
        this.importancia = importancia;
    }

    /**
     * @return the estabilidad
     */
    public Integer getEstabilidad() {
        return estabilidad;
    }

    /**
     * @param estabilidad the estabilidad to set
     */
    public void setEstabilidad(Integer estabilidad) {
        this.estabilidad = estabilidad;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

  

    /**
     * @return the requisitos
     */
    public List<RequisitoEntity> getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos the requisitos to set
     */
    public void setRequisitos(List<RequisitoEntity> requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return the fuentes
     */
    public List<String> getFuentes() {
        return fuentes;
    }

    /**
     * @param fuentes the fuentes to set
     */
    public void setFuentes(List<String> fuentes) {
        this.fuentes = fuentes;
    }

    public void setProyecto(ProyectoEntity proyecto) {
       this.proyecto = proyecto;
    }

    /**
     * @return the proyecto
     */
    public ProyectoEntity getProyecto() {
        return proyecto;
    }

    public List<CambioEntity> getCambios() {
        return cambios;
    }

    /**
     * @param cambios the cambios to set
     */
    public void setCambios(List<CambioEntity> cambios) {
        this.cambios = cambios;
    }
    /**
     * @return the aprobaciones
     */
    public List<AprobacionEntity> getAprobaciones() {
        return aprobaciones;
    }

    /**
     * @param aprobaciones the aprobaciones to set
     */
    public void setAprobaciones(List<AprobacionEntity> aprobaciones) {
        this.aprobaciones = aprobaciones;
    }
    
    
}

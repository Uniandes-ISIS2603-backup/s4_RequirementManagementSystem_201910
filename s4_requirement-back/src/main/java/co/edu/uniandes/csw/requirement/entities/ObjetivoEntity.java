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

    @PodamExclude
    @ManyToOne
    private DRSEntity drs;

    @PodamExclude
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CambioEntity> cambios = new ArrayList<CambioEntity>();

    @PodamExclude
    @OneToMany(mappedBy = "objetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AprobacionEntity> aprobaciones = new ArrayList<AprobacionEntity>();

    @PodamExclude
    @ManyToMany(mappedBy = "objetivos")
    private List<RequisitoEntity> requisitos;

    @PodamExclude
    @OneToOne(mappedBy = "autorObjetivo", fetch = FetchType.LAZY)
    private StakeHolderEntity autor;

    @PodamExclude
    @OneToMany(mappedBy = "fuenteObjetivo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<StakeHolderEntity> fuentes = new ArrayList<>();

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
     * @return the drs
     */
    public DRSEntity getDrs() {
        return drs;
    }

    /**
     * @param drs the drs to set
     */
    public void setDrs(DRSEntity drs) {
        this.drs = drs;
    }

    /**
     * @return the cambios
     */
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
    public StakeHolderEntity getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(StakeHolderEntity autor) {
        this.autor = autor;
    }

    /**
     * @return the fuentes
     */
    public List<StakeHolderEntity> getFuentes() {
        return fuentes;
    }

    /**
     * @param fuentes the fuentes to set
     */
    public void setFuentes(List<StakeHolderEntity> fuentes) {
        this.fuentes = fuentes;
    }

}

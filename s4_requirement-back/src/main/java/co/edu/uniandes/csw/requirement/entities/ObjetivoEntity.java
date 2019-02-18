/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author davidmanosalva
 */
@Entity
public class ObjetivoEntity extends BaseEntity implements Serializable
{
    
    private String autor;
    private String fuente;
    private String descripcion;
    private Integer importancia;
    private Integer estabilidad;
    private String comentarios;
    
    @PodamExclude
    @ManyToOne
    private DRSEntity drs;
    
    @PodamExclude
    @OneToMany(mappedBy="objetivo", orphanRemoval = true)
    private List<CambioEntity> cambios = new ArrayList<CambioEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy="objetivo", orphanRemoval = true)
    private List<AprobacionEntity> aprobaciones= new ArrayList<AprobacionEntity>();
    
    @ManyToMany(mappedBy="objetivos")
    private List<RequisitoEntity> requisitos;
    
    public ObjetivoEntity()
    {
        
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
     * @return the fuente
     */
    public String getFuente() {
        return fuente;
    }

    /**
     * @param fuente the fuente to set
     */
    public void setFuente(String fuente) {
        this.fuente = fuente;
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
    
    
    
}

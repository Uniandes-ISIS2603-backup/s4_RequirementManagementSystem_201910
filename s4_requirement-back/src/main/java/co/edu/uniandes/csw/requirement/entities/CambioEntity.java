/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Esta clase es la entidad para el recurso de cambio.
 * @author Sofia Alvarez
 */
@Entity
public class CambioEntity extends BaseEntity implements Serializable {

    /**
     * Fecha y hora de creación del cambio
     */
    private String fechaYHora;

    /**
     * Descripcion del cambio
     */
    private String descripcion;
    
    /**
     * Tipo del cambio, puede ser MODIFICACION, ELIMINACION, APROBACION
     */
    private String tipo;
    
    /**
     * Relación many to one con stakeholder.
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StakeHolderEntity stakeholder;
    
    /**
     * Relación many to one con objetivo
     */
    @PodamExclude
    @ManyToOne()
    private ObjetivoEntity objetivo;
    
    /**
     * Relación many to one con requisito
     */
    @PodamExclude
    @ManyToOne()
    private RequisitoEntity requisito;
    
     /**
     * Constructor vacio
     */
    public CambioEntity() {

    }

    /**
     * @return the fechaYHora
     */
    public String getFechaYHora() {
        return fechaYHora;
    }

    /**
     * @param fechaYHora the fechaYHora to set
     */
    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
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
     * @return the stakeholder
     */
    public StakeHolderEntity getStakeholder() {
        return stakeholder;
    }

    /**
     * @param stakeholder the stakeholder to set
     */
    public void setStakeholder(StakeHolderEntity stakeholder) {
        this.stakeholder = stakeholder;
    }

    /**
     * @return the objetivo
     */
    public ObjetivoEntity getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(ObjetivoEntity objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the requisito
     */
    public RequisitoEntity getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(RequisitoEntity requisito) {
        this.requisito = requisito;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}









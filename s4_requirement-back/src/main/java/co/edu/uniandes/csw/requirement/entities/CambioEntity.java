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
     * Tipo del cambio
     */
    private String tipo;

    /**
     * Fecha y hora de creación del cambio
     */
    private String fechaYHora;

    /**
     * Descripcion del cambio
     */
    private String descripcion;
    
    /**
     * Autor del cambio
     */
    private String autor;
    
    /**
     * Organización a la cual pertenece el autor del cambio
     */
    private String organizacion;
    
    /**
     * Id del requisito/objetivo aprobado
     */
    private String id_aprobado;
    
    /**
     * Nombre del requisito/objetivo aprobado
     */
    private String nombre_aprobado;
    
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
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ObjetivoEntity objetivo;
    
    /**
     * Relación many to one con requisito
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private RequisitoEntity requisito;
    
     /**
     * Constructor vacio
     */
    public CambioEntity() {

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
     * @return the organizacion
     */
    public String getOrganizacion() {
        return organizacion;
    }

    /**
     * @param organizacion the organizacion to set
     */
    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    /**
     * @return the id_aprobado
     */
    public String getId_aprobado() {
        return id_aprobado;
    }

    /**
     * @param id_aprobado the id_aprobado to set
     */
    public void setId_aprobado(String id_aprobado) {
        this.id_aprobado = id_aprobado;
    }

    /**
     * @return the nombre_aprobado
     */
    public String getNombre_aprobado() {
        return nombre_aprobado;
    }

    /**
     * @param nombre_aprobado the nombre_aprobado to set
     */
    public void setNombre_aprobado(String nombre_aprobado) {
        this.nombre_aprobado = nombre_aprobado;
    }
}









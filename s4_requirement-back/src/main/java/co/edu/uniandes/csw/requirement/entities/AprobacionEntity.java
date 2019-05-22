/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Esta clase es la entidad para el recurso de aprobación
 * @author Sofia Alvarez
 */

@Entity
public class AprobacionEntity extends BaseEntity implements Serializable {

    /**
     * Tipo de la aprobación
     */
    private String tipo;
     /**
     * Autor de la aprobación
     */
    private String autor;
     /**
     * Estado de la aprobación.
     * Puede ser aprobado, en revisión o no aprobado.
     */
    private String estado;
     /**
     * Organizacion a la que pertence el autor
     */
    private String organizacion;
     /**
     * Comentario sobre la aprobación
     */
    private String comentario;
     /**
     * Id del objetivo/requisito aprobado.
     */
    private String id_aprobado;
     /**
     * Nombre del objetivo/requisito aprobado.
     */
    private String nombre_aprobado;
    
    /**
     * Fecha y hora de creacion de la aprobacion
     */
    private String fechaYHora;
    
    /**
     * Relacion many to one con stakeholders
     */
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StakeHolderEntity stakeholder;
    
//    /**
//     * Relacion many to one con objetivos
//     */
//    @PodamExclude
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    private ObjetivoEntity objetivo;
//    
//    /**
//     * Relacion many to one con requisitos
//     */
//    @PodamExclude
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    private RequisitoEntity requisito;
    
     /**
     * Consrtructor vacío AprobaciónEntity
     */
    public AprobacionEntity() {

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
     * @return the aprobado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the aprobado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the aprobador
     */
    public StakeHolderEntity getStakeHolder() {
        return getStakeholder();
    }

    /**
     * @param stakeholder the stakeholder to set
     */
    public void setStakeHolder(StakeHolderEntity stakeholder) {
        this.setStakeholder(stakeholder);
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

}

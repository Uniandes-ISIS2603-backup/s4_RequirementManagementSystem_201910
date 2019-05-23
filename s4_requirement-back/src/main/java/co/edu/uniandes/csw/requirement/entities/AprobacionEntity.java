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
     * Estado de la aprobación.
     * Puede ser aprobado, en revisión o no aprobado.
     */
    private String estado;
     /**
     * Comentario sobre la aprobación
     */
    private String comentario;
    
    /**
     * Fecha y hora de creacion de la aprobacion
     */
    private String fechaYHora;
    
    /**
     * El autor de la aprobacion
     */
    private String autor;
    
    /**
     * Relacion many to one con objetivos
     */
    @PodamExclude
    @ManyToOne()
    private ObjetivoEntity objetivo;
    
    /**
     * Relacion many to one con requisitos
     */
    @PodamExclude
    @ManyToOne()
    private RequisitoEntity requisito;
    
     /**
     * Consrtructor vacío AprobaciónEntity
     */
    public AprobacionEntity() {
    
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
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
    

}

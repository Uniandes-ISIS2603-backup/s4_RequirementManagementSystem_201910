/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import java.io.Serializable;

/**
 * Data Transfer Object (DTO) de una Aprobacion
 *
 * @author Sofia Alvarez
 */
public class AprobacionDTO implements Serializable {

    /**
     * id de la aprobacion
     */
    private Long id;

    /**
     * Fecha y hora de la aprobacion
     */
    private String fechaYHora;

    /**
     * Estado de la aprobación. Puede ser: "Aprobado", "No aprobado" o "En
     * revisión"
     */
    private String estado;

    /**
     * Comentario sobre la aprobacion.
     */
    private String comentario;

    /**
     * Data Transfer Object del stakeholder.
     */
    private StakeHolderDTO stakeholder;

    /**
     * Data Transfer Object del stakeholder.
     */
    private ObjetivoDTO objetivo;

    /**
     * Data Transfer Object del stakeholder.
     */
    private RequisitoDTO requisito;

    /**
     * Constructor vacio
     */
    public AprobacionDTO() {

    }

    /**
     * Construye un DTO de aprobacion con la entity recibida por parametro
     *
     * @param entity
     */
    public AprobacionDTO(AprobacionEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.fechaYHora = entity.getFechaYHora();
            this.estado = entity.getEstado();
            this.comentario = entity.getComentario();
            if (entity.getObjetivo() != null) {
                this.objetivo = new ObjetivoDTO(entity.getObjetivo());
            }
            if (entity.getRequisito() != null) {
                this.requisito = new RequisitoDTO(entity.getRequisito());
            }

            if (entity.getStakeholder() != null) {
                this.stakeholder = new StakeHolderDTO(entity.getStakeholder());
            }
        }
    }

    /**
     * Metodo toEntity de una aprobacion
     *
     * @return vuelve un objetivo DTO en una entidad.
     */
    public AprobacionEntity toEntity() {
        AprobacionEntity entity = new AprobacionEntity();
        entity.setId(this.getId());
        entity.setFechaYHora(this.getFechaYHora());
        entity.setEstado(this.getEstado());
        entity.setComentario(this.getComentario());
        if (this.getObjetivo() != null) {
            entity.setObjetivo(this.getObjetivo().toEntity());
        }
        if (this.getRequisito() != null) {
            entity.setRequisito(this.getRequisito().toEntity());
        }
        if (this.getStakeholder() != null) {
            entity.setStakeholder(this.getStakeholder().toEntity());
        }
        return entity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the stakeholder
     */
    public StakeHolderDTO getStakeholder() {
        return stakeholder;
    }

    /**
     * @param stakeholder the stakeholder to set
     */
    public void setStakeholder(StakeHolderDTO stakeholder) {
        this.stakeholder = stakeholder;
    }

    /**
     * @return the objetivo
     */
    public ObjetivoDTO getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(ObjetivoDTO objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the requisito
     */
    public RequisitoDTO getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(RequisitoDTO requisito) {
        this.requisito = requisito;
    }
    
}

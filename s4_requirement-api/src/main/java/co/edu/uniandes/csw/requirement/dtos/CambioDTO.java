/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import java.io.Serializable;

/**
 * Esta clase representa un data transfer object para un cambio
 *
 * @author Sofia Alvarez
 */
public class CambioDTO implements Serializable {

    /**
     * Id del cambio.
     */
    private Long id;
    /**
     * Tipo del cambio. Puede ser: "Aprobación", "Modificación" o "Eliminación"
     */
    private String tipo;

    /**
     * Fecha y hota en que se realiza el cambio
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
     * Data transfer object del objetivo
     */
    private ObjetivoDTO objetivo;

    /**
     * Data transfer object del requisito
     */
    private RequisitoDTO requisito;

    /**
     * Constructor vacio
     */
    public CambioDTO() {

    }

    /**
     * Crea un Data transfer object (DTO) de cambio a partir de una entity de
     * aprobacion
     *
     * @param entity de aprobacion con la cual se creará el DTO
     */
    public CambioDTO(CambioEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.tipo = entity.getTipo();
            this.fechaYHora = entity.getFechaYHora();
            this.descripcion = entity.getDescripcion();
            this.autor = entity.getAutor();
            if (entity.getObjetivo() != null) {
                this.objetivo = new ObjetivoDTO(entity.getObjetivo());
            } else {
                this.objetivo = null;
            }
            if (entity.getRequisito() != null) {
                this.requisito = new RequisitoDTO(entity.getRequisito());
            } else {
                this.requisito = null;
            }
            
        }
    }

    /**
     * Convierte un DTO de aprobacion en una entity de aprobacion
     *
     * @return entity de aprobacion construida a partir del DTO
     */
    public CambioEntity toEntity() {
        CambioEntity entity = new CambioEntity();
        entity.setId(this.getId());
        entity.setTipo(this.getTipo());
        entity.setFechaYHora(this.getFechaYHora());
        entity.setDescripcion(this.getDescripcion());
        if (this.getObjetivo() != null)
        {
            entity.setObjetivo(this.getObjetivo().toEntity());
        }
        if (this.getRequisito() != null)
        {
            entity.setRequisito(this.getRequisito().toEntity());
        }
        entity.setAutor(this.getAutor());
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

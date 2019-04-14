/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import java.io.Serializable;

/**
 *
 * @author Sofia Sarmiento
 */
public class CondicionDTO implements Serializable{
     /**
     * Id de la condicion.
     */
    private Long id;
    /**
     * descripcion de la condicion.
     */
    private String descripcion;
    /**
     * seCumplio, variable que indica true si la condicion se cumplio y false de lo contrario.
     */
    private Boolean seCumplio;
    /**
     * esPrecondicion, variable que indica true si la condicion es una precondicion y false de lo contrario.
     */
    private Boolean esPrecondicion; 

    /**
     * Constructor vacio
     */
    public CondicionDTO(){

    }

    /**
     * Crea un Data transfer object (DTO) de condcion a partir de una entity
     * @param ce entidad con la cual se creará el DTO
     */
    public CondicionDTO(CondicionEntity ce)
    {
        if(ce != null)
        {
            this.id=ce.getId();
            this.descripcion=ce.getDescripcion();
            this.seCumplio=ce.isSeCumplio();
            this.esPrecondicion=ce.isEsPrecondicion();
        }
    }

    /**
     * Convierte un DTO en una entity de 
     * @return entity construida a partir del DTO
     */
  public CondicionEntity toEntity() {
        CondicionEntity condicionEntity = new CondicionEntity();
        condicionEntity.setId(this.id);
        condicionEntity.setDescripcion(this.descripcion);
        condicionEntity.setSeCumplio(this.seCumplio);
        condicionEntity.setEsPrecondicion(this.esPrecondicion);
        return condicionEntity;
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
     * @return the seCumplio
     */
    public Boolean isSeCumplio() {
        return seCumplio;
    }

    /**
     * @param seCumplio the seCumplio to set
     */
    public void setSeCumplio(Boolean seCumplio) {
        this.seCumplio = seCumplio;
    }

    /**
     * @return the esPrecondicion
     */
    public Boolean isEsPrecondicion() {
        return esPrecondicion;
    }

    /**
     * @param esPrecondicion the esPrecondicion to set
     */
    public void setEsPrecondicion(Boolean esPrecondicion) {
        this.esPrecondicion = esPrecondicion;
    }
    
}

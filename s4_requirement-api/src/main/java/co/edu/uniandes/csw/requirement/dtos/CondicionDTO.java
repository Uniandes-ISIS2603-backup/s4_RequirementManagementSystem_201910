/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;

/**
 *
 * @author Sofia Sarmiento
 */
public class CondicionDTO {
    private Long id;
    private String descripcion;
    private boolean seCumplio;
    
    public CondicionDTO(){
        
    }
    
    public CondicionDTO(CondicionEntity ce)
    {
        if(ce != null)
        {
            this.id=ce.getId();
            this.descripcion=ce.getDescripcion();
            this.seCumplio=ce.isSeCumplio();
        }
    }
 
  public CondicionEntity toEntity() {
        CondicionEntity condicionEntity = new CondicionEntity();
        condicionEntity.setId(this.id);
        condicionEntity.setDescripcion(this.descripcion);
        condicionEntity.setSeCumplio(this.seCumplio);
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
    public boolean isSeCumplio() {
        return seCumplio;
    }

    /**
     * @param seCumplio the seCumplio to set
     */
    public void setSeCumplio(boolean seCumplio) {
        this.seCumplio = seCumplio;
    }
    
}

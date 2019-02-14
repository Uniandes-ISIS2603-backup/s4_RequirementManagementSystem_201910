/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;

/**
 *
 * @author Sofia Sarmiento
 */
public class CondicionEntity extends BaseEntity implements Serializable {
    
    private String descripcion;
    
    private boolean seCumplio;
    
    public CondicionEntity(){
        
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

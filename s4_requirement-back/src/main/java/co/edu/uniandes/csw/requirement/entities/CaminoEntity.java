/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Sofia Alvarez
 */
@Entity
public class CaminoEntity extends BaseEntity implements Serializable
{
    private String descripcionPaso;
    
    public CaminoEntity(){
        
    }
    
    /**
     * @return the descripcionPaso
     */
    public String getDescripcionPaso() {
        return descripcionPaso;
    }

    /**
     * @param descripcionPaso to set
     */
    public void setDescripcionPaso(String descripPaso) {
        this.descripcionPaso = descripPaso;
    }
    
    
}

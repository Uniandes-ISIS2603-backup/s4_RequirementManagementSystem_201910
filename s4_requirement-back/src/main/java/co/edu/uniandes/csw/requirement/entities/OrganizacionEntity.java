/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class OrganizacionEntity extends BaseEntity implements Serializable {

 /**
  * Atributos
  */
    private String sector;
    private String nombre;
    
 /**
 * Relacion con stakeholders
 */
    @PodamExclude
    @ManyToOne
    private StakeHolderEntity stakeHolder;
    
/**
 * Constructor vacio
 */
    public OrganizacionEntity() {
    }
    
/**
 * 
 * @return sector
 */
    public String getSector() {
        return sector;
    }
    
/**
 * 
 * @param sector 
 */
    public void setSector(String sector) {
        this.sector = sector;
    }
    
/**
 * 
 * @return nombre
 */
    public String getNombre() {
        return nombre;
    }
    
/**
 * @param nombre 
 */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

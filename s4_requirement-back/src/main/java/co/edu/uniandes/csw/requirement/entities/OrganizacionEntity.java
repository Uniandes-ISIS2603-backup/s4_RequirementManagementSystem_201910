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
 * @author Mateo Pedroza
 */
@Entity
public class OrganizacionEntity extends BaseEntity implements Serializable {
    
    private String sector;
    private String nombre;

    public OrganizacionEntity() {
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
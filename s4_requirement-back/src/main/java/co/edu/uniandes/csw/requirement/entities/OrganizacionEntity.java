/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class OrganizacionEntity extends BaseEntity implements Serializable {

    private String sector;
    private String nombre;

    @PodamExclude
    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<StakeHolderEntity> stakeholders = new ArrayList<>();

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

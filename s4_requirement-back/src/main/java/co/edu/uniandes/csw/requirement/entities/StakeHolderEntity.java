/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class StakeHolderEntity extends BaseEntity implements Serializable {

    private String tipo;
    private String nombre;

    @ManyToOne
    private DRSEntity drs;
    
    @OneToMany (mappedBy = "autor")
    private List<CambioEntity> cambios = new ArrayList<>();
    
    @OneToMany (mappedBy = "aprobador")
    private List<AprobacionEntity> aprobaciones = new ArrayList<>();
    
    @ManyToOne
    private OrganizacionEntity organizacion;
    
    public StakeHolderEntity() {

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

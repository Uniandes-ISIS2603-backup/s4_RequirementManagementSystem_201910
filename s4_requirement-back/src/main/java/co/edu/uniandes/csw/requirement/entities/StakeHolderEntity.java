/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class StakeHolderEntity extends BaseEntity implements Serializable {

    private String tipo;
    private String nombre;

    @PodamExclude
    @ManyToOne
    private DRSEntity drs;
    
    @PodamExclude
    @OneToMany (mappedBy = "autor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CambioEntity> cambios = new ArrayList<>();
    
    @PodamExclude
    @OneToMany (mappedBy = "stakeholder", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AprobacionEntity> aprobaciones = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne
    private OrganizacionEntity organizacion;
    
    @PodamExclude
    @OneToOne
    private RequisitoEntity autorRequisito;

    @PodamExclude
    @ManyToOne
    private RequisitoEntity fuenteRequisito;
    
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

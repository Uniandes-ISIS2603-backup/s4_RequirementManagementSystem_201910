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

 /**
 *
 * Atributos de la clase
 */
    private String tipo;
    private String nombre;

//Entidad DRS de relacion
    @PodamExclude
    @ManyToOne
    private DRSEntity drs;
   
//Relacion cambios
    @PodamExclude
    @OneToMany(mappedBy = "autor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CambioEntity> cambios = new ArrayList<>();

//Relacion aprobaciones

    @PodamExclude
    @OneToMany(mappedBy = "stakeholder", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AprobacionEntity> aprobaciones = new ArrayList<>();


    @PodamExclude
    @ManyToOne
    private OrganizacionEntity organizacion;
    @PodamExclude
    @OneToOne
    private ObjetivoEntity autorObjetivo;

    @PodamExclude
    @ManyToOne
    private ObjetivoEntity fuenteObjetivo;



    @PodamExclude
    @OneToOne
    private RequisitoEntity autorRequisito;

    @PodamExclude
    @ManyToOne
    private RequisitoEntity fuenteRequisito;

    public StakeHolderEntity() {

    }
/**
 * 
 * @return tipo
 */
    public String getTipo() {
        return tipo;
    }

/**
* 
* @param tipo 
*/
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

 /**
 * 
 * @return nombre
 */
    public String getNombre() {
        return nombre;
    }
    
/**
 * 
 * @param nombre 
 */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

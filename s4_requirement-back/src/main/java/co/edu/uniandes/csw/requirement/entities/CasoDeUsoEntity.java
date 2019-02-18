/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Sarmiento
 */
@Entity
public class CasoDeUsoEntity extends BaseEntity implements Serializable {

    private String nombre;

    @ManyToOne
    private RequisitoEntity requisito;

    @OneToOne
    private CaminoEntity cursoBasicoDeEventos;

    @OneToMany(mappedBy = "casoCaminosDeExcepcion")
    private CaminoEntity caminosDeExcepecion;

    @OneToMany(mappedBy = "casoCaminosAlternativos")
    private CaminoEntity caminosAlternativos;

    @PodamExclude
    @OneToMany(mappedBy = "precondiciones")
    private CondicionEntity precondiciones;

    @PodamExclude
    @OneToMany(mappedBy = "postcondiciones")
    private CondicionEntity postcondiciones;

    public CasoDeUsoEntity() {

    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

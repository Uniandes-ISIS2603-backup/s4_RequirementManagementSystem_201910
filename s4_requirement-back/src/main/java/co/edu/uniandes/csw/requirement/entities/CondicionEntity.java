/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.*;

/**
 *
 * @author Sofia Sarmiento
 */
@Entity
public class CondicionEntity extends BaseEntity implements Serializable {

    private String descripcion;

    private Boolean seCumplio;
    
    private Boolean esPrecondicion;

    @PodamExclude
    @ManyToOne
    private CasoDeUsoEntity casos;


    public CondicionEntity() {

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
    public Boolean isSeCumplio() {
        return seCumplio;
    }

    /**
     * @param seCumplio the seCumplio to set
     */
    public void setSeCumplio(Boolean seCumplio) {
        this.seCumplio = seCumplio;
    }

    /**
     * @return the condiciones
     */
    public CasoDeUsoEntity getCasos() {
        return casos;
    }

    /**
     * @param casos the condiciones to set
     */
    public void setCasos(CasoDeUsoEntity casos) {
        this.casos = casos;
    }

    /**
     * @return the esPrecondicion
     */
    public Boolean isEsPrecondicion() {
        return esPrecondicion;
    }

    /**
     * @param esPrecondicion the esPrecondicion to set
     */
    public void setEsPrecondicion(Boolean esPrecondicion) {
        this.esPrecondicion = esPrecondicion;
    }

}

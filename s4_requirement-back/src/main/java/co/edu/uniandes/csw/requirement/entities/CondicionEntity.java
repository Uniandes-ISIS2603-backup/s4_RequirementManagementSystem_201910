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

    private boolean seCumplio;

    @PodamExclude
    @ManyToOne
    private CasoDeUsoEntity casoPrecondiciones;

    @PodamExclude
    @ManyToOne
    private CasoDeUsoEntity casoPostcondiciones;

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
    public boolean isSeCumplio() {
        return seCumplio;
    }

    /**
     * @param seCumplio the seCumplio to set
     */
    public void setSeCumplio(boolean seCumplio) {
        this.seCumplio = seCumplio;
    }

    /**
     * @return the casoPrecondiciones
     */
    public CasoDeUsoEntity getCasoPrecondiciones() {
        return casoPrecondiciones;
    }

    /**
     * @param casoPrecondiciones the casoPrecondiciones to set
     */
    public void setCasoPrecondiciones(CasoDeUsoEntity casoPrecondiciones) {
        this.casoPrecondiciones = casoPrecondiciones;
    }

    /**
     * @return the casoPostcondiciones
     */
    public CasoDeUsoEntity getCasoPostcondiciones() {
        return casoPostcondiciones;
    }

    /**
     * @param casoPostcondiciones the casoPostcondiciones to set
     */
    public void setCasoPostcondiciones(CasoDeUsoEntity casoPostcondiciones) {
        this.casoPostcondiciones = casoPostcondiciones;
    }

}

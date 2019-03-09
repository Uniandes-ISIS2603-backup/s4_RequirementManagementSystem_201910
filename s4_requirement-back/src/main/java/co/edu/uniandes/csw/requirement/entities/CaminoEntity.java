/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Alvarez
 */
@Entity
public class CaminoEntity extends BaseEntity implements Serializable {

    public enum TipoCamino
    {
        BASICO,
        EXCEPCION,
        ALTERNATIVO
    }
    

    private TipoCamino tipoCamino;
    private ArrayList<String> pasos;



    @PodamExclude
    @ManyToOne
    private CasoDeUsoEntity caminos;

    /**
     * Constructor vacío por defecto
     */
    public CaminoEntity() {

    }

    /**
     * @return the descripcionPaso
     */
    public ArrayList<String> getPasos() {
        return pasos;
    }

    /**
     * @param descripcionPaso to set
     */
    public void setPasos(ArrayList<String> pasitos) {
        this.pasos = pasitos;
    }
    
      /**
     * @return the descripcionPaso
     */
    public TipoCamino getTipoCamino() {
        return tipoCamino;
    }

    /**
     * @param tipoPasito to set
     */
    public void setTipoCamino(TipoCamino tipoPasito) {
        this.tipoCamino = tipoPasito;
    }

}

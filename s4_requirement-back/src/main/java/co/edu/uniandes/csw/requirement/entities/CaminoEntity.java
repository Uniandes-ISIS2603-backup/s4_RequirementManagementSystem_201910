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
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Alvarez
 */
@Entity
public class CaminoEntity extends BaseEntity implements Serializable {

   public final static String BASICO = "BASICO";
   public final static String EXCEPCION = "EXCEPCION";
   public final static String ALTERNATIVO = "ALTERNATIVO";

    private String tipoPaso;
    private ArrayList<String> pasos;

    @PodamExclude
    @OneToOne
    private CasoDeUsoEntity casoCursoBasicoDeEventos;

    @PodamExclude
    @ManyToOne
    private CasoDeUsoEntity casoCaminosDeExcepcion;

    @PodamExclude
    @ManyToOne
    private CasoDeUsoEntity casoCaminosAlternativos;

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
    public String getTipoPaso() {
        return tipoPaso;
    }

    /**
     * @param descripcionPaso to set
     */
    public void setTipoPaso(String tipoPasito) {
        this.tipoPaso = tipoPasito;
    }

}

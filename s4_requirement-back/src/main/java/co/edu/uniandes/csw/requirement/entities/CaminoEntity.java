/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author sofiaalvarez
 */
@Entity
public class CaminoEntity extends BaseEntity implements Serializable {
    /*
    public final static String BASICO = "BASICO";
    public final static String ALTERNATIVO = "ALTERNATIVO";
    public final static String EXCEPCION = "EXCEPCION";*/
    
    
    private String tipoPaso;
    
    private String pasos;
    
    @PodamExclude
    @ManyToOne 
    private CasoDeUsoEntity casos;
    
    public CaminoEntity(){
        
    }

    /**
     * @return the tipoPaso
     */
    public String getTipoPaso() {
        return tipoPaso;
    }

    /**
     * @param tipoPaso the tipoPaso to set
     */
    public void setTipoPaso(String tipoPaso) {
        this.tipoPaso = tipoPaso;
    }

    /**
     * @return the pasos
     */
    public String getPasos() {
        return pasos;
    }

    /**
     * @param pasos the pasos to set
     */
    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    /**
     * @return the casos
     */
    public CasoDeUsoEntity getCasos() {
        return casos;
    }

    /**
     * @param casos the casos to set
     */
    public void setCasos(CasoDeUsoEntity casos) {
        this.casos = casos;
    }

    
}

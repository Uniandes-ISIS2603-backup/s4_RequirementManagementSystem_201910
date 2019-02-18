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
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Alvarez
 */
@Entity
public class DRSEntity extends BaseEntity implements Serializable 
{
    private Integer version;
    private String reporte;
    
    //@PodamExclude
    //@ManyToMany
   // private List<StakeholderEntity> stakeholders = new ArrayList<StakeholderEntity>();
    //private List<ObjetivoEntity> objetivos = new ArrayList<ObjetivoEntity>();
    //private List<RequisitoEntity> requisitos = new ArrayList<RequisitoEntity>();
    

    public DRSEntity(){
    
         }
    
    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the reporte
     */
    public String getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(String reporte) {
        this.reporte = reporte;
    }
}

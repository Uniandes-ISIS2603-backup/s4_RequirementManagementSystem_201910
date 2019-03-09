/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Alvarez
 */
@Entity
public class DRSEntity extends BaseEntity implements Serializable {

    private Integer version;
    private String reporte;

    @PodamExclude
    @OneToMany(mappedBy = "drs", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<StakeHolderEntity> stakeholders = new ArrayList<StakeHolderEntity>();

    @PodamExclude
    @OneToMany(mappedBy = "drs", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ObjetivoEntity> objetivos = new ArrayList<ObjetivoEntity>();

    @PodamExclude
    @OneToMany(mappedBy = "drs", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<RequisitoEntity> requisitos = new ArrayList<RequisitoEntity>();

    public DRSEntity() {

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

    /**
     * @return the stakeholders
     */
    public List<StakeHolderEntity> getStakeholders() {
        return stakeholders;
    }

    /**
     * @param stakeholders the stakeholders to set
     */
    public void setStakeholders(List<StakeHolderEntity> stakeholders) {
        this.stakeholders = stakeholders;
    }

    /**
     * @return the objetivos
     */
    public List<ObjetivoEntity> getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(List<ObjetivoEntity> objetivos) {
        this.objetivos = objetivos;
    }

    /**
     * @return the requisitos
     */
    public List<RequisitoEntity> getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos the requisitos to set
     */
    public void setRequisitos(List<RequisitoEntity> requisitos) {
        this.requisitos = requisitos;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Sofia Alvarez
 */
public class DrsDTODetail extends DrsDTO implements Serializable
{
    //Relacion cero a muchos
  private List<ObjetivoDTO> objetivos;
  //Relacion cero a muchos
  private List<RequisitoDTO> requisitos;
  //Relacion cero a muchos
  private List<StakeHolderDTO> stakeholders;
    
  /**
   * Constructor por defecto
   */
    public DrsDTODetail(){
        
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param drsEntity La entidad de la cual se construye el DTO
     */
    public DrsDTODetail(DRSEntity drsEntity) {
        super(drsEntity);
        if(drsEntity != null)
        {
        if (drsEntity.getObjetivos() != null) {
            objetivos = new ArrayList<>();
            for (ObjetivoEntity entityObjetivo : drsEntity.getObjetivos()) {
                objetivos.add(new ObjetivoDTO(entityObjetivo));
            }
        }
           if (drsEntity.getRequisitos() != null) {
            requisitos = new ArrayList<>();
            for (RequisitoEntity entityRequisito : drsEntity.getRequisitos()) {
                requisitos.add(new RequisitoDTO(entityRequisito));
            }
        }
              if (drsEntity.getStakeholders() != null) {
            stakeholders = new ArrayList<>();
            for (StakeHolderEntity entityStakeholder : drsEntity.getStakeholders()) {
                stakeholders.add(new StakeHolderDTO(entityStakeholder));
            }
        }             
    }
        

    }
    
    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el drs.
     */
    @Override
    public DRSEntity toEntity() {
        DRSEntity drsEntity = super.toEntity();
        if (objetivos != null) {
            List<ObjetivoEntity> objetivosEntity = new ArrayList<>();
            for (ObjetivoDTO dtoObjetivo : getObjetivos()) {
                objetivosEntity.add(dtoObjetivo.toEntity());
            }
            drsEntity.setObjetivos(objetivosEntity);
        }
        if (requisitos != null) {
            List<RequisitoEntity> requisitosEntity = new ArrayList<>();
            for (RequisitoDTO dtoRequisito : getRequisitos()) {
                requisitosEntity.add(dtoRequisito.toEntity());
            }
            drsEntity.setRequisitos(requisitosEntity);
        }
        if (stakeholders != null) {
            List<StakeHolderEntity> stakeholdersEntity = new ArrayList<>();
            for (StakeHolderDTO dtoStakeholder : getStakeholders()) {
                stakeholdersEntity.add(dtoStakeholder.toEntity());
            }
            drsEntity.setStakeholders(stakeholdersEntity);
        }
        
        return drsEntity;
    }
    

    /**
     * @return the objetivos
     */
    public List<ObjetivoDTO> getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(List<ObjetivoDTO> objetivos) {
        this.objetivos = objetivos;
    }

    /**
     * @return the requisitos
     */
    public List<RequisitoDTO> getRequisitos() {
        return requisitos;
    }

    /**
     * @param requisitos the requisitos to set
     */
    public void setRequisitos(List<RequisitoDTO> requisitos) {
        this.requisitos = requisitos;
    }

    /**
     * @return the stakeholders
     */
    public List<StakeHolderDTO> getStakeholders() {
        return stakeholders;
    }

    /**
     * @param stakeholders the stakeholders to set
     */
    public void setStakeholders(List<StakeHolderDTO> stakeholders) {
        this.stakeholders = stakeholders;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

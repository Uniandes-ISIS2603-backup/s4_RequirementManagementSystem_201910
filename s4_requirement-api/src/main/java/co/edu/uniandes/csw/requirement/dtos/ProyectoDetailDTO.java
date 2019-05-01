/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
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
 * @author Sofia Alvarez, Jorge Esguerra, David Manosalva
 */
public class ProyectoDetailDTO extends ProyectoDTO implements Serializable {

    //Relacion cero a muchos
    private List<ObjetivoDTO> objetivos;
    //Relacion cero a muchos
    private List<StakeHolderDTO> stakeholders;
    // Relacion cero a muchos
    private List<UsuarioDTO> usuarios;

    /**
     * Constructor por defecto
     */
    public ProyectoDetailDTO() {

    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param proyectoEntity La entidad de la cual se construye el DTO
     */
    public ProyectoDetailDTO(ProyectoEntity proyectoEntity) {
        super(proyectoEntity);
        if (proyectoEntity != null) {
            if (proyectoEntity.getObjetivos() != null) {
                objetivos = new ArrayList<>();

                for (ObjetivoEntity entityObjetivo : proyectoEntity.getObjetivos()) {
                    objetivos.add(new ObjetivoDTO(entityObjetivo));
                }
            }

            if (proyectoEntity.getStakeholders() != null) {
                stakeholders = new ArrayList<>();
                for (StakeHolderEntity entityStakeholder : proyectoEntity.getStakeholders()) {
                    stakeholders.add(new StakeHolderDTO(entityStakeholder));
                }
            }
        }

    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el proyecto.
     */
    @Override
    public ProyectoEntity toEntity() {
        ProyectoEntity proyectoEntity = super.toEntity();
        if (objetivos != null) {
            List<ObjetivoEntity> objetivosEntity = new ArrayList<>();
            for (ObjetivoDTO dtoObjetivo : getObjetivos()) {
                objetivosEntity.add(dtoObjetivo.toEntity());
            }
            proyectoEntity.setObjetivos(objetivosEntity);
        }
        if (stakeholders != null) {
            List<StakeHolderEntity> stakeholdersEntity = new ArrayList<>();
            for (StakeHolderDTO dtoStakeholder : getStakeholders()) {
                stakeholdersEntity.add(dtoStakeholder.toEntity());
            }
            proyectoEntity.setStakeholders(stakeholdersEntity);
        }

        return proyectoEntity;
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

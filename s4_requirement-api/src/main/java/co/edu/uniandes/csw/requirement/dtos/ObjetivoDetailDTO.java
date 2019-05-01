/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que repesenta un Objetivo Detallado como DTO
 * @objetivo David Manosalva 
 */
public class ObjetivoDetailDTO extends ObjetivoDTO implements Serializable {

    /**
     * Lista de aprobaciones del objetivo
     */
    private List<AprobacionDTO> aprobaciones;
    
    /**
     * Lista de cambios del objetivo
     */
    private List<CambioDTO> cambios;
    
    /**
     * Lista de requisitos del objetivo
     */
    private List<RequisitoDTO> requisitos;
    
    /**
     * Lista de stakeholders del objetivo
     */
    private List<StakeHolderDTO> fuentes;
    
    /**
     * Constructor vacio para REST
     */
    public ObjetivoDetailDTO() {
        super();
    }

    /**
     * Constructor que crea un DTO de un objeto entity
     */
    public ObjetivoDetailDTO(ObjetivoEntity objetivoEntity) {
        super(objetivoEntity);
        if (objetivoEntity != null) {
            aprobaciones = new ArrayList<>();
            for (AprobacionEntity aprobacionEntities : objetivoEntity.getAprobaciones()) {
                aprobaciones.add(new AprobacionDTO(aprobacionEntities));
            }
            cambios = new ArrayList<>();
            for (CambioEntity cambioEntities : objetivoEntity.getCambios()) {
                cambios.add(new CambioDTO(cambioEntities));
            }
            requisitos = new ArrayList<>();
            for (RequisitoEntity requisitoEntities : objetivoEntity.getRequisitos()) {
                requisitos.add(new RequisitoDTO(requisitoEntities));
            }
            
            fuentes = new ArrayList<>();
            for (StakeHolderEntity stakeHolderEntities : objetivoEntity.getFuentes()) {
                fuentes.add(new StakeHolderDTO(stakeHolderEntities));
            }
        }
    }
    
    /**
     * Metodo que crea un ObjetivoEntity a partir de este DTO
     * @return ObjetivoEntity con la informacion del DTO
     */
    @Override
    public ObjetivoEntity toEntity() {
        ObjetivoEntity objetivoEntity = super.toEntity();
        if (aprobaciones != null) {
            List<AprobacionEntity> aprobacionesEntity = new ArrayList<>();
            for (AprobacionDTO dtoAprobacion : aprobaciones) {
                aprobacionesEntity.add(dtoAprobacion.toEntity());
            }
            objetivoEntity.setAprobaciones(aprobacionesEntity);
        }
        if (cambios != null) {
            List<CambioEntity> cambiosEntity = new ArrayList<>();
            for (CambioDTO dtoCambio : cambios) {
                cambiosEntity.add(dtoCambio.toEntity());
            }
            objetivoEntity.setCambios(cambiosEntity);
        }
        if (requisitos != null) {
            List<RequisitoEntity> requisitosEntity = new ArrayList<>();
            for (RequisitoDTO dtoRequisito : requisitos) {
                requisitosEntity.add(dtoRequisito.toEntity());
            }
            objetivoEntity.setRequisitos(requisitosEntity);
        }
        
        if (fuentes != null) {
            List<StakeHolderEntity> fuentesEntity = new ArrayList<>();
            for (StakeHolderDTO dtoFuentes : fuentes) {
                fuentesEntity.add(dtoFuentes.toEntity());
            }
            objetivoEntity.setFuentes(fuentesEntity);
        }
        return objetivoEntity;
    }

    /**
     * @return the aprobaciones
     */
    public List<AprobacionDTO> getAprobaciones() {
        return aprobaciones;
    }

    /**
     * @param aprobaciones the aprobaciones to set
     */
    public void setAprobaciones(List<AprobacionDTO> aprobaciones) {
        this.aprobaciones = aprobaciones;
    }

    /**
     * @return the cambios
     */
    public List<CambioDTO> getCambios() {
        return cambios;
    }

    /**
     * @param cambios the cambios to set
     */
    public void setCambios(List<CambioDTO> cambios) {
        this.cambios = cambios;
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
     * @return the fuentes
     */
    public List<StakeHolderDTO> getFuentes() {
        return fuentes;
    }

    /**
     * @param fuentes the fuentes to set
     */
    public void setFuentes(List<StakeHolderDTO> fuentes) {
        this.fuentes = fuentes;
    }
    
    
}

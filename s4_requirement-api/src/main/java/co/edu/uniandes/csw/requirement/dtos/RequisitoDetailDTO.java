/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import java.util.List;

/**
 *
 * @author Jorge Esguerra
 */
public class RequisitoDetailDTO extends RequisitoDTO {
    
    private List<CambioDTO> cambios;
    private List<ObjetivoDTO> objetivos;
    private List<AprobacionDTO> aprobaciones;
    private List<StakeHolderDTO> fuentes;
    
    public RequisitoDetailDTO()
    {
        super();
    }
    
    /*public RequisitoDetailDTO(RequisitoEntity reqEntity) {
        super(reqEntity);
        if (reqEntity != null) {
            aprobaciones = new ArrayList<>();
            for (AprobacionEntity aprEntity : reqEntity.getAprobaciones()) {
                aprobaciones.add(new AprobacionDTO(aprEntity));
            }
            cambios = new ArrayList<>();
            for (CambioEntity cambEntity : reqEntity.getCambios()) {
                cambios.add(new CambioDTO(cambEntity));
            }
            objetivos = new ArrayList<>();
            for (ObjetivoEntity objEntity: reqEntity.getObjetivos()){
                objetivos.add(new ObjetivoDTO(objEntity));
            }
            
            fuentes = new ArrayList<>();
            for (StakeHolderEntity stakeholderEntity : reqEntity.getFuentes()) {
                fuentes.add(new StakeHolderEntity(stakeHolderEntity));
            }
        }
    }*/
    
    /*@Override
    public RequisitoEntity toEntity() {
        RequisitoEntity reqEntity = super.toEntity();
        if (aprobaciones != null) {
            List<AprobacionEntity> aprobacionesEntity = new ArrayList<>();
            for (AprobacionDTO dtoAprobacion : aprobaciones) {
                aprobacionesEntity.add(dtoAprobacion.toEntity());
            }
            requisitoEntity.setAprobaciones(aprobacionesEntity);
        }
        if (cambios != null) {
            List<CambioEntity> cambiosEntity = new ArrayList<>();
            for (CambioDTO dtoCambio : cambios) {
                cambiosEntity.add(dtoCambio.toEntity());
            }
            requisitoEntity.setCambios(cambiosEntity);
        }
        if (objetivos != null) {
            List<ObjetivoEntity> objetivosEntity = new ArrayList<>();
            for (ObjetivoDTO dtoObjetivo : objetivos) {
                objetivosEntity.add(dtoRequisito.toEntity());
            }
            requisitoEntity.setRequisitos(objetivosEntity);
        }
        if (fuentes != null) {
            List<StakeHolderEntity> fuentesEntity = new ArrayList<>();
            for (StakeHolderDTO dtoFuentes : fuentes) {
                fuentesEntity.add(dtoFuentes.toEntity());
            }
            requisitoEntity.setFuentes(fuentesEntity);
        }
        return requisitoEntity;
    }*/

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
     * @return the objetivos
     */
    public List<ObjetivoDTO> getObjetivos() {
        return objetivos;
    }

    /**
     * @param requisitos the requisitos to set
     */
    public void setObjetivos(List<ObjetivoDTO> objetivos) {
        this.objetivos = objetivos;
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

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
 *
 * @author Jorge Esguerra
 */
public class RequisitoDetailDTO extends RequisitoDTO implements Serializable{
    
    private List<CambioDTO> cambios;
    private List<AprobacionDTO> aprobaciones;
    private List<CasoDeUsoDTO> casosDeUso;
    
    public RequisitoDetailDTO()
    {
        super();
    }
    
    public RequisitoDetailDTO(RequisitoEntity reqEntity) {
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
            casosDeUso = new ArrayList<>();
            for (CasoDeUsoEntity casos : reqEntity.getCasosDeUso()){
                casosDeUso.add(new CasoDeUsoDTO(casos));
            }
        }
    }
    
    @Override
    public RequisitoEntity toEntity() {
        RequisitoEntity reqEntity = super.toEntity();
        if (aprobaciones != null) {
            List<AprobacionEntity> aprobacionesEntity = new ArrayList<>();
            for (AprobacionDTO dtoAprobacion : aprobaciones) {
                aprobacionesEntity.add(dtoAprobacion.toEntity());
            }
            reqEntity.setAprobaciones(aprobacionesEntity);
        }
        if (cambios != null) {
            List<CambioEntity> cambiosEntity = new ArrayList<>();
            for (CambioDTO dtoCambio : cambios) {
                cambiosEntity.add(dtoCambio.toEntity());
            }
            reqEntity.setCambios(cambiosEntity);
        }
        if (getCasosDeUso() != null) {
            List<CasoDeUsoEntity> casosEntity = new ArrayList<>();
            for (CasoDeUsoDTO dtoCasos : getCasosDeUso()) {
                casosEntity.add(dtoCasos.toEntity());
            }
            reqEntity.setCasosDeUso(casosEntity);
        }
        return reqEntity;
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
     * @return the casosDeUso
     */
    public List<CasoDeUsoDTO> getCasosDeUso() {
        return casosDeUso;
    }

    /**
     * @param casosDeUso the casosDeUso to set
     */
    public void setCasosDeUso(List<CasoDeUsoDTO> casosDeUso) {
        this.casosDeUso = casosDeUso;
    }
    
    
}

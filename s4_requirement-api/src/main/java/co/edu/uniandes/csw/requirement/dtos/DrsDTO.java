/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import java.io.Serializable;

/**
 *
 * @author Sofia Alvarez
 */
public class DrsDTO implements Serializable {
    /**
     * Id del Drs
     */
    private Long id;
    /**
     * Version del Drs
     */
    private Integer version;
    /**
     * Reporte del Drs
     */
    private String reporte;
    
    /**
     * Constructor por defecto 
     */
    public DrsDTO()
    {
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     * @param entity Es la entidad que se va a convertir a DTO
     */
    public DrsDTO(DRSEntity entity)
    {
        if(entity != null)
        {
            this.id = entity.getId();
            this.version = entity.getVersion();
            this.reporte = entity.getReporte();
        }
    }
    /**
     * Convertir DTO a Entity
     * @return Un entity con los valores del DTO
     */
    public DRSEntity toEntity() {
        DRSEntity drsEntity = new DRSEntity();
        drsEntity.setId(this.getId());
        drsEntity.setVersion(this.version);
        drsEntity.setReporte(this.reporte);
        return drsEntity;
    }
    /**
     * El id del drs
     * @return el id del drs
     */
    public Long getId(){
        return id;
    }
    /**
     * Modifica el id del drs
     * @param pId el id del drs
     */
    public void setId(Long pId){
        id = pId;
    }
    /**
     * La version del drs
     * @return la version del drs
     */
      public Integer getVersion(){
        return version;
    }
    /**
     * Modifica la version del drs
     * @param pVersion la version del drs
     */
    public void setVersion(Integer pVersion){
        version = pVersion;
    }
    /**
     * Reporte del drs
     * @return 
     */
     public String getReporte(){
        return reporte;
    }
    /**
     * Modifica el reporte
     * @param pReporte el reporte por el que se quiere modificar
     */
    public void setReporte(String pReporte){
        reporte = pReporte;
    }
    
    
}

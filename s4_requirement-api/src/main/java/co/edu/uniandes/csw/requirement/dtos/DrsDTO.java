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
    
    private Long id;
    private Integer version;
    private String reporte;
    
    public DrsDTO()
    {
    }
    
    public DrsDTO(DRSEntity drse)
    {
        if(drse != null)
        {
            this.id = drse.getId();
            this.version = drse.getVersion();
            this.reporte = drse.getReporte();
        }
    }
    
    public DRSEntity toEntity() {
        DRSEntity drsEntity = new DRSEntity();
        drsEntity.setId(this.getId());
        drsEntity.setVersion(this.version);
        drsEntity.setReporte(this.reporte);
        return drsEntity;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long pId){
        id = pId;
    }
    
      public Integer getVersion(){
        return version;
    }
    
    public void setVersion(Integer pVersion){
        version = pVersion;
    }
    
     public String getReporte(){
        return reporte;
    }
    
    public void setReporte(String pReporte){
        reporte = pReporte;
    }
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class DrsDTO implements Serializable {
    
    private Integer id;
    private Integer version;
    private String reporte;
    
    public DrsDTO()
    {
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer pId){
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

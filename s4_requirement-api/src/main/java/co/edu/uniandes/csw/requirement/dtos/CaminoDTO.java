/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import com.sun.corba.se.impl.io.ValueUtility;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sofia Alvarez
 */
public class CaminoDTO implements Serializable
{
 private Long idPaso;
 private String tipoPaso;
 private ArrayList<String> pasos;
 
 public CaminoDTO(){
 }
 
 public CaminoDTO(CaminoEntity ce)
    {
        if(ce != null)
        {
            this.idPaso = ce.getId();
            this.pasos = ce.getPasos();
            this.tipoPaso = ce.getTipoPaso();
        }
 }
 
  public CaminoEntity toEntity() {
        CaminoEntity caminoEntity = new CaminoEntity();
        caminoEntity.setId(this.getIdPaso());
        caminoEntity.setPasos(this.pasos);
        caminoEntity.setTipoPaso(this.tipoPaso);
        return caminoEntity;
    }
 
 public Long getIdPaso(){
     return idPaso;
 }
  
  
  public void setIdPaso(Long pId)
 {
     idPaso = pId;
 }
 
 public String getTipoPaso(){
     return tipoPaso;
 }
 
 public void setTipoPaso(String tipoPasito){
     tipoPaso = tipoPasito;
 }
 
  public ArrayList<String> getPasos(){
     return pasos;
 }
 
 public void setPasos(ArrayList<String> pasitos){
     pasos = pasitos;
 }
    
}

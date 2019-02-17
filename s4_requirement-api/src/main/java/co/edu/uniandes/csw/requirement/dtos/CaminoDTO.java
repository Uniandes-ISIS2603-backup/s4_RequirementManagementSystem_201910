/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import java.io.Serializable;

/**
 *
 * @author Sofia Alvarez
 */
public class CaminoDTO implements Serializable
{
 private Integer idPaso;
 private String descripcionPaso;
 
 public CaminoDTO(){
 }
 
 public Integer getIdPaso()
 {
    return idPaso;
 }
 
 public void setIdPaso(Integer pId)
 {
     idPaso = pId;
 }
 
 public String getDescripcionPaso(){
     return descripcionPaso;
 }
 
 public void setDescripcionPaso(String pDescripcion){
     descripcionPaso = pDescripcion;
 }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import java.io.Serializable;

/**
 *
 * @author Sofia Alvarez
 */
public class CaminoDTO implements Serializable
{
   /**
    * Id del paso
    */
 private Long idPaso;
 /**
  * El tipo del paso. Puede ser básico, alternativo o de excepción.
  */
 private String tipoPaso;
 /**
  * Lista de pasos a seguir en un camino.
  */
 private String pasos;
 
 /**
  * Constructor por defecto.
  */
 public CaminoDTO(){
 }
 
 /**
  * Constructor a partir de la entidad
  * @param ce la entidad del libro
  */
 public CaminoDTO(CaminoEntity ce)
    {
        if(ce != null)
        {
            this.idPaso = ce.getId();
            this.pasos = ce.getPasos();
            this.tipoPaso = ce.getTipoPaso();
        }
 }
 /**
  * Método para transformar el DTO a una Entidad
  * @return la entidad del camino asociado.
  */
  public CaminoEntity toEntity() {
        CaminoEntity caminoEntity = new CaminoEntity();
        caminoEntity.setId(this.getIdPaso());
        caminoEntity.setPasos(this.pasos);
        caminoEntity.setTipoPaso(this.tipoPaso);
        return caminoEntity;
    }
 
  /**
   * Id del paso.
   * @return el id del paso.
   */
 public Long getIdPaso(){
     return idPaso;
 }
  
  /**
   * Modifica el id del paso
   * @param pId el id por el que se quiere cambiar
   */
  public void setIdPaso(Long pId)
 {
     idPaso = pId;
 }
 
  /**
   * El tipo del paso
   * @return el tipo del paso 
   */
 public String getTipoPaso(){
     return tipoPaso;
 }
 /**
  * Modifica el tipo del paso
  * @param tipoPasito el tipo de paso por el que se quiere cambiar
  */
 public void setTipoPaso(String tipoPasito){
     tipoPaso = tipoPasito;
 }
 
 /**
  * Pasos de un camino
  * @return pasos de un camino 
  */
  public String getPasos(){
     return pasos;
 }
 
  /**
   * Modifica la lista de pasos de un camino
   * @param pasitos la lista de pasos a poner
   */
 public void setPasos(String pasitos){
     pasos = pasitos;
 }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author sofiaalvarez
 */
@Stateless
public class CambioObjetivoLogic {
    
    /**
     * Inyección de la persistencia objetivo 
     */
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    /**
     * Inyeccion de la persistencia de CambioObjetivoLogic
     */
    @Inject
    private CambioPersistence cambioPersistence;
    
    
    /**
     * Servicio 1: Asociar un objetivo a un CambioObjetivoLogic
     * @param objID el objetivo a asociar
     * @param camID dl CambioObjetivoLogic a asociar
     * @return el objetivo que se asoció. 
     */
    public ObjetivoEntity addObjetivo(Long objID, Long camID)
    {
        ObjetivoEntity obj = objetivoPersistence.find(objID);
        CambioEntity camb = cambioPersistence.find(camID);
        camb.setObjetivo(obj);
        obj.getCambios().add(camb);
 
        return objetivoPersistence.find(camID);
    }
    
    /**
     *
     * Servicio 2: Obtener un Objetivo por medio del id de su CambioObjetivoLogic.
     *
     * @param cambioId id del aprobacion a ser buscado.
     * @return el requisito solicitada por medio de su id.
     */
    public ObjetivoEntity getObjetivo(Long cambioId) {
        ObjetivoEntity objEntity = cambioPersistence.find(cambioId).getObjetivo();
        return objEntity;
    }
    
  
    /**
     * Servicio 2: desasocia el objetivo del CambioObjetivoLogic
     * @param objID el id del objetivo a remover
     * @param aprID el id del CambioObjetivoLogic de la cual se remueve la asociación
     */
    public void removeObjetivo(Long objID, Long aprID)
    {
        ObjetivoEntity objEntity = objetivoPersistence.find(objID);
        CambioEntity aprEntity = cambioPersistence.find(aprID);
        objEntity.getCambios().remove(aprEntity);
        aprEntity.setObjetivo(null);
    }
    
}

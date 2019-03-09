/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jorgeandresesguerraalarcon
 */

@Stateless
public class AprobacionObjetivoLogic
{
    private static final Logger LOGGER = Logger.getLogger(AprobacionObjetivoLogic.class.getName());

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    @Inject
    private AprobacionPersistence aprobacionPersistence;
    
    
    /**
     * Servicio 1: Asociar un objetivo a una aprobación
     * @param objID el objetivo a asociar
     * @param aprID la aprobación a asociar
     * @return el objetivo que se asoció. 
     */
    public ObjetivoEntity addObjetivo(Long objID, Long aprID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un Objetivo a la aprobación con id = {0}", aprID);
        ObjetivoEntity obj = objetivoPersistence.find(objID);
        AprobacionEntity apr = aprobacionPersistence.find(aprID);
        apr.setObjetivo(obj);
        obj.getAprobaciones().add(apr);
        
        
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un Objetivo  a la aprobación con id = {0}", aprID);
        return objetivoPersistence.find(aprID);
    }
    
    /**
     * Servicio 2: desasocia el objetivo de la aprobación
     * @param objID el id del objetivo a remover
     * @param aprID el id de la aprobación de la cual se remueve la asociación
     */
    public void removeObjetivo(Long objID, Long aprID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un objetivo de la aprobación con id = {0}", aprID);
        ObjetivoEntity objEntity = objetivoPersistence.find(objID);
        AprobacionEntity aprEntity = aprobacionPersistence.find(aprID);
        objEntity.getAprobaciones().remove(aprEntity);
        aprEntity.setObjetivo(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un objetivo de la aprobación con id = {0}", aprID);
    }
   
    
}
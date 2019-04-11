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
 *Clase de asociacion entre aprobacion y objetivo
 * @author jorgeandresesguerraalarcon & Sofia Alvarez
 */
@Stateless
public class AprobacionObjetivoLogic
{
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(AprobacionObjetivoLogic.class.getName());

    /**
     * Inyección de la persistencia objetivo 
     */
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    /**
     * Inyeccion de la persistencia de aprobacion
     */
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
     *
     * Servicio 2: Obtener un Objetivo por medio del id de su aprobación.
     *
     * @param aprobacionsId id del aprobacion a ser buscado.
     * @return el requisito solicitada por medio de su id.
     */
    public ObjetivoEntity getObjetivo(Long aprobacionsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo de la aprobacion con id = {0}", aprobacionsId);
        ObjetivoEntity objEntity = aprobacionPersistence.find(aprobacionsId).getObjetivo();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el objetivo de ka aprobacion con id = {0}", aprobacionsId);
        return objEntity;
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

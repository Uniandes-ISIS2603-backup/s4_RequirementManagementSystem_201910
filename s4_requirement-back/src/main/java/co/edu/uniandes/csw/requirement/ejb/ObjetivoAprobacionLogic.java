/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jorgeandresesguerraalarcon
 */

@Stateless
public class ObjetivoAprobacionLogic
{
    private static final Logger LOGGER = Logger.getLogger(AprobacionObjetivoLogic.class.getName());

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    @Inject
    private AprobacionPersistence aprobacionPersistence;
    
    
    /**
     * Servicio 1: Asociar una aprobación a un objetivo
     * @param objID el objetivo a asociar
     * @param aprID la aprobación a asociar
     * @return la Aprobacion que se asoció. 
     */
    public AprobacionEntity addAprobacion(Long objID, Long aprID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una aprobación al objetivo con id = {0}", aprID);
        ObjetivoEntity obj = objetivoPersistence.find(objID);
        AprobacionEntity apr = aprobacionPersistence.find(aprID);
        //System.out.print("Obj" + obj);
        apr.setObjetivo(obj);
        obj.getAprobaciones().add(apr);
        //objetivoPersistence.update(obj);
        //aprobacionPersistence.update(apr);
        
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un Objetivo  a la aprobación con id = {0}", aprID);
        return aprobacionPersistence.find(aprID);
    }
    
    /**
     *
     * Servicio 2: Obtener una lista de Aprobaciones por medio del id de su objetivo.
     *
     * @param objetivoId id del objetivo a ser buscado.
     * @return la lista de aprobaciones del requisito en cuestión.
     */
    public List<AprobacionEntity> getAprobaciones(Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las aprobaciones de el objetivo con id = {0}", objetivoId);
        List<AprobacionEntity> lista = objetivoPersistence.find(objetivoId).getAprobaciones();
        LOGGER.log(Level.INFO, "Termina proceso de consultar las aprobaciones de el objetivo con id = {0}", objetivoId);
        return lista;
    }
    
    /**
     * Servicio 3: elimina la aprobación con el ID correspondiente de la lista de aprobaciones del objetivo con el ID correspondiente.
     * @param aprID el id de la aprobación a remover
     * @param objID el id del objetivo del cual se remueve la aprobación.
     */
    public void removeOneAprobacion(Long objID, Long aprID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una aprobación del objetivo con id = {0}", objID);
        ObjetivoEntity objEntity = objetivoPersistence.find(objID);
        AprobacionEntity aprEntity = aprobacionPersistence.find(aprID);
        objEntity.getAprobaciones().remove(aprEntity);
        aprEntity.setObjetivo(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un objetivo de la aprobación con id = {0}", objID);
    }
    
    
    /**
     * Servicio 4:  reinicia la lista de aprobaciones del objetivo con id pasado por parámetro.
     * @param objID el id del objetivo del cual se remueven las aprobaciones.
     */
    public void clearAprobaciones(Long objID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar todas las aprobaciones del objetivo con id = {0}", objID);
        ObjetivoEntity objEntity = objetivoPersistence.find(objID);
        objEntity.setAprobaciones(new ArrayList<AprobacionEntity>());
        LOGGER.log(Level.INFO, "Termina proceso de borrar un objetivo de la aprobación con id = {0}", objID);
    }
    
   
    
}

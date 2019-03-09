/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
public class RequisitoAprobacionLogic
{
    private static final Logger LOGGER = Logger.getLogger(AprobacionRequisitoLogic.class.getName());

    @Inject
    private RequisitoPersistence requisitoPersistence;

    @Inject
    private AprobacionPersistence aprobacionPersistence;
    
    
    /**
     * Servicio 1: Asociar una aprobación a un requisito
     * @param reqID el requisito a asociar
     * @param aprID la aprobación a asociar
     * @return la Aprobacion que se asoció. 
     */
    public AprobacionEntity addAprobacion(Long reqID, Long aprID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle una aprobación al requisito con id = {0}", aprID);
        RequisitoEntity req = requisitoPersistence.find(reqID);
        AprobacionEntity apr = aprobacionPersistence.find(aprID);
        //System.out.print("Req" + req);
        apr.setRequisito(req);
        req.getAprobaciones().add(apr);
        //requisitoPersistence.update(req);
        //aprobacionPersistence.update(apr);
        
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un Requisito  a la aprobación con id = {0}", aprID);
        return aprobacionPersistence.find(aprID);
    }
    
    /**
     *
     * Servicio 2: Obtener una lista de Aprobaciones por medio del id de su requisito.
     *
     * @param requisitoId id del requisito a ser buscado.
     * @return la lista de aprobaciones del requisito en cuestión.
     */
    public List<AprobacionEntity> getAprobaciones(Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las aprobaciones de el requisito con id = {0}", requisitoId);
        List<AprobacionEntity> lista = requisitoPersistence.find(requisitoId).getAprobaciones();
        LOGGER.log(Level.INFO, "Termina proceso de consultar las aprobaciones de el requisito con id = {0}", requisitoId);
        return lista;
    }
    
    /**
     * Servicio 3: elimina la aprobación con el ID correspondiente de la lista de aprobaciones del requisito con el ID correspondiente.
     * @param aprID el id de la aprobación a remover
     * @param reqID el id del requisito del cual se remueve la aprobación.
     */
    public void removeOneAprobacion(Long reqID, Long aprID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una aprobación del requisito con id = {0}", reqID);
        RequisitoEntity reqEntity = requisitoPersistence.find(reqID);
        AprobacionEntity aprEntity = aprobacionPersistence.find(aprID);
        reqEntity.getAprobaciones().remove(aprEntity);
        aprEntity.setRequisito(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un requisito de la aprobación con id = {0}", reqID);
    }
    
    
    /**
     * Servicio 4:  reinicia la lista de aprobaciones del requisito con id pasado por parámetro.
     * @param reqID el id del requisito del cual se remueven las aprobaciones.
     */
    public void clearAprobaciones(Long reqID)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar todas las aprobaciones del requisito con id = {0}", reqID);
        RequisitoEntity reqEntity = requisitoPersistence.find(reqID);
        reqEntity.setAprobaciones(new ArrayList<AprobacionEntity>());
        LOGGER.log(Level.INFO, "Termina proceso de borrar un requisito de la aprobación con id = {0}", reqID);
    }
    
   
    
}

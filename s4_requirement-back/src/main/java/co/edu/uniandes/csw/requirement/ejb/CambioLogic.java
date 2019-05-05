/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase de logica para un cambio
 * @author Sofia Alvarez
 */
@Stateless
public class CambioLogic {
    
    /**
     * Inyección de persistencia de un cambio
     */
    @Inject
    private CambioPersistence cambioPersistence;
    
    /**
     * Inyeccion de la persistencia de un stakeholder
     */
    @Inject 
    private StakeHolderPersistence shPersistence;
    
    /**
     * Inyeccion de la persistencia de un objetivo
     */
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    /**
     * Inyeccion de la persistencia de un requisito
     */
    @Inject 
    private RequisitoPersistence requisitoPersistence;
    
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(CambioLogic.class.getName());
    
   /**
    * Metodo que crea la logica de un cambio
    * @param cambio cambio a persistir
    * @return cambio persistido
    * @throws BusinessLogicException  si el tipo de cambio es null, si no es de modifcacion, eliminacion o aprobacion
    o si está asociado a un objetivo y a un requisito.
    */
    public CambioEntity createCambio(CambioEntity cambio) throws BusinessLogicException{
        /*if(cambio.getFechaYHora() == null){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }*/
        if(cambio.getTipo() == null){
            throw new BusinessLogicException("El tipo del cambio no puede ser nulo.");
        }
        if(cambio.getObjetivo() != null && cambio.getRequisito() != null){
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
 if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION")) ){          
     throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        cambio = cambioPersistence.create(cambio);
        return cambio;
    }
    
    /**
     * Elimina un cambio, dado su id
     * @param cambioId id del cambio a eliminar
     * @return cambio eliminado
     */
    public CambioEntity deleteCambio(Long cambioId){
        CambioEntity cambio = cambioPersistence.delete(cambioId);
        return cambio;
    }
    
    /**
     * Encuentra un cambio dado su id
     * @param id id del cambio a encontrar
     * @return cambio encontrado
     */
    public CambioEntity findCambioById(Long id){
        CambioEntity cambio = cambioPersistence.find(id);
        return cambio;
    }
    
    /**
     * Lista con todos los cambios.
     * @return lista con todos los cambios
     */
    public List<CambioEntity> findAllCambios(){
        List<CambioEntity> cambios = cambioPersistence.findAll();
        return cambios;
    }
    
    /**
     * Actualiza un cambio.
     * @param cambio el cambo a actualizar
     * @return cambio actualizado
    * @throws BusinessLogicException  si el tipo de cambio es null o si no es de modificacion, eliminacion o aprobacion
     */
    public CambioEntity updateCambio(CambioEntity cambio) throws BusinessLogicException{
        if(cambio.getFechaYHora() == null){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if(cambio.getTipo() == null){
            throw new BusinessLogicException("El tipo del cambio no puede ser nulo.");
        }
        if(cambio.getObjetivo() != null && cambio.getRequisito() != null){
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
 if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION")) ){          
            throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        cambio = cambioPersistence.update(cambio);
        return cambio;
    }
    
    /**
     * Cambia el stakeholder de un cambio
     * @param cambioId id del cambio 
     * @param shId id del stakeholder
     * @return  el cambio con el nuevo stakeholder
     */
    public CambioEntity changeStakeHolder(Long cambioId, Long shId){
        StakeHolderEntity nuevo = shPersistence.find(shId);
        CambioEntity cambio = cambioPersistence.find(cambioId);
        cambio.setStakeholder(nuevo);
        return cambio;
    }
    
    /**
     * Cambia el objetivo de un cambio
     * @param cambioId id del cambio 
     * @param objetivoId id del objetivo
     * @return  el cambio con el nuevo objetivo
     */
//    public CambioEntity changeObjetivo(Long cambioId, Long objetivoId){
//        ObjetivoEntity nuevo = objetivoPersistence.find(objetivoId);
//        CambioEntity cambio = cambioPersistence.find(cambioId);
//        cambio.setObjetivo(nuevo);
//        return cambio;
//    }
    
     /**
     * Cambia el requisito de un cambio
     * @param cambioId id del cambio 
     * @param requisitoId id del requisito
     * @return  el cambio con el nuevo requisito
     */   
    public CambioEntity changeRequisito(Long cambioId, Long requisitoId){
        RequisitoEntity nuevo = requisitoPersistence.find(requisitoId);
        CambioEntity cambio = cambioPersistence.find(cambioId);
        cambio.setRequisito(nuevo);
        return cambio;
    }
}

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase de logica para un cambio
 *
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
     *
     * @param cambio cambio a persistir
     * @return cambio persistido
     * @throws BusinessLogicException si el tipo de cambio es null, si no es de
     * modifcacion, eliminacion o aprobacion o si está asociado a un objetivo y
     * a un requisito.
     */
    public CambioEntity createCambioObjetivo(Long proyectosId, Long objetivosId, CambioEntity cambio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cambio");
        if(cambio.getFechaYHora() == null || cambio.getFechaYHora().equals("")){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION"))) {
            throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        
        ObjetivoEntity obj = objetivoPersistence.find(proyectosId, objetivosId);
        if (obj != null)
        {
            cambio.setObjetivo(obj);
        }
        else
        {
           throw new BusinessLogicException("El objetivo con id = " + objetivosId + "no existe");
        }
        cambio = cambioPersistence.create(cambio);
        LOGGER.log(Level.INFO, "Termina proceso de creación de un cambio");
        return cambio;
    }
    
    /**
     * Metodo que crea la logica de un cambio
     *
     * @param cambio cambio a persistir
     * @return cambio persistido
     * @throws BusinessLogicException si el tipo de cambio es null, si no es de
     * modifcacion, eliminacion o aprobacion o si está asociado a un objetivo y
     * a un requisito.
     */
    public CambioEntity createCambioRequisito( Long objetivosId, Long requisitosId, CambioEntity cambio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cambio");
        if(cambio.getFechaYHora() == null || cambio.getFechaYHora().equals("")){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION"))) {
            throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        
        RequisitoEntity obj = requisitoPersistence.find(objetivosId, requisitosId);
        if (obj != null)
        {
            cambio.setRequisito(obj);
        }
        else
        {
           throw new BusinessLogicException("El requisito con id = " + requisitosId + "no existe");
        }
        cambio = cambioPersistence.create(cambio);
        LOGGER.log(Level.INFO, "Termina proceso de creación de un cambio");
        return cambio;
    }

    /**
     * Borrar un cambio
     *
     * @param objetivoId
     * @param cambioId: id de la cambio a borrar
     * @throws BusinessLogicException 
     */
    public void deleteCambioObjetivo(Long objetivoId, Long cambioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar El cambio con id = {0}", cambioId);
        CambioEntity camino = findCambioByIdObjetivo(cambioId, objetivoId);
        if (camino == null) {
            throw new BusinessLogicException("El cambio con id = " + cambioId + " no esta asociado a el objetivo con id = " + objetivoId);
        }
        cambioPersistence.delete(camino.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar El cambio con id = {0}", cambioId);
    }
    
    /**
     * Borrar un cambio
     *
     * @param objetivoId
     * @param cambioId: id de la cambio a borrar
     * @throws BusinessLogicException 
     */
    public void deleteCambioRequisito(Long requisitoId, Long cambioId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cambio con id = {0}", cambioId);
        CambioEntity camino = findCambioByIdRequisito(cambioId, requisitoId);
        if (camino == null) {
            throw new BusinessLogicException("el cambio con id = " + cambioId + " no esta asociado a el objetivo con id = " + requisitoId);
        }
        cambioPersistence.delete(camino.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cambio con id = {0}", cambioId);
    }

    /**
     * Encuentra un cambio dado su id
     *
     * @param id id del cambio a encontrar
     * @return cambio encontrado
     */
    public CambioEntity findCambioByIdRequisito(Long id, Long requisitoId) {
        CambioEntity cambio = cambioPersistence.findWithRequisito(requisitoId, id);
        if (cambio == null)
        {
            LOGGER.log(Level.SEVERE, "La aprobacion con el id = {0} no existe", id);
        }
        return cambio;
    }
    
    /**
     * Encuentra un cambio dado su id
     *
     * @param id id del cambio a encontrar
     * @return cambio encontrado
     */
    public CambioEntity findCambioByIdObjetivo(Long id, Long objetivoId) {
        CambioEntity cambio = cambioPersistence.findWithObjetivo(objetivoId, id);
        if (cambio == null)
        {
            LOGGER.log(Level.SEVERE, "La aprobacion con el id = {0} no existe", id);
        }
        return cambio;
    }
    /**
     * Encuentra un cambio dado su id
     *
     * @param id id del cambio a encontrar
     * @return cambio encontrado
     */
    public List<CambioEntity> getCambiosOfObj(Long proyectosId, Long objetivosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los requisitos");
        ObjetivoEntity objE = objetivoPersistence.find(proyectosId, objetivosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los requisitos");
        return objE.getCambios();
    }
    
    /**
     * Encuentra un cambio dado su id
     *
     * @param id id del cambio a encontrar
     * @return cambio encontrado
     */
    public List<CambioEntity> getCambiosOfReq(Long objetivosId, Long requisitosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los requisitos");
        RequisitoEntity reqE = requisitoPersistence.find(objetivosId, requisitosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los requisitos");
        return reqE.getCambios();
    }
    

    /**
     * Actualiza un cambio.
     *
     * @param cambio el cambo a actualizar
     * @return cambio actualizado
     * @throws BusinessLogicException si el tipo de cambio es null o si no es de
     * modificacion, eliminacion o aprobacion
     */
    
    public CambioEntity updateCambioObjetivo(Long proyectosId, Long objetivosId,  CambioEntity cambio) throws BusinessLogicException {
        
        if (cambio.getFechaYHora() == null) {
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if (cambio.getTipo() == null) {
            throw new BusinessLogicException("El tipo del cambio no puede ser nulo.");
        }
        if (cambio.getObjetivo() != null && cambio.getRequisito() != null) {
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
        if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION"))) {
            throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        ObjetivoEntity x = objetivoPersistence.find(proyectosId, objetivosId);
        cambio.setObjetivo(x);
        cambio = cambioPersistence.update(cambio);
        return cambio;
    }
    
        /**
     * Actualiza un cambio.
     *
     * @param cambio el cambo a actualizar
     * @return cambio actualizado
     * @throws BusinessLogicException si el tipo de cambio es null o si no es de
     * modificacion, eliminacion o aprobacion
     */
    
    public CambioEntity updateCambioRequisito(Long objetivosId, Long requisitosId, CambioEntity cambio) throws BusinessLogicException {
        
        if (cambio.getFechaYHora() == null) {
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if (cambio.getTipo() == null) {
            throw new BusinessLogicException("El tipo del cambio no puede ser nulo.");
        }
        if (cambio.getObjetivo() != null && cambio.getRequisito() != null) {
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
        if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION"))) {
            throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        ObjetivoEntity x = objetivoPersistence.find(objetivosId, requisitosId);
        cambio.setObjetivo(x);
        cambio = cambioPersistence.update(cambio);
        return cambio;
    }

}

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
    public CambioEntity createCambio(CambioEntity cambio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del cambio");
        if(cambio.getFechaYHora() == null){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }

        if (cambio.getObjetivo() != null && cambio.getRequisito() != null) {
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
        if (cambio.getObjetivo() == null && cambio.getRequisito() == null) {
            throw new BusinessLogicException("El cambio debe estar asociado a un objetivo o a un requisito");
        }
        if (!(cambio.getTipo().equals("MODIFICACION") || cambio.getTipo().equals("ELIMINACION") || cambio.getTipo().equals("APROBACION"))) {
            throw new BusinessLogicException("El tipo de un cambio debe ser modificacion, eliminacion o aprobacion");
        }
        cambio = cambioPersistence.create(cambio);
        return cambio;
    }

    /**
     * Elimina un cambio, dado su id
     *
     * @param cambioId id del cambio a eliminar
     * @return cambio eliminado
     */
    public void deleteCambio(Long cambioId) {
        
        cambioPersistence.delete(cambioId);
    }

    /**
     * Encuentra un cambio dado su id
     *
     * @param id id del cambio a encontrar
     * @return cambio encontrado
     */
    public CambioEntity findCambioByIdRequisito(Long id, Long requisitoId) {
        CambioEntity cambio = cambioPersistence.findWithRequisito(requisitoId, id);
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
    
    public CambioEntity updateCambio(Long proyectosId, Long objetivosId,  CambioEntity cambio) throws BusinessLogicException {
        
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

}

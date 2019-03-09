/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @requisito jorgeandresesguerraalarcon
 */
@Stateless
public class RequisitoAprobacionLogic 
{
   
    private static final Logger LOGGER = Logger.getLogger(RequisitoAprobacionLogic.class.getName());

    @Inject
    private AprobacionPersistence aprobacionPersistence;

    @Inject
    private RequisitoPersistence requisitoPersistence;

    /**
     * Asocia un Aprobacion existente a un Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param aprobacionId Identificador de la instancia de Aprobacion
     * @return Instancia de AprobacionEntity que fue asociada a Requisito
     */
    public AprobacionEntity addAprobacion(Long requisitoId, Long aprobacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un aprobacion al requisito con id = {0}", requisitoId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionId);
        requisitoEntity.getAprobaciones().add(aprobacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un aprobacion al requisito con id = {0}", requisitoId);
        return aprobacionPersistence.find(aprobacionId);
    }

    /**
     * Obtiene una colección de instancias de AprobacionEntity asociadas a una
     * instancia de Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @return Colección de instancias de AprobacionEntity asociadas a la instancia de
     * Requisito
     */
    public List<AprobacionEntity> getAprobacions(Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los aprobacions del requisito con id = {0}", requisitoId);
        return requisitoPersistence.find(requisitoId).getAprobaciones();
    }

    /**
     * Obtiene una instancia de AprobacionEntity asociada a una instancia de Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param aprobacionId Identificador de la instancia de Aprobacion
     * @return La entidad de Aprobacion del requisito
     * @throws BusinessLogicException Si el aprobacion no está asociado al requisito
     */
    public AprobacionEntity getAprobacion(Long requisitoId, Long aprobacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el aprobacion con id = {0} del requisito con id = " + requisitoId, aprobacionId);
        List<AprobacionEntity> aprobacions = requisitoPersistence.find(requisitoId).getAprobaciones();
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionId);
        int index = aprobacions.indexOf(aprobacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el aprobacion con id = {0} del requisito con id = " + requisitoId, aprobacionId);
        if (index >= 0) {
            return aprobacions.get(index);
        }
        throw new BusinessLogicException("El aprobacion no está asociado al requisito");
    }

    /**
     * Remplaza las instancias de Aprobacion asociadas a una instancia de Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param aprobacions Colección de instancias de AprobacionEntity a asociar a instancia
     * de Requisito
     * @return Nueva colección de AprobacionEntity asociada a la instancia de Requisito
     */
    public List<AprobacionEntity> replaceAprobacions(Long requisitoId, List<AprobacionEntity> aprobacions) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los aprobacions asocidos al requisito con id = {0}", requisitoId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
        List<AprobacionEntity> aprobacionList = aprobacionPersistence.findAll();
        for (AprobacionEntity aprobacion : aprobacionList) {
            if (aprobacions.contains(aprobacion)) {
                if (!aprobacion.getRequisito().equals(requisitoEntity)) 
                {
                    aprobacion.setRequisito(requisitoEntity);
                }
            }
        }
        requisitoEntity.setAprobaciones(aprobacions);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los aprobacions asocidos al requisito con id = {0}", requisitoId);
        return requisitoEntity.getAprobaciones();
    }

    /**
     * Desasocia un Aprobacion existente de un Requisito existente
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param aprobacionId Identificador de la instancia de Aprobacion
     */
    public void removeAprobacion(Long requisitoId, Long aprobacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un aprobacion del requisito con id = {0}", requisitoId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionId);
        requisitoEntity.getAprobaciones().remove(aprobacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un aprobacion del requisito con id = {0}", requisitoId);
    }
}

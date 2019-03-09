/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this requisitolate file, choose Tools | Requisitolates
 * and open the requisitolate in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @cambio David Manosalva
 */
@Stateless
public class RequisitoCambioLogic{
   
    private static final Logger LOGGER = Logger.getLogger(RequisitoCambioLogic.class.getName());

    @Inject
    private RequisitoPersistence requisitoPersistence;

    @Inject
    private CambioPersistence cambioPersistence;

    /**
     * Asocia un Requisito existente a un Cambio
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @param requisitoId Identificador de la instancia de Requisito
     * @return Instancia de RequisitoEntity que fue asociada a Cambio
     */
    public RequisitoEntity addRequisito(Long cambioId, Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un requisito al cambio con id = {0}", cambioId);
        CambioEntity cambioEntity = cambioPersistence.find(cambioId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
        requisitoEntity.getCambios().add(cambioEntity);
        cambioEntity.setRequisito(requisitoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un requisito al cambio con id = {0}", cambioId);
        return requisitoPersistence.find(requisitoId);
    }

    /**
     * Obtiene una colección de instancias de RequisitoEntity asociadas a una
     * instancia de Cambio
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @return Colección de instancias de RequisitoEntity asociadas a la instancia de
     * Cambio
     */
    public RequisitoEntity getRequisito(Long cambioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los requisitos del cambio con id = {0}", cambioId);
        return cambioPersistence.find(cambioId).getRequisito();
    }

    /**
     * Obtiene una instancia de RequisitoEntity asociada a una instancia de Cambio
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @param requisitoId Identificador de la instancia de Requisito
     * @return La entidad de Requisito del cambio
     * @throws BusinessLogicException Si el requisito no está asociado al cambio
     */
    public RequisitoEntity getRequisito(Long cambioId, Long requisitoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el requisito con id = {0} del cambio con id = " + cambioId, requisitoId);
        RequisitoEntity requisito = cambioPersistence.find(cambioId).getRequisito();
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
        
        if (requisitoEntity != null) {
            return requisito;
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el requisito con id = {0} del cambio con id = " + cambioId, requisitoId);
        throw new BusinessLogicException("El requisito no está asociado al cambio");
    }


    /**
     * Desasocia un Requisito existente de un Cambio existente
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @param requisitoId Identificador de la instancia de Requisito
     */
    public void removeRequisito(Long cambioId, Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el requisito del cambio con id = {0}", cambioId);
        CambioEntity cambioEntity = cambioPersistence.find(cambioId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
        requisitoEntity.getCambios().remove(cambioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un requisito del cambio con id = {0}", cambioId);
    }
}

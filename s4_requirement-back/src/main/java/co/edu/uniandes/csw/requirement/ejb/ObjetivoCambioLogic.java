/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this objetivolate file, choose Tools | Objetivolates
 * and open the objetivolate in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
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
public class ObjetivoCambioLogic{
   
    private static final Logger LOGGER = Logger.getLogger(ObjetivoCambioLogic.class.getName());

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    @Inject
    private CambioPersistence cambioPersistence;

    /**
     * Asocia un Objetivo existente a un Cambio
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @param objetivoId Identificador de la instancia de Objetivo
     * @return Instancia de ObjetivoEntity que fue asociada a Cambio
     */
    public ObjetivoEntity addObjetivo(Long cambioId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un objetivo al cambio con id = {0}", cambioId);
        CambioEntity cambioEntity = cambioPersistence.find(cambioId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        objetivoEntity.getCambios().add(cambioEntity);
        cambioEntity.setObjetivo(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un objetivo al cambio con id = {0}", cambioId);
        return objetivoPersistence.find(objetivoId);
    }

    /**
     * Obtiene una colección de instancias de ObjetivoEntity asociadas a una
     * instancia de Cambio
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @return Colección de instancias de ObjetivoEntity asociadas a la instancia de
     * Cambio
     */
    public ObjetivoEntity getObjetivo(Long cambioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los objetivos del cambio con id = {0}", cambioId);
        return cambioPersistence.find(cambioId).getObjetivo();
    }

    /**
     * Obtiene una instancia de ObjetivoEntity asociada a una instancia de Cambio
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @param objetivoId Identificador de la instancia de Objetivo
     * @return La entidad de Objetivo del cambio
     * @throws BusinessLogicException Si el objetivo no está asociado al cambio
     */
    public ObjetivoEntity getObjetivo(Long cambioId, Long objetivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo con id = {0} del cambio con id = " + cambioId, objetivoId);
        ObjetivoEntity objetivo = cambioPersistence.find(cambioId).getObjetivo();
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        
        if (objetivoEntity != null) {
            return objetivo;
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el objetivo con id = {0} del cambio con id = " + cambioId, objetivoId);
        throw new BusinessLogicException("El objetivo no está asociado al cambio");
    }


    /**
     * Desasocia un Objetivo existente de un Cambio existente
     *
     * @param cambioId Identificador de la instancia de Cambio
     * @param objetivoId Identificador de la instancia de Objetivo
     */
    public void removeObjetivo(Long cambioId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el objetivo del cambio con id = {0}", cambioId);
        CambioEntity cambioEntity = cambioPersistence.find(cambioId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        objetivoEntity.getCambios().remove(cambioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un objetivo del cambio con id = {0}", cambioId);
    }
}

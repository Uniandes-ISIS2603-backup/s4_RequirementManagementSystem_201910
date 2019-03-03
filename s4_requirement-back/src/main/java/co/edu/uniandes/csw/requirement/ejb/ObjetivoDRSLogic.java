/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.*;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.inject.Inject;

/**
 *
 * @author davidmanosalva
 */
@Stateless
public class ObjetivoDRSLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ObjetivoDRSLogic.class.getName());

    @Inject
    private DRSPersistence drsPersistence;

    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    public DRSEntity addObjetivo(Long drsId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el drs con id = {0} al objetivo con id = " + objetivoId, drsId);
        DRSEntity drsEntity = drsPersistence.find(drsId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        objetivoEntity.setDrs(drsEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el drs con id = {0} al objetivo con id = " + objetivoId, drsId);
        return drsPersistence.find(drsId);
    }

    public DRSEntity getDRS(Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el drs del objetivo con id = {0}", objetivoId);
        DRSEntity drsEntity = objetivoPersistence.find(objetivoId).getDrs();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el drs del objetivo con id = {0}", objetivoId);
        return drsEntity;
    }

    public DRSEntity replaceDRS(Long objetivoId, Long drsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el drs del objetivo con id = {0}", objetivoId);
        DRSEntity drsEntity = drsPersistence.find(drsId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        objetivoEntity.setDrs(drsEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el drs con id = {0} al objetivo con id = " + objetivoId, drsId);
        return drsPersistence.find(drsId);
    }

    public void removeDRS(Long objetivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el drs del objetivo con id = {0}", objetivoId);
        ObjetivoEntity oe = objetivoPersistence.find(objetivoId);
        if (oe.getDrs() == null) {
            throw new BusinessLogicException("El objetivo no tiene drs");
        }
        DRSEntity drsEntity = drsPersistence.find(oe.getDrs().getId());
        oe.setDrs(null);
        drsEntity.getObjetivos().remove(oe);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el drs con id = {0} del objetivo con id = " + objetivoId, drsEntity.getId());
    }
}

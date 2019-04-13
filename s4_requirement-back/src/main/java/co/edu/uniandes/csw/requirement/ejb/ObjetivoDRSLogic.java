/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.*;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.inject.Inject;

/**
 *
 * @author David Manosalva & Sofia Alvarez
 */
@Stateless
public class ObjetivoDRSLogic {
     /**
      * Logger de la clase
      */
    private static final Logger LOGGER = Logger.getLogger(ObjetivoDRSLogic.class.getName());

    /**
     * Persistencia de DRS
     */
    @Inject
    private DRSPersistence drsPersistence;

    /**
     * Persistencia del Objetivo
     */
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    /**
     * Añadir un objetivo a el DRS dado
     * @param drsId Id del drs a unir
     * @param objetivoId Id del objetivo a unir
     * @return DRS Entity con el objetivo
     */
    public DRSEntity addObjetivo(Long drsId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el drs con id = {0} al objetivo con id = " + objetivoId, drsId);
        DRSEntity drsEntity = drsPersistence.find(drsId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        objetivoEntity.setDrs(drsEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el drs con id = {0} al objetivo con id = " + objetivoId, drsId);
        return drsPersistence.find(drsId);
    }

    /**
     *Consulta el drs con el objetivo dado
     * @param objetivoId Id del objetivo
     * @return Entity del DRS 
     */
    public DRSEntity getDRS(Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el drs del objetivo con id = {0}", objetivoId);
        DRSEntity drsEntity = objetivoPersistence.find(objetivoId).getDrs();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el drs del objetivo con id = {0}", objetivoId);
        return drsEntity;
    }

    /**
     * Actualizar un objetivo a el DRS dado
     * @param drsId Id del drs a unir
     * @param objetivoId Id del objetivo a unir
     * @return DRS Entity con el objetivo
     */
    public List<ObjetivoEntity> replaceObjetivos(Long objetivoId, Long drsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el drs del objetivo con id = {0}", objetivoId);
        List<ObjetivoEntity> objEnt = objetivoPersistence.findAll();
        DRSEntity drsEntity = drsPersistence.find(drsId);
        drsEntity.setObjetivos(objEnt);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el drs con id = {0} al objetivo con id = " + objetivoId, drsId);
        return objetivoPersistence.findAll();
    }

    /**
     * Eliminar el objetivo del DRS dado
     * @param drsId Id del drs
     * @throws BusinessLogicException Si no existe el objetivo dentro del DRS
     */
    public void removeObjetivos(Long drsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar los objetivos del drs con id = {0}", drsId);
        DRSEntity de = drsPersistence.find(drsId);
        if (de.getObjetivos() == null || de.getObjetivos().isEmpty()) {
            throw new BusinessLogicException("El objetivo no tiene drs");
        }
        List<ObjetivoEntity> oe = objetivoPersistence.findAll();
        for(int i = 0; i < oe.size(); i++){
        de.getObjetivos().remove(i);
        }
        LOGGER.log(Level.INFO, "Termina proceso de borrar los objetivos del drs ");
    }
}

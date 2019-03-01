/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.persistence.*;

/**
 *
 * @author davidmanosalva
 */
@Stateless
public class ObjetivoPersistence {

    private static final Logger LOGGER = Logger.getLogger(ObjetivoPersistence.class.getName());

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public ObjetivoEntity create(ObjetivoEntity op) {
        LOGGER.log(Level.INFO, "Creando un nuevo objetivo");
        em.persist(op);
        LOGGER.log(Level.INFO, "Objetivo creado");
        return op;
    }

    public ObjetivoEntity find(Long objetivoId) {
        LOGGER.log(Level.INFO, "Consultando el objetivo con id={0}", objetivoId);
        return em.find(ObjetivoEntity.class, objetivoId);
    }

    public List<ObjetivoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los objetivos");
        TypedQuery<ObjetivoEntity> query = em.createQuery("select u from ObjetivoEntity u", ObjetivoEntity.class);

        return query.getResultList();
    }

    public ObjetivoEntity update(ObjetivoEntity objetivoEntity) {
        LOGGER.log(Level.INFO, "Actualizando el objetivo con id={0}", objetivoEntity.getId());
        return em.merge(objetivoEntity);
    }

    public void delete(Long objetivoId) {
        LOGGER.log(Level.INFO, "Borrando el objetivo con id={0}", objetivoId);
        ObjetivoEntity objetivoEntity = em.find(ObjetivoEntity.class, objetivoId);
        em.remove(objetivoEntity);
    }

}

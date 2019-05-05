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
 * Persistencia del Objetivo
 * @author davidmanosalva
 */
@Stateless
public class ObjetivoPersistence {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(ObjetivoPersistence.class.getName());

    /**
     * manejador de la Persistencia
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * Metodo para crear un nuevo objetivo
     * @param op Entidad del objetivo a crear
     * @return Objetivo entity persistido
     */
    public ObjetivoEntity create(ObjetivoEntity op) {
        LOGGER.log(Level.INFO, "Creando un nuevo objetivo");
        em.persist(op);
        LOGGER.log(Level.INFO, "Objetivo creado");
        return op;
    }

    /**
     * Buscar un objetivo
     *
     * Busca si hay algun objetivo asociado a un libro y con un ID específico
     *
     * @param proyectosId El ID del proyecto con respecto al cual se busca
     * @param objetivoId El ID del objetivo buscado
     * @return El Objetivo encontrado o null. Nota: Si existe uno o más objetivos
     * devuelve siempre la primera que encuentra
     */
    public ObjetivoEntity find(Long proyectosId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Consultando el Objetivo con id = {0} del Proyecto con id = " + proyectosId, objetivoId);
        TypedQuery<ObjetivoEntity> q = em.createQuery("select p from ObjetivoEntity p where (p.proyecto.id = :proyectosId) and (p.id = :objetivoId)", ObjetivoEntity.class);
        q.setParameter("proyectosId", proyectosId);
        q.setParameter("objetivoId", objetivoId);
        List<ObjetivoEntity> results = q.getResultList();
        ObjetivoEntity objetivos = null;
        if (results == null) {
            objetivos = null;
        } else if (results.isEmpty()) {
            objetivos = null;
        } else if (results.size() >= 1) {
            objetivos = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar el objetivo con id = {0} del proyecto con id =" + proyectosId, objetivoId);
        return objetivos;
    }

    /**
     * Metodo para retornra todos los objetivos persistidos
     * @return Lista de los objetivos persistidos
     */
    public List<ObjetivoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los objetivos");
        TypedQuery<ObjetivoEntity> query = em.createQuery("select u from ObjetivoEntity u", ObjetivoEntity.class);

        return query.getResultList();
    }

    /**
     * Metodo para actualizar un objeto entity
     * @param objetivoEntity Objetivo a actualizar
     * @return Entidad del objetivo actualizado y persistido
     */
    public ObjetivoEntity update(ObjetivoEntity objetivoEntity) {
        LOGGER.log(Level.INFO, "Actualizando el objetivo con id = {0}", objetivoEntity.getId());
        return em.merge(objetivoEntity);
    }

    /**
     * Elimina un objetivo
     * @param objetivoId Id del objetivo a eliminar
     */
    public void delete(Long objetivoId) {
        LOGGER.log(Level.INFO, "Borrando el objetivo con id={0}", objetivoId);
        ObjetivoEntity objetivoEntity = em.find(ObjetivoEntity.class, objetivoId);
        em.remove(objetivoEntity);
        LOGGER.log(Level.INFO, "Saliendo de borrar El objetivo con id = {0}", objetivoId);
        
    }

}

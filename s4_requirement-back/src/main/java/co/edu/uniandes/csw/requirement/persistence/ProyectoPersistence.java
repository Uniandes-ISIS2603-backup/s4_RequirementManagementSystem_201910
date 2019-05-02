/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jorge Esguerra, David Manosalva
 */
@Stateless
public class ProyectoPersistence {
    /**
     * LOGGER de operaciones realizadas por la clase
     */
    private static final Logger LOGGER = Logger.getLogger(ObjetivoPersistence.class.getName());
    
    /**
     * Unidad para persistir objetos tipo proyecto.
     */
    @PersistenceContext(unitName = "requirementPU") // requirement persistence unit.
    protected EntityManager em; // esta hace el acceso a la base de datos.

    /**
     * Método para crear en la base de datos una nueva tupla
     * @param e proyecto a persistir
     * @return el proyecto persistido y con id asignado
     */
    public ProyectoEntity create(ProyectoEntity e) // la diferencia entre el que recibe y el que devuelve es que en el devuelto la base de datos ya ha dejado un id. 
    {
        LOGGER.log(Level.INFO, "Creando un nuevo proyecto");
        em.persist(e);  // La base de datos le crea el valor al Long ID, gracias a que extiende de BaseEntity
        LOGGER.log(Level.INFO, "Objetivo creado");
        return e;
    }

    /**
     * Método para encontrar un proyecto por identificador único
     * @param id el identificador del proyecto a buscar 
     * @return el ProyectoEntity correspondiente al id, null si no existe.
     */
    public ProyectoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando el proyecto con id={0}", id);
        return em.find(ProyectoEntity.class, id);
    }
    
    /**
     * Retorna la lista de todos los proyectos en el sistema
     * @return Lista de proyectos
     */
    public List<ProyectoEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los proyectos");
        TypedQuery<ProyectoEntity> query = em.createQuery("select u from ProyectoEntity u", ProyectoEntity.class);
        return query.getResultList();
    }

    /**
     * Método para actualizar un ProyectoEntity en base de datos.
     * @param r el ProyectoEntity a actualizar
     * @return 
     */
    public ProyectoEntity update(ProyectoEntity r) 
    {
        LOGGER.log(Level.INFO, "Actualizando el proyecto con id={0}", r.getId());
        return em.merge(r);
    }
    
    /**
     * Método para borrar un ProyectoEntity por identificador único.
     * @param id el identificador
     */
    public void delete(Long id) 
    {
        LOGGER.log(Level.INFO, "Borrando el proyecto con id={0}", id);
        ProyectoEntity x = em.find(ProyectoEntity.class, id);
        em.remove(x);
    }  
}

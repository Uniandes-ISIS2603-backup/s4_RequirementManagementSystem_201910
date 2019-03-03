/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import javax.ejb.Stateless;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jorge Esguerra
 */
@Stateless
public class RequisitoPersistence {

    private static final Logger LOGGER = Logger.getLogger(ObjetivoPersistence.class.getName());
    
    @PersistenceContext(unitName = "requirementPU") // requirement persistence unit.
    protected EntityManager em; // esta hace el acceso a la base de datos.

    public RequisitoEntity create(RequisitoEntity e) // la diferencia entre el que recibe y el que devuelve es que en el devuelto la base de datos ya ha dejado un id. 
    {
        LOGGER.log(Level.INFO, "Creando un nuevo requisito");
        em.persist(e);  // La base de datos le crea el valor al Long ID, gracias a que extiende de BaseEntity
        LOGGER.log(Level.INFO, "Objetivo creado");
        return e;
    }

    public RequisitoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando el requisito con id={0}", id);
        return em.find(RequisitoEntity.class, id);
    }

    public List<RequisitoEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los requisito");
        TypedQuery<RequisitoEntity> query = em.createQuery("select u from RequisitoEntity u", RequisitoEntity.class);
        return query.getResultList();
    }

    // Still not implemented
    public RequisitoEntity update(RequisitoEntity r) 
    {
        LOGGER.log(Level.INFO, "Actualizando el requisito con id={0}", r.getId());
        return em.merge(r);
    }
    
    // Still not implemented
    public void delete(Long id) 
    {
        LOGGER.log(Level.INFO, "Borrando el requisito con id={0}", id);
        RequisitoEntity x = em.find(RequisitoEntity.class, id);
        em.remove(x);
    }  
}


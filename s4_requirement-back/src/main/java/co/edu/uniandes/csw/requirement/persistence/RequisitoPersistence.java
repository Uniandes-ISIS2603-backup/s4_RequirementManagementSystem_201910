/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jorge Esguerra
 */
@Stateless
public class RequisitoPersistence
{
    @PersistenceContext(unitName = "requirementPU") // requirement persistence unit.
    protected EntityManager em; // esta hace el acceso a la base de datos.
    public RequisitoEntity create (RequisitoEntity e)  // la diferencia entre el que recibe y el que devuelve es que en el devuelto la base de datos ya ha dejado un id. 
    {
        em.persist(e);  // La base de datos le crea el valor al Long ID, gracias a que extiende de BaseEntity
        return e;
    }
    
    public RequisitoEntity find(Long id)
    {
        return em.find(RequisitoEntity.class, id);
    }
       
    public List<RequisitoEntity> findAll() 
    {
        TypedQuery<RequisitoEntity> query = em.createQuery("select u from RequisitoEntity u", RequisitoEntity.class);
        return query.getResultList();
    }
    
    // implementacion de pruebas JUNIT
}

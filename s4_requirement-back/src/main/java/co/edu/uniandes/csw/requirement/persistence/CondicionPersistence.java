/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sofia Sarmiento
 */

@Stateless
public class CondicionPersistence {
    
    @PersistenceContext(unitName = "requirementPU" )
    protected EntityManager em;
    
    public CondicionEntity create (CondicionEntity condicionEntity){
        em.persist(condicionEntity);
        return condicionEntity;
    }
    
    public CondicionEntity find (Long condicionId){
        return em.find(CondicionEntity.class, condicionId);
    }
    
    public List<CondicionEntity> findAll (){
        
        TypedQuery<CondicionEntity> query = em.createQuery("select u from CasoDeUsoEntity u", CondicionEntity.class);
        return query.getResultList();
    }
    
}

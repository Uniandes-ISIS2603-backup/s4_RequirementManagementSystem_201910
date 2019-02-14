/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class AprobacionPersistence {
    
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;
    
        public AprobacionEntity create(AprobacionEntity aprobacion){
        em.persist(aprobacion);
        return aprobacion;
    }
    
    public AprobacionEntity find(Long aprobacionId){
        return em.find(AprobacionEntity.class, aprobacionId);
    }
    
    public List<AprobacionEntity> findAll(){
        TypedQuery<AprobacionEntity> query = em.createQuery("select u from CambioEntity", AprobacionEntity.class);
        return query.getResultList();
    }
    
    public AprobacionEntity delete(AprobacionEntity aprobacion){
        em.remove(aprobacion);
        return aprobacion;
    }
}

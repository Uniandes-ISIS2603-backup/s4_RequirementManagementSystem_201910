/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
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
public class CambioPersistence {
    
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;
    
    public CambioEntity create(CambioEntity cambioEntity){
        em.persist(cambioEntity);
        return cambioEntity;
    }
    
    public CambioEntity find(Long cambioId){
        return em.find(CambioEntity.class, cambioId);
    }
    
    public List<CambioEntity> findAll(){
        TypedQuery<CambioEntity> query = em.createQuery("select u from CambioEntity", CambioEntity.class);
        return query.getResultList();
    }
    
    public CambioEntity delete(CambioEntity cambio){
        em.remove(cambio);
        return cambio;
    }
}

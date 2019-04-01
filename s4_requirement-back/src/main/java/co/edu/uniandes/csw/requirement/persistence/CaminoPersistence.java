/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author sofiaalvarez
 */
@Stateless
public class CaminoPersistence {
    
     @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;
    
    public CaminoEntity create(CaminoEntity camino){
        em.persist(camino);
        return camino;
    }
    
    
    public CaminoEntity find(Long caminoId){
        return em.find(CaminoEntity.class, caminoId);
    }
    
    public List<CaminoEntity> findAll(){
        TypedQuery<CaminoEntity> q = em.createQuery("select u from CaminoEntity u", CaminoEntity.class);
        return q.getResultList();
    }
    
    public CaminoEntity update(CaminoEntity camino){
        return em.merge(camino);
    }
    
    public CaminoEntity delete(Long caminoId){
        CaminoEntity camino = em.find(CaminoEntity.class, caminoId);
        em.remove(camino);
        return camino;
    }
     
     
    
    
    
}

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
    
    
    public CaminoEntity find(Long casoDeUsoId,Long caminoId){
        TypedQuery<CaminoEntity> q = em.createQuery("select p from CaminoEntity p where (p.caso.id = :casoDeUsoId) and (p.id = :caminoId)", CaminoEntity.class);
        q.setParameter("casoDeUsoId", casoDeUsoId);
        q.setParameter("caminoId", caminoId);
        List<CaminoEntity> results = q.getResultList();
        CaminoEntity caminos = null;
        if (results == null) {
            caminos = null;
        } else if (results.isEmpty()) {
            caminos = null;
        } else if (results.size() >= 1) {
            caminos = results.get(0);
        }
        return caminos;
    }
    
    public CaminoEntity update(CaminoEntity camino){
        return em.merge(camino);
    }
    
    public void delete(Long caminoId){
        CaminoEntity camino = em.find(CaminoEntity.class, caminoId);
        em.remove(camino);
    }
     
     
    
    
    
}

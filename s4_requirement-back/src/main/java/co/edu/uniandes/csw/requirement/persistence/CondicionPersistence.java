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

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public CondicionEntity create(CondicionEntity condicionEntity) {
        em.persist(condicionEntity);
        return condicionEntity;
    }

    public CondicionEntity find(Long condicionId) {
        return em.find(CondicionEntity.class, condicionId);
    }

    public List<CondicionEntity> findAll() {

        TypedQuery<CondicionEntity> query = em.createQuery("select u from CondicionEntity u", CondicionEntity.class);
        return query.getResultList();
    }
    
    public CondicionEntity findByDescripcion(String description){
        TypedQuery<CondicionEntity> query = em.createQuery("select u from CondicionEntity u where u.descripcion = :descripcion", CondicionEntity.class);
        query = query.setParameter("descripcion", description);
        
        List<CondicionEntity> sameDes= query.getResultList();
        CondicionEntity resultado;
        if(sameDes==null){
            resultado=null;
        }
        else if(sameDes.isEmpty()){
            resultado=null;
        }
        else{
            resultado= sameDes.get(0);
        }
        return resultado;
    }
    
    public CondicionEntity update(CondicionEntity condicion){
        return em.merge(condicion);
    }
    
    public CondicionEntity delete(Long condicionId){
        CondicionEntity condicion=em.find(CondicionEntity.class, condicionId);
        em.remove(condicion);
        return condicion;
    }

}

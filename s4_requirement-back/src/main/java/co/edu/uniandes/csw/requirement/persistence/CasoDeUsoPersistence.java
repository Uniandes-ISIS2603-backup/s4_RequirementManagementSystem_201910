/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
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
public class CasoDeUsoPersistence {

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public CasoDeUsoEntity create(CasoDeUsoEntity casoDeUsoEntity) {
        em.persist(casoDeUsoEntity);
        return casoDeUsoEntity;
    }

    public CasoDeUsoEntity find(Long casoDeUsoId) {
        return em.find(CasoDeUsoEntity.class, casoDeUsoId);
    }

    public List<CasoDeUsoEntity> findAll() {

        TypedQuery<CasoDeUsoEntity> query = em.createQuery("select u from CasoDeUsoEntity u", CasoDeUsoEntity.class);
        return query.getResultList();
    }
    
    public CasoDeUsoEntity findByName(String name){
        TypedQuery<CasoDeUsoEntity> query = em.createQuery("select u from CasoDeUsoEntity u where u.nombre = :nombre", CasoDeUsoEntity.class);
        query = query.setParameter("nombre", name);
        
        List<CasoDeUsoEntity> sameName= query.getResultList();
        CasoDeUsoEntity resultado;
        if(sameName==null){
            resultado=null;
        }
        else if(sameName.isEmpty()){
            resultado=null;
        }
        else{
            resultado= sameName.get(0);
        }
        return resultado;
    }
    
    public CasoDeUsoEntity update(CasoDeUsoEntity casoDeUso){
        return em.merge(casoDeUso);
    }
    
    public CasoDeUsoEntity delete(Long casoDeUsoId){
        CasoDeUsoEntity casoDeUso=em.find(CasoDeUsoEntity.class, casoDeUsoId);
        em.remove(casoDeUso);
        return casoDeUso;
    }
}

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
 * @author Sofia Alvarez
 */
@Stateless
public class CaminoPersistence {
    
    @PersistenceContext(unitName = "requirementPU") 
    protected EntityManager em;
   
   
   public CaminoEntity create(CaminoEntity caminoEntity){
       em.persist(caminoEntity);
       return caminoEntity;
   }
   
   public CaminoEntity find(Long caminoId){
       return em.find(CaminoEntity.class, caminoId);
   }
   
   public List<CaminoEntity> findAll(){
       TypedQuery<CaminoEntity> query = em.createQuery("select u from CaminoEntity u", CaminoEntity.class);
       return query.getResultList();
   }
    
}

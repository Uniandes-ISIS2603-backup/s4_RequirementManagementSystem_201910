/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
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
public class DRSPersistence {

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public DRSEntity create(DRSEntity drsEntity) {
        em.persist(drsEntity);
        return drsEntity;
    }

    public DRSEntity find(Long drsId) {
        return em.find(DRSEntity.class, drsId);
    }
    
    public DRSEntity findByVersion(Integer drsVersion) {
            TypedQuery query = em.createQuery("Select e From DRSEntity e where e.version = :version", DRSEntity.class);
             query = query.setParameter("version", drsVersion);
             List<DRSEntity> sameVersion = query.getResultList();
             DRSEntity result;
             if(sameVersion == null)
             {
                 result = null;
             }
             else if(sameVersion.isEmpty())
             {
                 result = null;
             }
             else
             {
                 result = sameVersion.get(0);
             }
            return result;
    }

    public List<DRSEntity> findAll() {
        TypedQuery<DRSEntity> query = em.createQuery("select u from DRSEntity u", DRSEntity.class);
        return query.getResultList();
    }
    
     public DRSEntity update(DRSEntity drs){
        return em.merge(drs);
    }
    
    public DRSEntity delete(Long drsId){
        DRSEntity drs=em.find(DRSEntity.class, drsId);
        em.remove(drs);
        return drs;
    }

}

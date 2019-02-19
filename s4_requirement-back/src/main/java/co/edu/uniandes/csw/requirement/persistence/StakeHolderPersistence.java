/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class StakeHolderPersistence {

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public StakeHolderEntity create(StakeHolderEntity stakeHolderEntity) {
        em.persist(stakeHolderEntity);
        return stakeHolderEntity;
    }

    public StakeHolderEntity find(Long stakeHolderID) {
        return em.find(StakeHolderEntity.class, stakeHolderID);
    }

    public List<StakeHolderEntity> findAll() {
        TypedQuery query = em.createQuery("select u from StakeHolderEntity u", StakeHolderEntity.class);
        return query.getResultList();
    }

}

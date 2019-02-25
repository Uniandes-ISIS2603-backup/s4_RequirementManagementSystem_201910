/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
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

    /**
     * Creacion del entity manager
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * 
     * @param stakeHolderEntity
     * @return stakeholder
     */
    public StakeHolderEntity create(StakeHolderEntity stakeHolderEntity) {
        em.persist(stakeHolderEntity);
        return stakeHolderEntity;
    }

    /**
     * 
     * @param stakeHolderID
     * @return stakeholder encontrado
     */
    public StakeHolderEntity find(Long stakeHolderID) {
        return em.find(StakeHolderEntity.class, stakeHolderID);
    }

    /**
     * 
     * @return todos los stakeholders
     */
    public List<StakeHolderEntity> findAll() {
        TypedQuery query = em.createQuery("select u from StakeHolderEntity u", StakeHolderEntity.class);
        return query.getResultList();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
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
public class OrganizacionPersistence {

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public OrganizacionEntity create(OrganizacionEntity organizacionEntity) {
        em.persist(organizacionEntity);
        return organizacionEntity;
    }

    public OrganizacionEntity find(Long stakeHolderID) {
        return em.find(OrganizacionEntity.class, stakeHolderID);
    }

    public List<OrganizacionEntity> findAll() {
        TypedQuery query = em.createQuery("select u from OrganizacionEntity u", OrganizacionEntity.class);
        return query.getResultList();
    }
}

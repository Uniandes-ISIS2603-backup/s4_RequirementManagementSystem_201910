/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import java.util.List;
import javax.ejb.*;
import javax.persistence.*;

/**
 *
 * @author davidmanosalva
 */
@Stateless
public class ObjetivoPersistence {

    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    public ObjetivoEntity create(ObjetivoEntity op) {
        em.persist(op);
        return op;
    }

    public ObjetivoEntity find(Long objetivoId) {
        return em.find(ObjetivoEntity.class, objetivoId);
    }

    public List<ObjetivoEntity> findAll() {
        TypedQuery<ObjetivoEntity> query = em.createQuery("select u from ObjetivoEntity u", ObjetivoEntity.class);

        return query.getResultList();
    }

}

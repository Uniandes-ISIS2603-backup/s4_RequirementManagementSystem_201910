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

    /**
     * Creacion del entity manager
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * crea un stakeholder
     * @param stakeHolderEntity
     * @return stakeholder
     */
    public StakeHolderEntity create(StakeHolderEntity stakeHolderEntity) {
        em.persist(stakeHolderEntity);
        return stakeHolderEntity;
    }

    /**
     * busca un stakeholder con id por parametro
     * @param stakeHolderID
     * @return stakeholder encontrado
     */
    public StakeHolderEntity find(Long stakeHolderID) {
        return em.find(StakeHolderEntity.class, stakeHolderID);
    }

    /**
     * retorna todos los stakeholders
     * @return todos los stakeholders
     */
    public List<StakeHolderEntity> findAll() {
        TypedQuery query = em.createQuery("select u from StakeHolderEntity u", StakeHolderEntity.class);
        return query.getResultList();
    }
    
    /**
     * actualiza un stakeholder
     * @param stakeholderEntity
     * @return 
     */
    public StakeHolderEntity update(StakeHolderEntity stakeholderEntity) {
        return em.merge(stakeholderEntity);
    }

    /**
     * Borra un stakeholder de la base de datos identifica por el id
     * @param stakeholderId: id correspondiente al stakeholder
     */
    public void delete(Long stakeholderId) {
        TypedQuery<StakeHolderEntity> query = em.createQuery("Select e From StakeHolderEntity e where e.id = :id", StakeHolderEntity.class);
        query = query.setParameter("id", stakeholderId);
        StakeHolderEntity stakeholderEntity = query.getSingleResult();
        em.remove(stakeholderEntity);
    }
}

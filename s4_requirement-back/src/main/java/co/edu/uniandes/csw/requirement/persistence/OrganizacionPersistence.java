package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
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
public class OrganizacionPersistence {

    /**
     * Creacion de entity manager
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * crea una organizacion
     * @param organizacionEntity
     * @return organizacion creada 
     */
    public OrganizacionEntity create(OrganizacionEntity organizacionEntity) {
        em.persist(organizacionEntity);
        return organizacionEntity;
    }

    /**
     * busca una organizacion con id dado por parametro
     * @param organizacionID
     * @return organizacion encontrada
     */
    public OrganizacionEntity find(Long organizacionID) {
        return em.find(OrganizacionEntity.class, organizacionID);
    }

    /**
     * retorna lista de todas las organizaciones
     * @return todas las organizaciones
     */
    public List<OrganizacionEntity> findAll() {
        TypedQuery query = em.createQuery("select u from OrganizacionEntity u", OrganizacionEntity.class);
        return query.getResultList();
    }
    
    /**
     * encuetra organizacion por nombre
     * @param nombre
     * @return organizacion con nombre buscado, null si no hay
     */
    public OrganizacionEntity findByName (String nombre){
        TypedQuery<OrganizacionEntity> query = em.createQuery("Select e From OrganizacionEntity e where e.nombre = :nombre", OrganizacionEntity.class);
        query = query.setParameter("nombre", nombre);
        List <OrganizacionEntity> sameName = query.getResultList();
        OrganizacionEntity result;
        
        if(sameName == null){
           result = null;
        }
        else if(sameName.isEmpty()){
            result = null;
        }
        else {
            result = sameName.get(0);
        }
        
        return result;   
    }

/**
 * acutaliza organizacion
 * @param organizacionEntity
 * @return organizacion actualizada
 */
    public OrganizacionEntity update(OrganizacionEntity organizacionEntity) {
        return em.merge(organizacionEntity);
    }
    
        
     /**
     * Borra una organizacion de la base de datos identificada por id
     * @param organizacionId: id correspondiente a la organizacion a borrar.
     */
    public void delete(Long organizacionId) {
        TypedQuery<OrganizacionEntity> query = em.createQuery("Select e From OrganizacionEntity e where e.id = :id", OrganizacionEntity.class);
        query = query.setParameter("id", organizacionId);
        OrganizacionEntity organizationEntity = query.getSingleResult();
        em.remove(organizationEntity);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.List;
import java.util.logging.Level;
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
     * 
     * @param organizacionEntity
     * @return organizacion creada 
     */
    public OrganizacionEntity create(OrganizacionEntity organizacionEntity) {
        em.persist(organizacionEntity);
        return organizacionEntity;
    }

    /**
     * 
     * @param organizacionID
     * @return organizacion encontrada
     */
    public OrganizacionEntity find(Long organizacionID) {
        return em.find(OrganizacionEntity.class, organizacionID);
    }

    /**
     * 
     * @return todas las organizaciones
     */
    public List<OrganizacionEntity> findAll() {
        TypedQuery query = em.createQuery("select u from OrganizacionEntity u", OrganizacionEntity.class);
        return query.getResultList();
    }
    
    /**
     * 
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

    public OrganizacionEntity update(OrganizacionEntity organizacionEntity) {
        return em.merge(organizacionEntity);
    }
    
        
     /**
     * Borra una organizacion de la base de datos recibiendo como argumento el
     * id de la organizacion
     *
     * @param organizacionId: id correspondiente a la organizacion a borrar.
     */
    public void delete(Long organizacionId) {
        LOGGER.log(Level.INFO, "Borrando organizacion con id={0}", organizacionId);
        // Se hace uso OrganizationEntity de mismo método que esta explicado en public OrganizationEntity find(Long id) para obtener la organizacion a borrar.
        TypedQuery<OrganizacionEntity> query = em.createQuery("Select e From OrganizacionEntity e where e.id = :id", OrganizacionEntity.class);
        query = query.setParameter("id", organizacionId);
        OrganizacionEntity organizationEntity = query.getSingleResult();
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
        EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
        Es similar a "delete from OrganizationEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(organizationEntity);
    }
}

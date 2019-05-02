package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
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
public class UsuarioPersistence {

    /**
     * Creacion del entity manager
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * crea un usuario
     * @param usuarioEntity
     * @return usuario
     */
    public UsuarioEntity create(UsuarioEntity usuarioEntity) {
        em.persist(usuarioEntity);
        return usuarioEntity;
    }

    /**
     * busca un usuario con id por parametro
     * @param usuarioID
     * @return usuario encontrado
     */
    public UsuarioEntity find(Long usuarioID) {
        return em.find(UsuarioEntity.class, usuarioID);
    }

    /**
     * retorna todos los usuarios
     * @return todos los usuarios
     */
    public List<UsuarioEntity> findAll() {
        TypedQuery query = em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        return query.getResultList();
    }
    
    /**
     * actualiza un usuario
     * @param usuarioEntity
     * @return 
     */
    public UsuarioEntity update(UsuarioEntity usuarioEntity) {
        return em.merge(usuarioEntity);
    }

    /**
     * Borra un usuario de la base de datos identifica por el id
     * @param usuarioId: id correspondiente al usuario
     */
    public void delete(Long usuarioId) {
        TypedQuery<UsuarioEntity> query = em.createQuery("Select e From UsuarioEntity e where e.id = :id", UsuarioEntity.class);
        query = query.setParameter("id", usuarioId);
        UsuarioEntity usuarioEntity = query.getSingleResult();
        em.remove(usuarioEntity);
    }
    
     /**
     * encuetra organizacion por nombre
     * @param nombre
     * @return organizacion con nombre buscado, null si no hay
     */
    public UsuarioEntity findByName (String usuario){
        TypedQuery<UsuarioEntity> query = em.createQuery("Select e From UsuarioEntity e where e.usuario = :usuario",UsuarioEntity.class);
        query = query.setParameter("usuario", usuario);
        List <UsuarioEntity> sameName = query.getResultList();
        UsuarioEntity result;
        
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
}

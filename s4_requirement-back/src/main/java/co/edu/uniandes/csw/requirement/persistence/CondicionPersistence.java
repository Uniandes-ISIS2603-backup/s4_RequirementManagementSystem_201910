/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sofia Sarmiento
 */
@Stateless
public class CondicionPersistence {

    /**
     * Contexto de persistencia del entity manager.
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * Método para crear (persistir) un objeto condicion.
     * @param condicionEntity la condicion a persistir.
     * @return la condicion persistida.
     */
    public CondicionEntity create(CondicionEntity condicionEntity) {
        em.persist(condicionEntity);
        return condicionEntity;
    }

    /**
     * Método para encontrar una condicion por su id.
     * @param condicionId id de la condicion a buscar.
     * @param casoDeUsoId
     * @return devuelve la condicion encontrada.
     */
    public CondicionEntity find(Long casoDeUsoId, Long condicionId) {
        TypedQuery<CondicionEntity> q = em.createQuery("select p from CondicionEntity p where (p.casos.id = :casoDeUsoId) and (p.id = :condicionId)", CondicionEntity.class);
        q.setParameter("casoDeUsoId", casoDeUsoId);
        q.setParameter("condicionId", condicionId);
        List<CondicionEntity> results = q.getResultList();
        CondicionEntity condiciones = null;
        if (results == null) {
            condiciones = null;
        } else if (results.isEmpty()) {
            condiciones = null;
        } else if (results.size() >= 1) {
            condiciones = results.get(0);
        }
        return condiciones;     
    
    }

    
    public CondicionEntity findByDescripcion(String description){
        TypedQuery<CondicionEntity> query = em.createQuery("select u from CondicionEntity u where u.descripcion = :descripcion", CondicionEntity.class);
        query = query.setParameter("descripcion", description);
        
        List<CondicionEntity> sameDes= query.getResultList();
        CondicionEntity resultado;
        if(sameDes==null){
            resultado=null;
        }
        else if(sameDes.isEmpty()){
            resultado=null;
        }
        else{
            resultado= sameDes.get(0);
        }
        return resultado;
    }
    
    /**
     * Método que actualiza una condicion
     * @param condicion es la condicion a actualizar
     * @return la condicion actualizada
     */
    public CondicionEntity update(CondicionEntity condicion){
        return em.merge(condicion);
    }
    
    /**
     * Método que elimina una condicion, dado su id.
     * @param condicionId id de la condicion a eliminar
     * @return la condicion eliminada.
     */
    public void delete(Long condicionId){
        CondicionEntity condicion=em.find(CondicionEntity.class, condicionId);
        em.remove(condicion);
    }

}

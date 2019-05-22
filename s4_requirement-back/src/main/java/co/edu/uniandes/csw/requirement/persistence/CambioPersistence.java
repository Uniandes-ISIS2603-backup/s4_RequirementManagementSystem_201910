/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase de persistencia para un cambio
 * @author Sofia Alvarez
 */
@Stateless
public class CambioPersistence {
    /**
     * Contexto de persistencia del entity manager.
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * Método para crear (persistir) un objeto cambio.
     * @param cambioEntity el cambio a persistir.
     * @return el cambio persistido.
     */
    public CambioEntity create(CambioEntity cambioEntity) {
        em.persist(cambioEntity);
        return cambioEntity;
    }

    public CambioEntity findWithObjetivo(Long objetivoId, Long cambioId) {
        TypedQuery<CambioEntity> q = em.createQuery("select p from CambioEntity p where (p.objetivo.id = :objetivoId) and (p.id = :cambioId)", CambioEntity.class);
        q.setParameter("objetivoId", objetivoId);
        q.setParameter("cambioId", cambioId);
        List<CambioEntity> results = q.getResultList();
        CambioEntity cambioE = null;
        if (results == null) {
            cambioE = null;
        } else if (results.isEmpty()) {
            cambioE = null;
        } else if (results.size() >= 1) {
            cambioE = results.get(0);
        }
        return cambioE;    
    }
    
    public CambioEntity findWithRequisito(Long requisitoId, Long cambioId) {
        TypedQuery<CambioEntity> q = em.createQuery("select p from CambioEntity p where (p.requisito.id = :requisito) and (p.id = :cambioId)", CambioEntity.class);
        q.setParameter("requisitoId", requisitoId);
        q.setParameter("cambioId", cambioId);
        List<CambioEntity> results = q.getResultList();
        CambioEntity cambioE = null;
        if (results == null) {
            cambioE  = null;
        } else if (results.isEmpty()) {
            cambioE  = null;
        } else if (results.size() >= 1) {
            cambioE = results.get(0);
        }
        return cambioE ;    
    }
    
    
    /**
     * Método que actualiza un cambio
     * @param cambio es el cambio a actualizar
     * @return el cambio actualizado
     */
    public CambioEntity update(CambioEntity cambio){
        return em.merge(cambio);
    }
    
    /**
     * Método que elimina un cambio, dado su id.
     * @param cambioId id del cambio a eliminar
     * @return el cambio eliminado.
     */
    public void delete(Long cambioId){
        CambioEntity cambio = em.find(CambioEntity.class, cambioId);
        em.remove(cambio);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *Clase de persistencia que define el recurso de aprobación
 * @author Sofia Alvarez
 */
@Stateless
public class AprobacionPersistence {

    /**
     * Referencia al entity manager.
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;
    
    /**
     * Método para crear un objeto de tipo aprobación entity
     * @param aprobacion la aprobación a persistir.
     * @return la entidad persistida.
     */
    public AprobacionEntity create(AprobacionEntity aprobacion){
        em.persist(aprobacion);
        return aprobacion;
    }

    /**
     * Método para encontrar una aprobación por id
     * @param aprobacionId el id de la aprobación a buscar
     * @return la aprobación encontrada
     */
    public AprobacionEntity findWithObjetivo(Long objetivoId, Long aprobacionId) {
        TypedQuery<AprobacionEntity> q = em.createQuery("select p from AprobacionEntity p where (p.objetivo.id = :objetivoId) and (p.id = :aprobacionId)", AprobacionEntity.class);
        q.setParameter("objetivoId", objetivoId);
        q.setParameter("aprobacionId", aprobacionId);
        List<AprobacionEntity> results = q.getResultList();
        AprobacionEntity aprobacionE = null;
        if (results == null) {
            aprobacionE = null;
        } else if (results.isEmpty()) {
            aprobacionE = null;
        } else if (results.size() >= 1) {
            aprobacionE = results.get(0);
        }
        return aprobacionE;    
    }
    
    public AprobacionEntity findWithRequisito(Long requisitoId, Long aprobacionId) {
        TypedQuery<AprobacionEntity> q = em.createQuery("select p from AprobacionEntity p where (p.requisito.id = :requisitoId) and (p.id = :aprobacionId)", AprobacionEntity.class);
        q.setParameter("requisitoId", requisitoId);
        q.setParameter("aprobacionId", aprobacionId);
        List<AprobacionEntity> results = q.getResultList();
        AprobacionEntity aprobacionE = null;
        if (results == null) {
            aprobacionE = null;
        } else if (results.isEmpty()) {
            aprobacionE = null;
        } else if (results.size() >= 1) {
            aprobacionE = results.get(0);
        }
        return aprobacionE;    
    }
    
    /**
     * Método para actualizar una aprobación
     * @param aprobacion es la aprobación a actualizar
     * @return la aprobación actualizada
     */
    public AprobacionEntity update(AprobacionEntity aprobacion){
        return em.merge(aprobacion);
    }
    
    /**
     * Método que elimina una aprobación con un id dado por parámetro.
     * @param aprobacionId id de la aprobación a eliminar
     * @return la aprobación eliminada.
     */
    public void delete(Long aprobacionId){
        AprobacionEntity aprobacion = em.find(AprobacionEntity.class, aprobacionId);
        em.remove(aprobacion);
    }
}

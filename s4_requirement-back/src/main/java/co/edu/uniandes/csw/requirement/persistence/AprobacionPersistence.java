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
    public AprobacionEntity find(Long aprobacionId) {
        return em.find(AprobacionEntity.class, aprobacionId);
    }
    
    /**
     * Método para encontrar todas las aprobaciones de la base de datos.
     * @return una lista con todas las aprobaciones encontradas.
     */
    public List<AprobacionEntity> findAll(){
        TypedQuery<AprobacionEntity> query = em.createQuery("select u from AprobacionEntity u", AprobacionEntity.class);
        return query.getResultList();
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
    public AprobacionEntity delete(Long aprobacionId){
        AprobacionEntity aprobacion = em.find(AprobacionEntity.class, aprobacionId);
        em.remove(aprobacion);
        return aprobacion;
    }
}

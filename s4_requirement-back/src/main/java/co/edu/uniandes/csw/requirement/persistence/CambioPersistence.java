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

    /**
     * Método para encontrar un cambio por su id.
     * @param cambioId id del cambio a buscar.
     * @return devuelve el cambio encontrado.
     */
    public CambioEntity find(Long cambioId) {
        return em.find(CambioEntity.class, cambioId);
    }
    /**
     * Método que encuentra todos los cambios.
     * @return lista con todos los cambios.
     */
    public List<CambioEntity> findAll(){
        TypedQuery<CambioEntity> query = em.createQuery("select u from CambioEntity u", CambioEntity.class);
        return query.getResultList();
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
    public CambioEntity delete(Long cambioId){
        CambioEntity cambio = em.find(CambioEntity.class, cambioId);
        em.remove(cambio);
        return cambio;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.persistence;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
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
public class CasoDeUsoPersistence {

    /**
     * Contexto de persistencia del entity manager.
     */
    @PersistenceContext(unitName = "requirementPU")
    protected EntityManager em;

    /**
     * Método para crear (persistir) un objeto caso de uso.
     * @param casoDeUsoEntity el caso de uso a persistir.
     * @return el caso de uso persistido.
     */
    public CasoDeUsoEntity create(CasoDeUsoEntity casoDeUsoEntity) {
        em.persist(casoDeUsoEntity);
        return casoDeUsoEntity;
    }

    /**
     * Método para encontrar un caso de uso por su id.
     * @param casoDeUsoId id del caso de uso a buscar.
     * @param requisitoId 
     * @return devuelve el caso de uso encontrado.
     */
    public CasoDeUsoEntity find(Long requisitoId,Long casoDeUsoId) {
       TypedQuery<CasoDeUsoEntity> q = em.createQuery("select p from CasoDeUsoEntity p where (p.requisito.id = :requisitoId) and (p.id = :casoDeUsoId)", CasoDeUsoEntity.class);
        q.setParameter("requisitoId", requisitoId);
        q.setParameter("casoDeUsoId", casoDeUsoId);
        List<CasoDeUsoEntity> results = q.getResultList();
        CasoDeUsoEntity casos = null;
        if (results == null) {
            casos = null;
        } else if (results.isEmpty()) {
            casos = null;
        } else if (results.size() >= 1) {
            casos = results.get(0);
        }
        return casos;
    }

    /**
     * Método que encuentra todos los casos de uso.
     * @return lista con todos los casos de uso.
     */

    
    public CasoDeUsoEntity findByName(String name){
        TypedQuery<CasoDeUsoEntity> query = em.createQuery("select u from CasoDeUsoEntity u where u.nombre = :nombre", CasoDeUsoEntity.class);
        query = query.setParameter("nombre", name);
        
        List<CasoDeUsoEntity> sameName= query.getResultList();
        CasoDeUsoEntity resultado;
        if(sameName==null){
            resultado=null;
        }
        else if(sameName.isEmpty()){
            resultado=null;
        }
        else{
            resultado= sameName.get(0);
        }
        return resultado;
    }
   
    /**
     * Método que actualiza un caso de uso
     * @param casoDeUso es el caso de uso a actualizar
     * @return el caso de uso actualizado
     */
    public CasoDeUsoEntity update(CasoDeUsoEntity casoDeUso){
        return em.merge(casoDeUso);
    }
  
    /**
     * Método que elimina un caso de uso, dado su id.
     * @param casoDeUsoId id del caso de uso a eliminar
     * @return el caso de uso eliminado.
     */
    public void delete(Long casoDeUsoId){
        CasoDeUsoEntity casoDeUso=em.find(CasoDeUsoEntity.class, casoDeUsoId);
        em.remove(casoDeUso);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase de logica para una condicion
 * @author Sofia Sarmiento
 */

@Stateless
public class CondicionLogic {
    
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(CondicionLogic.class.getName());

    /**
     * Inyección de persistencia de una condicion
     */
    @Inject
    private CondicionPersistence persistence;
    
    /**
    * Metodo que crea la logica de una condicion
    * @param condicion condicion a persistir
    * @return condicion persistida
    * @throws BusinessLogicException  si ya existe una condicion con esa decripcion
    */
    public CondicionEntity createCondicion(CondicionEntity condicion) throws BusinessLogicException{
        
        if(persistence.findByDescripcion(condicion.getDescripcion())!=null){
            throw new BusinessLogicException("Ya existe una condicion con esa descripcion: "+condicion.getDescripcion());
        }
        condicion= persistence.create(condicion);
        return condicion;
    }
    
    /**
     * Encuentra un caso de uso dado su id
     * @param condicionId id de la condicion a encontrar
     * @return condicion encontrado
     */
    public CondicionEntity getCondicion(Long condicionId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la condicion con id = {0}", condicionId);
        CondicionEntity condicion = persistence.find(condicionId);
        if (condicion == null) {
            LOGGER.log(Level.SEVERE, "La condicion con el id = {0} no existe", condicionId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la condicion con id = {0}", condicionId);
        return condicion;
    }
    
    public CondicionEntity getCondicionDescripcion(String descr){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la condicion por descripcion", descr);
        CondicionEntity condicion = persistence.findByDescripcion(descr);
        if (condicion == null) {
            LOGGER.log(Level.SEVERE, "La condicion con la descripcion no existe", descr);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la condicion con la descripcion", descr);
        return condicion;
    }
    
    /**
     * Lista con todas las condiciones.
     * @return lista con todas las condiciones
     */
    public List<CondicionEntity> getCondiciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las condiciones");
        List<CondicionEntity> condiciones = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las condiciones");
        return condiciones;
    }
    
    /**
     * Actualiza una condicion.
     * @param condicionId la condicion a actualizar
     * @return condicion actualizada
     */
     public CondicionEntity updateCondicion(Long condicionId, CondicionEntity condicion) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el caso de uso con id = {0}", condicionId);
        CondicionEntity newEntity = persistence.update(condicion);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el caso de uso con id = {0}", condicion.getId());
        return newEntity;
    }
     
    /**
     * Elimina una condicion, dada su id
     * @param condicionId id de la condicion a eliminar
     */
     public void deleteCondicion(Long condicionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", condicionId);
       
        persistence.delete(condicionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", condicionId);
    }
}

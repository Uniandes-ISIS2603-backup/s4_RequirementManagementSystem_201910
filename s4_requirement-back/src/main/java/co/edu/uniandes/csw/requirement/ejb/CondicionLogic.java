/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
    private CondicionPersistence condicionPersistence;
    
    @Inject
    private CasoDeUsoPersistence casoPersistence;

    
    /**
    * Metodo que crea la logica de una condicion
    * @param condicion condicion a persistir
    * @param requisitoId
    * @param casoDeUsoId
    * @return condicion persistida
    * @throws BusinessLogicException  si ya existe una condicion con esa decripcion
    */
    public CondicionEntity createCondicion(Long requisitoId, Long casoDeUsoId, CondicionEntity condicion) throws BusinessLogicException{
        
        if(condicionPersistence.findByDescripcion(condicion.getDescripcion())!=null){
            throw new BusinessLogicException("Ya existe una condicion con esa descripcion: "+condicion.getDescripcion());
        }
        
        CasoDeUsoEntity caso = casoPersistence.find(requisitoId, casoDeUsoId);
        condicion.setCasos(caso);
        LOGGER.log(Level.INFO, "Termina proceso de creación del objetivo");
        return condicionPersistence.create(condicion);
    }
    
    /**
     * Encuentra un caso de uso dado su id
     * @param condicionId id de la condicion a encontrar
     * @param casoDeUsoId
     * @return condicion encontrado
     */
    public CondicionEntity getCondicion(Long casoDeUsoId, Long condicionId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la condicion con id = {0}", condicionId);
        CondicionEntity condicion = condicionPersistence.find(casoDeUsoId, condicionId);
        if (condicion == null) {
            LOGGER.log(Level.SEVERE, "La condicion con el id = {0} no existe", condicionId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la condicion con id = {0}", condicionId);
        return condicion;
    }
    
    public CondicionEntity getCondicionDescripcion(String descr){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la condicion por descripcion", descr);
        CondicionEntity condicion = condicionPersistence.findByDescripcion(descr);
        if (condicion == null) {
            LOGGER.log(Level.SEVERE, "La condicion con la descripcion no existe", descr);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la condicion con la descripcion", descr);
        return condicion;
    }
    
    /**
     * Lista con todas las condiciones.
     * @param requisitoId
     * @param casoDeUsoId
     * @return lista con todas las condiciones
     */
    public List<CondicionEntity> getCondiciones(Long requisitoId, Long casoDeUsoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las condiciones");
        
        CasoDeUsoEntity caso = casoPersistence.find(requisitoId, casoDeUsoId);

        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las condiciones");
        return caso.getCondiciones();
    }
    
    /**
     * Actualiza una condicion.
     * @param requisitoId
     * @param casoDeUsoId la condicion a actualizar
     * @param condicion la condicion a actualizar
     * @return condicion actualizada
     */
     public CondicionEntity updateCondicion(Long requisitoId, Long casoDeUsoId, CondicionEntity condicion) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el caso de uso con id = {0}", casoDeUsoId);
        CasoDeUsoEntity caso = casoPersistence.find(requisitoId, casoDeUsoId);
        condicion.setCasos(caso);
        condicionPersistence.update(condicion);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el caso de uso con id = {0}", condicion.getId());
        return condicion;
    }
     
    /**
     * Elimina una condicion, dada su id
     * @param casoDeUsoId
     * @param condicionId id de la condicion a eliminar
     */
     public void deleteCondicion(Long casoDeUsoId, Long condicionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", condicionId);
        CondicionEntity condicion = getCondicion(casoDeUsoId, condicionId);
        if(condicion == null)
        {
            throw new BusinessLogicException("La condicion con id = " + condicionId + " no esta asociado a el caso de uso con id = " + casoDeUsoId);
        }
        condicionPersistence.delete(condicion.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", condicionId);
    }
}

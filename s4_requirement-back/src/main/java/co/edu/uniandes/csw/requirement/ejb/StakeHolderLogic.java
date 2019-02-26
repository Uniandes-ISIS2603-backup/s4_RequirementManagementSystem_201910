/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class StakeHolderLogic {
    
/**
 * Atributo persistence creado
 */    
    @Inject
    private StakeHolderPersistence persistence;
    
    /**
     * 
     * @param stakeHolder
     * @return stakeholder creado
     */
    public StakeHolderEntity createStakeHolder (StakeHolderEntity stakeHolder) throws BusinessLogicException{
       
        if (stakeHolder.getNombre()== null){
            throw new BusinessLogicException("El nombre del stakeholder no puede ser nulo");
        }
        
        stakeHolder = persistence.create(stakeHolder);
        return stakeHolder;
    }

     /**
     * Obtener una organizacion por medio de su id.
     *
     * @param stakeholderId: id de la organizacion para ser buscada.
     * @return la organizacion solicitada por medio de su id.
     */
    public StakeHolderEntity getStakeHolder(Long stakeholderId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar organizacion con id = {0}", stakeholderId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        StakeHolderEntity organizacion = persistence.find(stakeholderId);
        if (organizacion == null) {
            LOGGER.log(Level.SEVERE, "La organizacion con el id = {0} no existe", stakeholderId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar organizacion con id = {0}", stakeholderId);
        return organizacion;
    }
    
        /**
     * Actualizar una organizacion.
     *
     * @param stakeholderId: id de la organizacion para buscarla en la base de
     * datos.
     * @param stakeholderEntity: organizacion con los cambios para ser
     * actualizada, por ejemplo el nombre.
     * @return la organizacion con los cambios actualizados en la base de datos.
     */
    public StakeHolderEntity updateStakeHolder(Long stakeholderId, StakeHolderEntity stakeholderEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar organizacion con id = {0}", stakeholderId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        StakeHolderEntity newEntity = persistence.update(stakeholderEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar organizacion con id={0}", stakeholderEntity.getId());
        return newEntity;
    }
    
     /**
     * Borrar un organizacion
     *
     * @param organizacionsId: id de la organizacion a borrar
     * @throws BusinessLogicException si la organizacion tiene un premio
     * asociado.
     */
    public void deleteStakeHolder(Long organizacionsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar organizacion con id = {0}", organizacionsId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        StakeHolderEntity organizacionEntity = persistence.find(organizacionsId);
        persistence.delete(organizacionsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar organizacion con id = {0}", organizacionsId);
    }
    
    
}

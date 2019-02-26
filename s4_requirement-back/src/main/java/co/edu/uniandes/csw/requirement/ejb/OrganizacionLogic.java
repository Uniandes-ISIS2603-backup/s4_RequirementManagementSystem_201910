/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class OrganizacionLogic {
    
/**
 * Atributo persistence creado
 */    
    @Inject
    private OrganizacionPersistence persistence;
    
    
    /**
     * 
     * @param organizacion
     * @return organizacion creada
     */
    public OrganizacionEntity createOrganizacion (OrganizacionEntity organizacion) throws BusinessLogicException{
       
        if ((persistence.findByName(organizacion.getNombre()))!= null){
            throw new BusinessLogicException("Ya existe una organizacion con el nombre: "+organizacion.getNombre());
        }
        organizacion = persistence.create(organizacion);
        return organizacion;
    } 
    
     /**
     * Obtener una organizacion por medio de su id.
     *
     * @param organizacionId: id de la organizacion para ser buscada.
     * @return la organizacion solicitada por medio de su id.
     */
    public OrganizacionEntity getOrganizacion(Long organizacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar organizacion con id = {0}", organizacionId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        OrganizacionEntity organizacion = persistence.find(organizacionId);
        if (organizacion == null) {
            LOGGER.log(Level.SEVERE, "La organizacion con el id = {0} no existe", organizacionId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar organizacion con id = {0}", organizacionId);
        return organizacion;
    }
    
        /**
     * Actualizar una organizacion.
     *
     * @param organizationsId: id de la organizacion para buscarla en la base de
     * datos.
     * @param organizationEntity: organizacion con los cambios para ser
     * actualizada, por ejemplo el nombre.
     * @return la organizacion con los cambios actualizados en la base de datos.
     */
    public OrganizacionEntity updateOrganizacion(Long organizationsId, OrganizacionEntity organizationEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar organizacion con id = {0}", organizationsId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        OrganizacionEntity newEntity = persistence.update(organizationEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar organizacion con id={0}", organizationEntity.getId());
        return newEntity;
    }
    
     /**
     * Borrar un organizacion
     *
     * @param organizacionsId: id de la organizacion a borrar
     * @throws BusinessLogicException si la organizacion tiene un premio
     * asociado.
     */
    public void deleteOrganizacion(Long organizacionsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar organizacion con id = {0}", organizacionsId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        OrganizacionEntity organizacionEntity = persistence.find(organizacionsId);
        persistence.delete(organizacionsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar organizacion con id = {0}", organizacionsId);
    }
    
    
    
}
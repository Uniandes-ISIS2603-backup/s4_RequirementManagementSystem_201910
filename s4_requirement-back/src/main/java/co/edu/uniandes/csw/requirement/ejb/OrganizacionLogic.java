/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import java.util.List;
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
        OrganizacionEntity organizacion = persistence.find(organizacionId);
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
        OrganizacionEntity newEntity = persistence.update(organizationEntity);
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
        OrganizacionEntity organizacionEntity = persistence.find(organizacionsId);
        persistence.delete(organizacionsId);
    }
    
     /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<OrganizacionEntity> getOrganizaciones() {
        List<OrganizacionEntity> organizaciones = persistence.findAll();
        return organizaciones;
    }
    
}
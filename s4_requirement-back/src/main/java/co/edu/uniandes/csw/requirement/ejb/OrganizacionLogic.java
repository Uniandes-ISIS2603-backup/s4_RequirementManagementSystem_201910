/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
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
    
}
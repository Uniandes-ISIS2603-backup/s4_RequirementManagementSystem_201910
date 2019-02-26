/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.OrganizacionDTO;
import co.edu.uniandes.csw.requirement.dtos.OrganizacionDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.OrganizacionLogic;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author estudiante
 */

@Path("organizacion")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrganizacionResource {
    
    private static final Logger LOGGER = Logger.getLogger(OrganizacionResource.class.getName());
    
    @Inject
    private OrganizacionLogic logica;

    @POST
    public OrganizacionDTO createOrganizacion(OrganizacionDTO organizacion) throws BusinessLogicException{
        
        OrganizacionEntity organizacionEntity = organizacion.toEntity();
        logica.createOrganizacion(organizacionEntity);
        return new OrganizacionDTO (organizacionEntity);
    }
    
    @GET
    @Path("{id: \\d+}")
    public OrganizacionDetailDTO getOrganizacion(@PathParam("id") Long id)
    {
        OrganizacionEntity entidad = logica.getOrganizacion(id);
        
        if(entidad == null)
            throw new WebApplicationException ("El recurso /organizaciones/"+id+" No existe. ", 404);
        return new OrganizacionDetailDTO(entidad);
    }

    @DELETE
    @Path("{id: \\d+}")
    public void deleteOrganizacion(@PathParam("id") Integer id)
    {
        return;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public OrganizacionDTO putOrganizacion(@PathParam("id") Integer id)
    {
        return null;
    }
    
}

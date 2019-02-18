/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.OrganizacionDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
    
    @POST
    public OrganizacionDTO createOrganizacion(OrganizacionDTO organizacion){
        
        return organizacion;
    }
    
}

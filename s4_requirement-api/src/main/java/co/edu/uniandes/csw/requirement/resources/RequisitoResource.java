/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.RequisitoDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("requisitos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class RequisitoResource
{
    private static final Logger LOGGER = Logger.getLogger(RequisitoResource.class.getName());
    
    @POST
    public RequisitoDTO createRequisito(RequisitoDTO req)
    {
        return req;
    }
    
    @GET
    @Path("{id: \\d+}")
    public RequisitoDTO getRequisito(@PathParam("id") Integer id)
    {
        return null;
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteRequisito(@PathParam("id") Integer id)
    {
        return;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public RequisitoDTO putRequisito(@PathParam("id") Integer id)
    {
        return null;
    }
    
}

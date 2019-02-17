/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;
import co.edu.uniandes.csw.requirement.dtos.DrsDTO;
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

/**
 *
 * @author Sofia Alvarez
 */
@Path("drs")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DrsResource 
{
     private static final Logger LOGGER = Logger.getLogger(DrsResource.class.getName());

     @POST
    public DrsDTO createDrs(DrsDTO drs) {

        return drs;
    }
    
     @GET
    @Path("{id: \\d+}")
    public DrsDTO getDrs (@PathParam("id") Integer id)
    {
        return null;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteDrs (@PathParam("id") Integer id)
    {
        
    }
    
    @PUT
    @Path("{id: \\d+}")
    public DrsDTO getDrs (@PathParam("id") Integer id, DrsDTO dto)
    {
        return null;
    }
}

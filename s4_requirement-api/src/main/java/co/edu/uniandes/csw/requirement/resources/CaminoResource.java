/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CaminoDTO;
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
@Path("camino")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CaminoResource 
{
     private static final Logger LOGGER = Logger.getLogger(CaminoResource.class.getName());

     @POST
    public CaminoDTO createDrs(CaminoDTO camino) {

        return camino;
    }
    
     @GET
    @Path("{id: \\d+}")
    public CaminoDTO getCamino (@PathParam("id") Integer id)
    {
        return null;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteCamino (@PathParam("id") Integer id)
    {
        
    }
    
    @PUT
    @Path("{id: \\d+}")
    public CaminoDTO getCamino (@PathParam("id") Integer id, CaminoDTO dto)
    {
        return null;
    }
}
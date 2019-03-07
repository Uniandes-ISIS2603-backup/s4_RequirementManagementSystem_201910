/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CondicionDTO;
import co.edu.uniandes.csw.requirement.ejb.CondicionLogic;
import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.logging.Level;
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

/**
 *
 * @author Sofia Sarmiento
 */
@Path("condiciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CondicionResource {
    
    @Inject
    private CondicionLogic condicionLogic;
    
    private static final Logger LOGGER = Logger.getLogger(CondicionResource.class.getName());
    
    @POST
    public CondicionDTO crearCondicion(CondicionDTO condicion) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CondicionResource createCondicion: input: {0}", condicion);
        CondicionEntity condicionEntity = condicion.toEntity();
        CondicionEntity nuevaCondicionEntity = condicionLogic.createCondicion(condicionEntity);
        CondicionDTO nuevacondicionDTO = new CondicionDTO(nuevaCondicionEntity);
        LOGGER.log(Level.INFO, "CondicionResource createCondicion: output: {0}", nuevacondicionDTO);
        return nuevacondicionDTO;
    }
    
    @GET
    @Path("{id: \\d+}")
    public CondicionDTO getCondicion (@PathParam("id") Long id)
    {
        return null;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteCondicion (@PathParam("id") Long id)
    {
        
    }
    
    @PUT
    @Path("{id: \\d+}")
    public CondicionDTO putCondicion (@PathParam("id") Long id, CondicionDTO dto)
    {
        return null;
    }
}

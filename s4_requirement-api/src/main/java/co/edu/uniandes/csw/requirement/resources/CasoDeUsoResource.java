/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CasoDeUsoDTO;
import co.edu.uniandes.csw.requirement.ejb.CasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
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
@Path("casos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CasoDeUsoResource {
    private static final Logger LOGGER = Logger.getLogger(CasoDeUsoResource.class.getName());
    
    @Inject
    private CasoDeUsoLogic casoDeUsoLogic;
    
    @POST
    public CasoDeUsoDTO crearCasoDeUso(CasoDeUsoDTO casoDeUso)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CasoDeUsoResource createCasoDeUso: input: {0}", casoDeUso);
        CasoDeUsoEntity casoEntity = casoDeUso.toEntity();
        CasoDeUsoEntity nuevocasoEntity = casoDeUsoLogic.createCasoDeUso(casoEntity);
        CasoDeUsoDTO nuevocasoDTO = new CasoDeUsoDTO(nuevocasoEntity);
        LOGGER.log(Level.INFO, "CasoDeUsoResource createCasoDeUso: output: {0}", nuevocasoDTO);
        return nuevocasoDTO;    }
    
    @GET
    @Path("{id: \\d+}")
    public CasoDeUsoDTO getCasoDeUso (@PathParam("id") Long id)
    {
        return null;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteCasoDeUso (@PathParam("id") Long id)
    {
        
    }
    
    @PUT
    @Path("{id: \\d+}")
    public CasoDeUsoDTO putCasoDeUso (@PathParam("id") Long id, CasoDeUsoDTO dto)
    {
        return null;
    }
}

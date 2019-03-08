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
import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.WebApplicationException;

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
    public CondicionDTO getCondicion (@PathParam("id") Long id)throws WebApplicationException {
        LOGGER.log(Level.INFO, "CondicionResource getCondicion: input: {0}", id);
        CondicionEntity condicionEntity = condicionLogic.getCondicion(id);
        if (condicionEntity == null) {
            throw new WebApplicationException("El recurso /condiciones/" + id + " no existe.", 404);
        }
        CondicionDTO condicionDTO = new CondicionDTO(condicionEntity);
        LOGGER.log(Level.INFO, "CondicionResource getCondicion: output: {0}", condicionDTO);
        return condicionDTO;
    }
    
    @GET
    public List<CondicionDTO> getCondiciones() {
        LOGGER.info("CondicionResource getCondiciones: input: void");
        List<CondicionDTO> listaCondiciones = listEntity2DTO(condicionLogic.getCondiciones());
        LOGGER.log(Level.INFO, "CondicionResource getCondiciones: output: {0}", listaCondiciones);
        return listaCondiciones;
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCondicion (@PathParam("id") Long id)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CondicionResource deleteCondicion: input: {0}", id);
        if (condicionLogic.getCondicion(id) == null) {
            throw new WebApplicationException("El recurso /condiciones/" + id + " no existe.", 404);
        }
        condicionLogic.deleteCondicion(id);
        LOGGER.info("CondicionResource deleteCondicion: output: void");
    }
    
    @PUT
    @Path("{id: \\d+}")
    public CondicionDTO putCondicion (@PathParam("id") Long id, CondicionDTO dto)throws WebApplicationException {
        LOGGER.log(Level.INFO, "CondicionResource putCondicion: input: id:{0} , dto: {1}", new Object[]{id, dto});
        dto.setId(id);
        if (condicionLogic.getCondicion(id) == null) {
            throw new WebApplicationException("El recurso /condiciones/" + id + " no existe.", 404);
        }
        CondicionDTO condicionDTO = new CondicionDTO(condicionLogic.updateCondicion(id, dto.toEntity()));
        LOGGER.log(Level.INFO, "CasoDeUsoResource putCasoDeUso: output: {0}", condicionDTO);
        return condicionDTO;
    }
    
     private List<CondicionDTO> listEntity2DTO(List<CondicionEntity> entityList) {
        List<CondicionDTO> list = new ArrayList<>();
        for (CondicionEntity entity : entityList) {
            list.add(new CondicionDTO(entity));
        }
        return list;
    }
}

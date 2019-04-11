/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CasoDeUsoDTO;
import co.edu.uniandes.csw.requirement.dtos.CasoDeUsoDetail;
import co.edu.uniandes.csw.requirement.ejb.CasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
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
    public CasoDeUsoDetail getCasoDeUso (@PathParam("id") Long id) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "CasoDeUsoResource getCasoDeUso: input: {0}", id);
        CasoDeUsoEntity casoEntity = casoDeUsoLogic.getCasoDeUso(id);
        if (casoEntity == null) {
            throw new WebApplicationException("El recurso /casos/" + id + " no existe.", 404);
        }
        CasoDeUsoDetail detailDTO = new CasoDeUsoDetail(casoEntity);
        LOGGER.log(Level.INFO, "CasoDeUsoResource getCasoDeUso: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    public List<CasoDeUsoDetail> getCasosDeUso() {
        LOGGER.info("CasoDeUsoResource getCasosDeUso: input: void");
        List<CasoDeUsoDetail> listaCasos = listEntity2DTO(casoDeUsoLogic.getCasosDeUso());
        LOGGER.log(Level.INFO, "CasoDeUsoResource getCasosDeUso: output: {0}", listaCasos);
        return listaCasos;
    }
    
     @DELETE
    @Path("{id: \\d+}")
    public void deleteCasoDeUso (@PathParam("id") Long id)throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CasoDeUsoResource deleteCasoDeUso: input: {0}", id);
        if (casoDeUsoLogic.getCasoDeUso(id) == null) {
            throw new WebApplicationException("El recurso /casos/" + id + " no existe.", 404);
        }
        casoDeUsoLogic.deleteCasoDeUso(id);
        LOGGER.info("CasoDeUsoResource deleteCasoDeUso: output: void");  
    }
    
    @PUT
    @Path("{id: \\d+}")
    public CasoDeUsoDTO putCasoDeUso (@PathParam("id") Long id, CasoDeUsoDTO dto)throws WebApplicationException {
        LOGGER.log(Level.INFO, "CasoDeUsoResource putCasoDeUso: input: id:{0} , dto: {1}", new Object[]{id, dto});
        dto.setId(id);
        if (casoDeUsoLogic.getCasoDeUso(id) == null) {
            throw new WebApplicationException("El recurso /casos/" + id + " no existe.", 404);
        }
        CasoDeUsoDetail detailDTO = new CasoDeUsoDetail(casoDeUsoLogic.updateCasoDeUso(id, dto.toEntity()));
        LOGGER.log(Level.INFO, "CasoDeUsoResource putCasoDeUso: output: {0}", detailDTO);
        return detailDTO;
    }
    
    private List<CasoDeUsoDetail> listEntity2DTO(List<CasoDeUsoEntity> entityList) {
        List<CasoDeUsoDetail> list = new ArrayList<>();
        for (CasoDeUsoEntity entity : entityList) {
            list.add(new CasoDeUsoDetail(entity));
        }
        return list;
    }
}

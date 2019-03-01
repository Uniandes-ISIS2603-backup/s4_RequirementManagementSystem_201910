/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.ObjetivoDTO;
import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 *
 * @author David Manosalva
 */
@Path("objetivos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ObjetivoResource {

    private static final Logger LOGGER = Logger.getLogger(ObjetivoResource.class.getName());

    @Inject
    private ObjetivoLogic objetivoLogic;

    @POST
    public ObjetivoDTO createObjetivo(ObjetivoDTO objetivo) {
        LOGGER.log(Level.INFO, "ObjetivoResource createObjetivo: input: {0}", objetivo);
        ObjetivoDTO objetivoDTO = new ObjetivoDTO(objetivoLogic.createObjetivo(objetivo.toEntity()));
        LOGGER.log(Level.INFO, "ObjetivoResource createObjetivo: output: {0}", objetivoDTO);
        return objetivoDTO;
    }

    /*
    @GET
    public List<ObjetivoDetailDTO> getObjetivos() {
        LOGGER.info("ObjetivoResource getObjetivos: input: void");
        List<ObjetivoDetailDTO> listaObjetivos = listEntity2DTO(objetivoLogic.getObjetivos());
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivos: output: {0}", listaObjetivos);
        return listaObjetivos;
    }
    
    @GET
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO getObjetivo(@PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivo: input: {0}", objetivosId);
        ObjetivoEntity objetivoEntity = objetivoLogic.getObjetivo(objetivosId);
        if (objetivoEntity == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO detailDTO = new ObjetivoDetailDTO(objetivoEntity);
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivo: output: {0}", detailDTO);
        return detailDTO;
    }

    @PUT
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO updateObjetivo(@PathParam("objetivosId") Long objetivosId, ObjetivoDetailDTO objetivo) {
        LOGGER.log(Level.INFO, "ObjetivoResource updateObjetivo: input: objetivosId: {0} , objetivo: {1}", new Object[]{objetivosId, objetivo});
        objetivo.setId(objetivosId);
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO detailDTO = new ObjetivoDetailDTO(objetivoLogic.updateObjetivo(objetivosId, objetivo.toEntity()));
        LOGGER.log(Level.INFO, "ObjetivoResource updateObjetivo: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{objetivosId: \\d+}")
    public void deleteObjetivo(@PathParam("objetivosId") Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoResource deleteObjetivo: input: {0}", objetivosId);
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        objetivoLogic.deleteObjetivo(objetivosId);
        LOGGER.info("ObjetivoResource deleteObjetivo: output: void");
    }
    
    //TODO Uno
    @Path("{objetivosId: \\d+}/books")
    public Class<ObjetivoBooksResource> getObjetivoBooksResource(@PathParam("objetivosId") Long objetivosId) {
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        return ObjetivoBooksResource.class;
    }
    
    private List<ObjetivoDetailDTO> listEntity2DTO(List<ObjetivoEntity> entityList) {
        List<ObjetivoDetailDTO> list = new ArrayList<>();
        for (ObjetivoEntity entity : entityList) {
            list.add(new ObjetivoDetailDTO(entity));
        }
        return list;
    }
    */
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CambioDTO;
import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.dtos.RequisitoDetailDTO;
import co.edu.uniandes.csw.requirement.dtos.StakeHolderDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author estudiante
 */
@Path("cambios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CambioResource{
    
    private static final Logger LOGGER = Logger.getLogger(CambioResource.class.getName());
    
    @Inject
    private CambioLogic cambioLogic;
    
    @Inject
    private StakeHolderLogic stakeHolderLogic;
    
    @Inject
    private ObjetivoLogic objetivoLogic;
    
    @Inject
    private RequisitoLogic requisitoLogic;
    
    @POST
    public CambioDTO createCambio(CambioDTO cambio) throws BusinessLogicException{
        CambioEntity entity = cambio.toEntity();
        entity = cambioLogic.createCambio(entity);
        return new CambioDTO(entity);
    }
    
    @GET
    public List<CambioDTO> getCambios(){
        List<CambioDTO> dtos = new ArrayList<CambioDTO>();
        List<CambioEntity> entities = cambioLogic.findAllCambios();
        for(CambioEntity entity:entities){
            dtos.add(new CambioDTO(entity));
        }
        return dtos;
    }
    
    @GET
    @Path("{id: \\d+}")
    public CambioDTO getCambio(@PathParam("id") Long id){
        CambioEntity entity = cambioLogic.findCambioById(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /cambios/"+id+" no existe.", 404);
        }
        return new CambioDTO(entity);
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public CambioDTO deleteCambio(@PathParam("id") Long id){
        CambioEntity entity = cambioLogic.deleteCambio(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /cambios/"+id+" no existe.", 404);
        }
        CambioDTO dto = new CambioDTO(entity);
        return dto;
    }
    
  @PUT 
    @Path("{id1: \\d+}/stakeholder/{id2: \\d+}")
    public CambioDTO changeStakeHolder(@PathParam("id1") Long idCambio, @PathParam("id2") Long idAprobador){
        CambioEntity aprobacion = cambioLogic.findCambioById(idCambio);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        /*StakeHolderEntity stakeHolder = requisitoLogic.find(idRequisito);
        if(stakeholder == null){
            throw new WebApplicationException("El recurso /stakeholders/"+idAprobador+" no existe.", 404);
        }
        aprobacionLogic.changeStakeHolder(idAprobacion, idAprobador); 
        */
        CambioDTO dto = new CambioDTO(aprobacion);
        return dto;
    }
    
    @GET
    @Path("{id1: \\d+}/stakeholder")
    public StakeHolderDetailDTO getAutor(@PathParam("id1")Long  idCambio){
        CambioEntity entity = cambioLogic.findCambioById(idCambio);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        StakeHolderEntity sh = entity.getAutor();
        StakeHolderDetailDTO dto = new StakeHolderDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
    
    @PUT
    @Path("{id1: \\d+}/requisito/{id2: \\d+}")
    public CambioDTO changeRequisito(@PathParam("id1") Long idCambio, @PathParam("id2") Long idRequisito){
        CambioEntity aprobacion = cambioLogic.findCambioById(idCambio);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        /*RequisitoEntity requisito = requisitoLogic.find(idRequisito);
        if(requisito == null){
            throw new WebApplicationException("El recurso /requisitos/"+idRequisito+" no existe.", 404);
        }
        aprobacionLogic.changeRequisito(idApobacion, idRequisito);
        */
        CambioDTO dto = new CambioDTO(aprobacion);
        return dto;
    }
    
    @GET
    @Path("{id1: \\d+}/requisito")
    public RequisitoDetailDTO getRequisito(@PathParam("id1") Long idCambio){
        CambioEntity entity = cambioLogic.findCambioById(idCambio);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        RequisitoEntity requisito = entity.getRequisito();
        RequisitoDetailDTO dto = new RequisitoDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
    
    @PUT 
    @Path("{id1: \\d+}/objetivo/{id2: \\d+}")
    public CambioDTO changeObjetivo(@PathParam("id1") Long idCambio, @PathParam("id2") Long idObjetivo){
        CambioEntity aprobacion = cambioLogic.findCambioById(idCambio);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        ObjetivoEntity objetivo = objetivoLogic.getObjetivo(idObjetivo);
        if(objetivo == null){
            throw new WebApplicationException("El recurso /objetivos/"+idObjetivo+" no existe.", 404);
        }
        cambioLogic.changeRequisito(idCambio, idObjetivo);
        CambioDTO dto = new CambioDTO(aprobacion);
        return dto;
    }
    
    @GET
    @Path("{id1: \\d+}/objetivo")
    public ObjetivoDetailDTO getObjetivo(@PathParam("id1") Long idCambio){
        CambioEntity entity = cambioLogic.findCambioById(idCambio);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        ObjetivoEntity objetivo = entity.getObjetivo();
        ObjetivoDetailDTO dto = new ObjetivoDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
}

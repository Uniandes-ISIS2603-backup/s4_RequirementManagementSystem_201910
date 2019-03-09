/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.dtos.RequisitoDetailDTO;
import co.edu.uniandes.csw.requirement.dtos.StakeHolderDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
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
 * @author Emilio
 */
@Path("aprobaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AprobacionResource {
    
    private static final Logger LOGGER = Logger.getLogger(AprobacionResource.class.getName());
    
    @Inject
    private AprobacionLogic aprobacionLogic;
    
    @Inject
    private StakeHolderLogic stakeHolderLogic;
    
    @Inject
    private ObjetivoLogic objetivoLogic;
    
    @Inject
    private RequisitoLogic requisitoLogic;
    
    @POST
    public AprobacionDTO createAprobacion(AprobacionDTO aprobacion) throws BusinessLogicException{
        AprobacionEntity entity = aprobacion.toEntity();
        entity = aprobacionLogic.createAprobacion(entity);
        return new AprobacionDTO(entity);
    }
    
    @GET
    public List<AprobacionDTO> getAprobaciones(){
        List<AprobacionDTO> dtos = new ArrayList<AprobacionDTO>();
        List<AprobacionEntity> entities = aprobacionLogic.findAllAprobaciones();
        for(AprobacionEntity entity:entities){
            dtos.add(new AprobacionDTO(entity));
        }
        return dtos;
    }
    
    @GET
    @Path("{id: \\d+}")
    public AprobacionDTO getAprobacion(@PathParam("id") Long id){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+id+" no existe.", 404);
        }
        return new AprobacionDTO(entity);
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public AprobacionDTO deleteAprobacion(@PathParam("id") Long id){
        AprobacionEntity entity = aprobacionLogic.deleteAprobacion(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+id+" no existe.", 404);
        }
        AprobacionDTO dto = new AprobacionDTO(entity);
        return dto;
    }
    
    /*@PUT 
    @Path("{id1: \\d+}/stakeholder/{id2: \\d+}")
    public AprobacionDTO changeStakeHolder(@PathParam("id1") Long idAprobacion, @PathParam("id2") Long idAprobador){
        AprobacionEntity aprobacion = aprobacionLogic.findAprobacionById(idAprobacion);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        /*StakeHolderEntity stakeHolder = requisitoLogic.find(idRequisito);
        if(stakeholder == null){
            throw new WebApplicationException("El recurso /stakeholders/"+idAprobador+" no existe.", 404);
        }
        aprobacionLogic.changeStakeHolder(idAprobacion, idAprobador); 
        
        AprobacionDTO dto = new AprobacionDTO(aprobacion);
        return dto;
    }*/
    
    /*
    @GET
    @Path("{id1: \\d+}/stakeholder")
    public StakeHolderDetailDTO getStakeHolder(@PathParam("id1")Long  idAprobacion){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(idAprobacion);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        StakeHolderEntity sh = entity.getStakeHolder();
        StakeHolderDetailDTO dto = new StakeHolderDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
    */
    /*@PUT
    @Path("{id1: \\d+}/requisito/{id2: \\d+}")
    public AprobacionDTO changeRequisito(@PathParam("id1") Long idAprobacion, @PathParam("id2") Long idRequisito){
        AprobacionEntity aprobacion = aprobacionLogic.findAprobacionById(idAprobacion);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        RequisitoEntity requisito = requisitoLogic.find(idRequisito);
        if(requisito == null){
            throw new WebApplicationException("El recurso /requisitos/"+idRequisito+" no existe.", 404);
        }
        aprobacionLogic.changeRequisito(idApobacion, idRequisito);
        
        AprobacionDTO dto = new AprobacionDTO(aprobacion);
        return dto;
    }*/
    
    /*@GET
    @Path("{id1: \\d+}/requisito")
    public RequisitoDetailDTO getRequisito(@PathParam("id1") Long idAprobacion){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(idAprobacion);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        RequisitoEntity requisito = entity.getRequisito();
        RequisitoDetailDTO dto = new RequisitoDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }*/
    
    /*@PUT 
    @Path("{id1: \\d+}/objetivo/{id2: \\d+}")
    public AprobacionDTO changeObjetivo(@PathParam("id1") Long idAprobacion, @PathParam("id2") Long idObjetivo){
        AprobacionEntity aprobacion = aprobacionLogic.findAprobacionById(idAprobacion);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        ObjetivoEntity objetivo = objetivoLogic.getObjetivo(idObjetivo);
        if(objetivo == null){
            throw new WebApplicationException("El recurso /objetivos/"+idObjetivo+" no existe.", 404);
        }
        aprobacionLogic.changeRequisito(idAprobacion, idObjetivo);
        AprobacionDTO dto = new AprobacionDTO(aprobacion);
        return dto;
    }*/
    
    /*@GET
    @Path("{id1: \\d+}/objetivo")
    public ObjetivoDetailDTO getObjetivo(@PathParam("id1") Long idAprobacion){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(idAprobacion);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        ObjetivoEntity requisito = entity.getObjetivo();
        ObjetivoDetailDTO dto = new ObjetivoDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }*/
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos PrizeDetailDTO (json)
     *
     * @param entityList corresponde a la lista de premios de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de premios en forma DTO (json)
     */
    private List<AprobacionDTO> listEntity2DetailDTO(List<AprobacionEntity> entityList) {
        List<AprobacionDTO> list = new ArrayList<>();
        for (AprobacionEntity entity : entityList) {
            list.add(new AprobacionDTO(entity));
        }
        return list;
    }
}

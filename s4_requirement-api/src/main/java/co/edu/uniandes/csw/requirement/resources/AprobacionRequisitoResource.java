/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import co.edu.uniandes.csw.requirement.dtos.RequisitoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * Produce jsons
 */
@Produces("application/json")
/**
 * Lee jsons
 */
@Consumes("application/json")
/**
 * El request scoped.
 */
@RequestScoped
/**
 *Asociación entre los recursos de aprobación y requisito
 * @author Sofia Alvarez
 */
public class AprobacionRequisitoResource {
    
     /**
     * Inyección de la logica de la aprobacion
     */
    @Inject
    private AprobacionLogic aprobacionLogic;
    /**
     * Inyeccion de la logica de requisito.
     */
    @Inject
    private RequisitoLogic requisitoLogic;
    
   
    
    /**
     * Actualiza el requisito asociado a una aprobacion
     * @param idAprobacion id de la aprobacion
     * @param idRequisito id del requisito
     * @return una aprobacion con su requisito actualizado
     */
    @PUT
    @Path("{id1: \\d+}/requisito/{id2: \\d+}")
    public AprobacionDTO changeRequisito(@PathParam("id1") Long idAprobacion, @PathParam("id2") Long idRequisito){
        AprobacionEntity aprobacion = aprobacionLogic.findAprobacionById(idAprobacion);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        RequisitoEntity requisito = requisitoLogic.getRequisito(idRequisito);
        if(requisito == null){
            throw new WebApplicationException("El recurso /requisitos/"+idRequisito+" no existe.", 404);
        }
        aprobacionLogic.changeRequisito(idAprobacion, idRequisito);
        
        AprobacionDTO dto = new AprobacionDTO(aprobacion);
        return dto;
    }
    /**
     * Retorna el requisito asociado a una aprobacion
     * @param idAprobacion id de la aprobacion a buscar.
     * @return el requisito asociado a una aprobacion
     */
    @GET
    @Path("{id1: \\d+}/requisito")
    public RequisitoDetailDTO getRequisito(@PathParam("id1") Long idAprobacion){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(idAprobacion);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        RequisitoEntity requisito = entity.getRequisito();
        RequisitoDetailDTO dto = new RequisitoDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
    
    
}

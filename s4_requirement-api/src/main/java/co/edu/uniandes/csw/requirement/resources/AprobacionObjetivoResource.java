/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
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
 *Asociación entre los recursos de aprobación y objetivo
 * @author Sofia Alvarez
 */
public class AprobacionObjetivoResource {
   
    /**
     * Inyección de la logica de la aprobacion
     */
    @Inject
    private AprobacionLogic aprobacionLogic;
    /**
     * Inyeccion de la logica de objetivo
     */
    @Inject
    private ObjetivoLogic objetivoLogic;
    
    /**
     * Actualiza el objetivo asociado a una aprobacion
     * @param idAprobacion id de la aprobacion
     * @param idObjetivo id del objetivo
     * @return una aprobacion con su objetivo actualizado
     */
    @PUT 
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
    }
    
    /**
     * Retorna el requisito asociado a un objetivo
     * @param idAprobacion id de la aprobacion a buscar.
     * @return el objetivo asociado a una aprobacion
     */
    @GET
    @Path("{id1: \\d+}/objetivo")
    public ObjetivoDetailDTO getObjetivo(@PathParam("id1") Long idAprobacion){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(idAprobacion);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        ObjetivoEntity objetivo = entity.getObjetivo();
        ObjetivoDetailDTO dto = new ObjetivoDetailDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
    
}

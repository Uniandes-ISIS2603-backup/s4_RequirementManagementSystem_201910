/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import co.edu.uniandes.csw.requirement.dtos.StakeHolderDTO;
import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
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
 *Asociación entre los recursos de aprobación y stakeholder
 * @author Sofia Alvarez
 */
public class AprobacionStakeholderResource {
    /**
     * Inyección de la logica de la aprobacion
     */
    @Inject
    private AprobacionLogic aprobacionLogic;
    /**
     * Inyeccion de la logica de stakeholder
     */
    @Inject
    private StakeHolderLogic stakeHolderLogic;
    /**
     * Inyeccion de la logica de objetivo
     */
    @Inject
    private ObjetivoLogic objetivoLogic;
    /**
     * Inyeccion de la logica de requisito.
     */
    @Inject
    private RequisitoLogic requisitoLogic;
    
    /**
     * Actualiza una aprobacion un stakeholder
     * @param idAprobacion id de la aprobacion
     * @param idAprobador id del stakeholder
     * @return una aprobacion actualizada
     */
    @PUT 
    @Path("{id1: \\d+}/stakeholder/{id2: \\d+}")
    public AprobacionDTO changeStakeHolder(@PathParam("id1") Long idAprobacion, @PathParam("id2") Long idAprobador){
        AprobacionEntity aprobacion = aprobacionLogic.findAprobacionById(idAprobacion);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        StakeHolderEntity stakeHolder = stakeHolderLogic.getStakeHolder(idAprobador);
        if(stakeHolder == null){
            throw new WebApplicationException("El recurso /stakeholders/"+idAprobador+" no existe.", 404);
        }
        aprobacionLogic.changeStakeHolder(idAprobacion, idAprobador); 
        
        AprobacionDTO dto = new AprobacionDTO(aprobacion);
        return dto;
    }
    
    /**
     * Obtiene los stakeholders de una aprobacion
     * @param idAprobacion id de la aprobacion a obtener.
     * @return los stakeholders de una aprobacion
     */
    @GET
    @Path("{id1: \\d+}/stakeholder")
    public StakeHolderDTO getStakeHolder(@PathParam("id1")Long  idAprobacion){
        AprobacionEntity entity = aprobacionLogic.findAprobacionById(idAprobacion);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idAprobacion+" no existe.", 404);
        }
        StakeHolderEntity sh = entity.getStakeHolder();
        StakeHolderDTO dto = new StakeHolderDTO(); //Falta constructor con entity como parámetro.
        return dto;
    }
    
}

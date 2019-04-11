/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CambioDTO;
import co.edu.uniandes.csw.requirement.dtos.StakeHolderDTO;
import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
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
 * Produce un json
 */
@Produces("application/json")
/**
 * Consume un json
 */
@Consumes("application/json")
/**
 * EL request scoped.
 */
@RequestScoped

/**
 *Asociación entre recursos de cambio y stakeholder
 * @author Sofia Alvarez
 */
public class CambioStakeholderResource {
    
    /**
    * Inyeccion de las dependencias de cambios
    */
    @Inject
    private CambioLogic cambioLogic;
    
    /**
     * Inyeccion de las dependencias de stakeholders
     */
    @Inject
    private StakeHolderLogic stakeHolderLogic;

    /**
     * Actualiza el stakeholder de un cambio
     * @param idCambio id del cambio
     * @param idAprobador id del aprobador
     * @return el cambio actualizado
     */
  @PUT 
    @Path("{id1: \\d+}/stakeholder/{id2: \\d+}")
    public CambioDTO changeStakeHolder(@PathParam("id1") Long idCambio, @PathParam("id2") Long idAprobador){
        CambioEntity aprobacion = cambioLogic.findCambioById(idCambio);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        StakeHolderEntity stakeHolder = stakeHolderLogic.getStakeHolder(idAprobador);
        if(stakeHolder == null){
            throw new WebApplicationException("El recurso /stakeholders/"+idAprobador+" no existe.", 404);
        }
        cambioLogic.changeStakeHolder(idCambio, idAprobador); 
        CambioDTO dto = new CambioDTO(aprobacion);
        return dto;
    }
    
    /**
     * Retorna los stakeholders asociados a un cambio
     * @param idCambio id del cambio
     * @return stakeholder de un cambio.
     */
    @GET
    @Path("{id1: \\d+}/stakeholder")
    public StakeHolderDTO getAutor(@PathParam("id1")Long  idCambio){
        CambioEntity entity = cambioLogic.findCambioById(idCambio);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        StakeHolderEntity sh = entity.getStakeholder();
        StakeHolderDTO dto = new StakeHolderDTO(sh); 
        return dto;
    }
    
     
    
}

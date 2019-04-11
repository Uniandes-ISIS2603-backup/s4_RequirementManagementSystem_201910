/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CambioDTO;
import co.edu.uniandes.csw.requirement.dtos.RequisitoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
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
 *Asociación entre recursos de cambio y requisito
 * @author Sofia Alvarez
 */
public class CambioRequisitoResource {
    /**
    * Inyeccion de las dependencias de cambios
    */
    @Inject
    private CambioLogic cambioLogic;
    
    
    /**
     * Inyeccion de las dependencias de requisitos
     */
    @Inject
    private RequisitoLogic requisitoLogic;
    
    
     /**
     * Actualiza el requisito de un cambio
     * @param idCambio id del cambio
     * @param idRequisito id del requisito
     * @return el cambio actualizado
     */
    @PUT
    @Path("{id1: \\d+}/requisito/{id2: \\d+}")
    public CambioDTO changeRequisito(@PathParam("id1") Long idCambio, @PathParam("id2") Long idRequisito){
        CambioEntity aprobacion = cambioLogic.findCambioById(idCambio);
        if(aprobacion == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        RequisitoEntity requisito = requisitoLogic.getRequisito(idRequisito);
        if(requisito == null){
            throw new WebApplicationException("El recurso /requisitos/"+idRequisito+" no existe.", 404);
        }
        cambioLogic.changeRequisito(idCambio, idRequisito);
        CambioDTO dto = new CambioDTO(aprobacion);
        return dto;
    }
    
     /**
     * Retorna los requisitos asociados a un cambio
     * @param idCambio id del cambio
     * @return requisito de un cambio.
     */
    @GET
    @Path("{id1: \\d+}/requisito")
    public RequisitoDetailDTO getRequisito(@PathParam("id1") Long idCambio){
        CambioEntity entity = cambioLogic.findCambioById(idCambio);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        RequisitoEntity requisito = entity.getRequisito();
        RequisitoDetailDTO dto = new RequisitoDetailDTO(requisito); 
        return dto;
    }
    

}

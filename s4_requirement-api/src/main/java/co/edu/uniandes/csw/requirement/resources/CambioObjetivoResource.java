/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CambioDTO;
import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
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
 *Asociación entre recursos de cambio y objetivo
 * @author Sofia Alvarez
 */
public class CambioObjetivoResource {
     /**
    * Inyeccion de las dependencias de cambios
    */
    @Inject
    private CambioLogic cambioLogic;
    
    
    /**
     * Inyeccion de las dependencias de objetivos
     */
    @Inject
    private ObjetivoLogic objetivoLogic;
   
    
   
     /**
     * Actualiza el objetivo de un cambio
     * @param idCambio id del cambio
     * @param idObjetivo id del objetivo
     * @return el cambio actualizado
     */
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
    
     /**
     * Retorna los objetivos asociados a un cambio
     * @param idCambio id del cambio
     * @return objetivo de un cambio.
     */
    @GET
    @Path("{id1: \\d+}/objetivo")
    public ObjetivoDetailDTO getObjetivo(@PathParam("id1") Long idCambio){
        CambioEntity entity = cambioLogic.findCambioById(idCambio);
        if(entity == null){
            throw new WebApplicationException("El recurso /aprobaciones/"+idCambio+" no existe.", 404);
        }
        ObjetivoEntity objetivo = entity.getObjetivo();
        ObjetivoDetailDTO dto = new ObjetivoDetailDTO(objetivo);
        return dto;
    }
}

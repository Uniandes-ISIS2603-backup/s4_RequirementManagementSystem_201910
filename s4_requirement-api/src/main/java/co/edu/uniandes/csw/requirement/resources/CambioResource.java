/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CambioDTO;
import co.edu.uniandes.csw.requirement.ejb.CambioLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
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

/**
 * Ruta de los cambios
 */
@Path("cambios")
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
 *Recurso de un cambio
 * @author Sofia Alvarez
 */
public class CambioResource{
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(CambioResource.class.getName());
    
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
     * Inyeccion de las dependencias de objetivos
     */
    @Inject
    private ObjetivoLogic objetivoLogic;
    
    /**
     * Inyeccion de las dependencias de requisitos
     */
    @Inject
    private RequisitoLogic requisitoLogic;
    
    /**
     * Crea un nuevo cambio. 
     * @param cambio a crear
     * @return cambio creado
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @POST
    public CambioDTO createCambio(CambioDTO cambio) throws BusinessLogicException{
         CambioDTO cambioDTO = new CambioDTO(cambioLogic.createCambio(cambio.toEntity()));
         return cambioDTO;
    }
    /**
     * Retorna todos los cambios
     * @return todos los cambios
     */
    @GET
    public List<CambioDTO> getCambios(){
        List<CambioDTO> dtos = new ArrayList<>();
        List<CambioEntity> entities = cambioLogic.findAllCambios();
        for(CambioEntity entity:entities){
            dtos.add(new CambioDTO(entity));
        }
        return dtos;
    }
    
    /**
     * Retorna un cambio con un id especifico
     * @param id del cambio a buscar
     * @return el cambio buscado
     */
    @GET
    @Path("{id: \\d+}")
    public CambioDTO getCambio(@PathParam("id") Long id){
        CambioEntity entity = cambioLogic.findCambioById(id);
        if(entity == null){
            throw new WebApplicationException("El recurso /cambios/"+id+" no existe.", 404);
        }
        return new CambioDTO(entity);
    }
    
    /**
     * Elimina un cambio
     * @param id del cambio a eliminar
     * @return cambio eliminado
     */
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
    
     /**
     * Actualiza un cambio
     * @param cambioId id del cambio
     * @param cambio a actualizar
     * @return cambio actualizada 
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @PUT
    @Path("{id: \\d+}")
    public CambioDTO updateAprobacion(@PathParam("id") Long cambioId, CambioDTO cambio) throws BusinessLogicException{
        cambio.setId(cambioId);
        if (cambioLogic.findCambioById(cambioId) == null) {
            throw new WebApplicationException("El recurso /cambios/" + cambioId + " no existe.", 404);
        }
        CambioDTO cambDTO = new CambioDTO(cambioLogic.updateCambio(cambio.toEntity()));
        return cambDTO;
    }
    
}

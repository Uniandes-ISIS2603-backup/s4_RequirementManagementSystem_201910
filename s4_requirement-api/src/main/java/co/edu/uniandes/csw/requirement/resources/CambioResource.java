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
    
    @POST 
    @Path("{id1: \\d+}/autor/{id2: \\d+}")
    public void changeAutor(@PathParam("id1") Long idCambio, @PathParam("id2") Long idAutor){
        
    }
    
    //Falta cambiar retorno y retornar el autor
    @GET
    @Path("{id1: \\d+}/autor")
    public void getAutor(@PathParam("id1") Long idCambio){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/requisito/{id2: \\d+}")
    public void changeRquisito(@PathParam("id1") Long idCambio, @PathParam("id2") Long idRequisito){
        
    }
    
    //Falta cambiar retorno y retornar el requisito
    @GET
    @Path("{id1: \\d+}/requisito")
    public void getRequisito(@PathParam("id1") Long idCambio){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/objetivo/{id2: \\d+}")
    public void changeObjetivo(@PathParam("id1") Long idCambio, @PathParam("id2") Long idObjetivo){
        
    }
    
    //Falta cambiar retorno y retornar el requisito
    @GET
    @Path("{id1: \\d+}/objetivo")
    public void getObjetivo(@PathParam("id1") Long idCambio){
        
    }
}

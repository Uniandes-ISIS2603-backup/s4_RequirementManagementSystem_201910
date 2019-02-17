/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author estudiante
 */
@Path("aprobaciones")
@Produces("application/JSON")
@Consumes("application/JSON")
@RequestScoped
public class AprobacionResource {
    
    private static final Logger LOGGER = Logger.getLogger(AprobacionResource.class.getName());
    
    @POST
    public AprobacionDTO createAprobacion(AprobacionDTO cambio){
        return cambio;
    }
    
    @GET
    @Path("{id: \\d+}")
    public AprobacionDTO getAprobacion(@PathParam("id") int id){
        return new AprobacionDTO();
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteAprobacion(@PathParam("id") int id){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/aprobador/{id2: \\d+}")
    public void changeAprobador(@PathParam("id1") int idCambio, @PathParam("id2") int idAprobador){
        
    }
    
    //Falta cambiar retorno y retornar el autor
    @GET
    @Path("{id1: \\d+}/aprobador")
    public void getAprobador(@PathParam("id1") int idAprobador){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/reuisito/{id2: \\d+}")
    public void changeRquisito(@PathParam("id1") int idAprobacion, @PathParam("id2") int idRequisito){
        
    }
    
    //Falta cambiar retorno y retornar el requisito
    @GET
    @Path("{id1: \\d+}/requisito")
    public void getRequisito(@PathParam("id1") int idAprobacion){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/objetivo/{id2: \\d+}")
    public void changeObjetivo(@PathParam("id1") int idAprobacion, @PathParam("id2") int idObjetivo){
        
    }
    
    //Falta cambiar retorno y retornar el requisito
    @GET
    @Path("{id1: \\d+}/objetivo")
    public void getObjetivo(@PathParam("id1") int idAprobacion){
        
    }
}

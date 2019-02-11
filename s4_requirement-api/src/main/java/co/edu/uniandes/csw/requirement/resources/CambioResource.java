/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CambioDTO;
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
@Path("cambios")
@Produces("application/JSON")
@Consumes("application/JSON")
@RequestScoped
public class CambioResource{
    
    @POST
    public CambioDTO createCambio(CambioDTO cambio){
        return cambio;
    }
    
    @GET
    @Path("{id: \\d+}")
    public CambioDTO getCambio(@PathParam("id") int id){
        return new CambioDTO();
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public CambioDTO deleteCambio(@PathParam("id") int id){
        return new CambioDTO();
    }
    
    @POST 
    @Path("{id1: \\d+}/autor/{id2: \\d+}")
    public void changeAutor(@PathParam("id1") int idCambio, @PathParam("id2") int idAutor){
        
    }
    
    //Falta cambiar retorno y retornar el autor
    @GET
    @Path("{id1: \\d+}/autor")
    public void getAutor(@PathParam("id1") int idCambio){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/rquisito/{id2: \\d+}")
    public void changeRquisito(@PathParam("id1") int idCambio, @PathParam("id2") int idRequisito){
        
    }
    
    //Falta cambiar retorno y retornar el requisito
    @GET
    @Path("{id1: \\d+}/requisito")
    public void getRequisito(@PathParam("id1") int idCambio){
        
    }
    
    @POST 
    @Path("{id1: \\d+}/objetivo/{id2: \\d+}")
    public void changeObjetivo(@PathParam("id1") int idCambio, @PathParam("id2") int idObjetivo){
        
    }
    
    //Falta cambiar retorno y retornar el requisito
    @GET
    @Path("{id1: \\d+}/objetivo")
    public void getObjetivo(@PathParam("id1") int idCambio){
        
    }
}

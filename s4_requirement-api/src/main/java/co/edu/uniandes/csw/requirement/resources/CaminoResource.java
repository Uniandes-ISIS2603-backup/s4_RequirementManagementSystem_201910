/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CaminoDTO;
import co.edu.uniandes.csw.requirement.ejb.CaminoLogic;
import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
 *
 * @author Sofia Alvarez
 */
@Path("camino")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CaminoResource 
{
    @Inject
    private CaminoLogic caminoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
     private static final Logger LOGGER = Logger.getLogger(CaminoResource.class.getName());

     /**
      * Crea un nuevo camino con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
      * @param camino {@link CaminoDTO} - El camino que se desea
     * guardar.
      * @return JSON {@link CaminoDTO} - El camino guardado con el atributo
     * id autogenerado.
      */
     @POST
    public CaminoDTO createCamino(CaminoDTO camino) throws BusinessLogicException{

         CaminoEntity caminoEntity = camino.toEntity();
        // Invoca la lógica para crear un camino nuevo
        CaminoEntity nuevoCaminoEntity = caminoLogic.createCamino(caminoEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        CaminoDTO nuevoCaminoDTO = new CaminoDTO(nuevoCaminoEntity);
        return nuevoCaminoDTO;
    }
   
      /**
     * Busca el camino con el id asociado recibido en la URL y la devuelve.
     *
     * @param id Identificador del camino que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link CaminoDTO} - El camino buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{id: \\d+}")
    public CaminoDTO getCamino (@PathParam("id") Long id) throws WebApplicationException {
        CaminoEntity caminoEntity = caminoLogic.getCamino(id);
        if (caminoEntity == null) {
            throw new WebApplicationException("El recurso /camino/" + id + " no existe.", 404);
        }
        CaminoDTO caminoDTO = new CaminoDTO(caminoEntity);
        return caminoDTO;
    }
    
    @GET
    public List<CaminoDTO> getCaminos() {
        LOGGER.info("CaminoResource getCaminos: input: void");
        List<CaminoDTO> listaCaminos = listEntity2DTO(caminoLogic.getCaminos());
        LOGGER.log(Level.INFO, "CaminoResource getCaminos: output: {0}", listaCaminos);
        return listaCaminos;
    }
    /**
     * Borra el camino con el id asociado recibido en la URL
     * @param id Identificador del camino que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el camino.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el camino.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCamino (@PathParam("id") Long id) throws BusinessLogicException {
        if (caminoLogic.getCamino(id) == null) {
            throw new WebApplicationException("El recurso /camino/" + id + " no existe.", 404);
        }
        caminoLogic.deleteCamino(id);
    }

    /**
     * Actualiza el camino con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     * @param id  Identificador de el camino que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param camino el camino que se desea guardar 
     * @return JSON {@link CaminoDTO} - El camino guardado.
      * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el camino a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public CaminoDTO updateCamino(@PathParam("id") Long id, CaminoDTO camino) throws WebApplicationException {
        camino.setIdPaso(id);
        if (caminoLogic.getCamino(id) == null) {
            throw new WebApplicationException("El recurso /camino/" + id + " no existe.", 404);
        }
        CaminoDTO caminoDTO = new CaminoDTO(caminoLogic.updateCamino(id, camino.toEntity()));
        return caminoDTO;
    }
    
    private List<CaminoDTO> listEntity2DTO(List<CaminoEntity> entityList) {
        List<CaminoDTO> list = new ArrayList<>();
        for (CaminoEntity entity : entityList) {
            list.add(new CaminoDTO(entity));
        }
        return list;
    }

}
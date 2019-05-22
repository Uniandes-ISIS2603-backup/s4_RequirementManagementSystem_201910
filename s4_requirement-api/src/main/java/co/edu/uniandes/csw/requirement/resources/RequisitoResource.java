/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.RequisitoDTO;
import co.edu.uniandes.csw.requirement.dtos.RequisitoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.*;


@Produces("application/json")
@Consumes("application/json")
public class RequisitoResource
{
    private static final Logger LOGGER = Logger.getLogger(RequisitoResource.class.getName());
    
    @Inject
    private RequisitoLogic reqLogic;
    
    /**
     * Crea un nuevo requisito con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param req {@link RequisitoDTO} - EL requisito que se desea guardar.
     * @return JSON {@link RequisitoDTO} - El requisito guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando las reglas de negocio no se cumplen.
     * 
     */
    @POST
    public RequisitoDTO createRequisito(@PathParam ("proyectosId") Long proyectosId, @PathParam ("objetivosId") Long objetivosId,RequisitoDTO req) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "RequisitoResource createRequisito: input: {0}", req);
        RequisitoDetailDTO reqDTO = new RequisitoDetailDTO(reqLogic.createRequisito(proyectosId, objetivosId, req.toEntity()));
        LOGGER.log(Level.INFO, "RequisitoResource createRequisito: output: {0}", reqDTO);
        return reqDTO;
    }
    
    /**
     * Busca el requisito con el id asociado recibido en la URL y lo devuelve.
     *
     * @param requisitosId Identificador del requisito que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link RequisitoDetailDTO} - El requisito buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @GET
    @Path("{requisitosId: \\d+}")
    public RequisitoDetailDTO getRequisito(@PathParam ("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId)
    {
        LOGGER.log(Level.INFO, "RequisitoResource getRequisito: input: {0}", requisitosId);
        RequisitoEntity requisitoEntity = reqLogic.getRequisito(objetivosId, requisitosId);
        if (requisitoEntity == null) {
            throw new WebApplicationException("El recurso /objetivos/"+ objetivosId + "/requisitos/" + requisitosId + " no existe.", 404);
        }
        RequisitoDetailDTO requisitoDetailDTO = new RequisitoDetailDTO(requisitoEntity);
        LOGGER.log(Level.INFO, "RequisitoResource getRequisito: output: {0}", requisitoDetailDTO);
        return requisitoDetailDTO;
    }
    
    /**
     * Busca y devuelve todos los requisitos que existen en la aplicacion.
     *
     * @return JSONArray {@link RequisitoDetailDTO} - Los requisitos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<RequisitoDetailDTO> getRequisitos(@PathParam ("proyectosId") Long proyectosId, @PathParam ("objetivosId") Long objetivosId)
    {
        LOGGER.info("RequisitoResource getRequisitos: input: void");
        List<RequisitoDetailDTO> listaReqs = listEntity2DetailDTO(reqLogic.getRequisitos(proyectosId, objetivosId));
        LOGGER.log(Level.INFO, "RequisitoResource getRequisitos: output: {0}", listaReqs);
        return listaReqs;
    }
    
    
    /**
     * Borra el requisito con el id asociado recibido en la URL.
     *
     * @param requisitosId Identificador del requisito que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     * cuando el requisito tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @DELETE
    @Path("{requisitosId: \\d+}")
    public void deleteRequisito( @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "RequisitoResource deleteRequisito: input: {0}", requisitosId);
        RequisitoEntity entity = reqLogic.getRequisito(objetivosId, requisitosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/requisitos/" + requisitosId + " no existe.", 404);
        }
        reqLogic.deleteRequisito(objetivosId, requisitosId);
        LOGGER.info("RequisitoResource deleteRequisito: output: void");
    }
    
    /**
     * Actualiza el requisito con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param id Identificador del requisito que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param req {@link RequisitoDTO} El requisito que se desea guardar.
     * @return JSON {@link RequisitoDetailDTO} - El requisito guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el requisito.
     */
    @PUT
    @Path("{requisitosId: \\d+}")
    public RequisitoDetailDTO putRequisito(@PathParam ("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, RequisitoDetailDTO req) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "RequisitoResource updateRequisito: input: objetivo: {0},  requisitoId: {1} , requisito: {2}", new Object[]{objetivosId, requisitosId, req});
        RequisitoEntity entity = reqLogic.getRequisito(objetivosId, requisitosId);
        
        req.setId(requisitosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/requisitos/" + requisitosId + " no existe.", 404);
        }
        RequisitoDetailDTO current = new RequisitoDetailDTO(entity);
        req.setAprobaciones(current.getAprobaciones());
        req.setAutor(current.getAutor());
        req.setCambios(current.getCambios());
        req.setFuentes(current.getFuentes());
        if (req.getTipo().equals("") || req.getTipo() == null)
        {
            req.setTipo(current.getTipo());
        }
        if (req.getComentarios().equals("") || req.getComentarios() == null)
        {
            req.setComentarios(current.getComentarios());
        }
        if (req.getDescripcion().equals("") || req.getDescripcion() == null)
        {
            req.setDescripcion(current.getDescripcion());
        }
        if (req.getEstabilidad() == null)
        {
            req.setEstabilidad(current.getEstabilidad());
        }
        if (req.getImportancia() == null)
        {
            req.setImportancia(current.getImportancia());
        }
        
        RequisitoDetailDTO detailDTO = new RequisitoDetailDTO(reqLogic.updateRequisito(proyectosId, objetivosId, requisitosId, req.toEntity()));
        LOGGER.log(Level.INFO, "RequisitoResource updateRequisito: output: {0}", detailDTO);
        return detailDTO;
    }
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos RequisitoEntity a una lista de
     * objetos RequisitoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de requisitos de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de requisitos en forma DTO (json)
     */
    private List<RequisitoDetailDTO> listEntity2DetailDTO(List<RequisitoEntity> entityList)
    {
        List<RequisitoDetailDTO> list = new ArrayList<>();
        for (RequisitoEntity entity : entityList)
        {
            list.add(new RequisitoDetailDTO(entity));
        }
        return list;
    }
    
    @Path("{requisitosId: \\d+}/casosDeUso")
    public Class<CasoDeUsoResource> getCasodeResource(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        if (reqLogic.getRequisito(objetivosId, requisitosId) == null) {
            throw new WebApplicationException("El recurso /requisitos/" + requisitosId + " no existe.", 404);
        }
        return CasoDeUsoResource.class;
    }
    
    @Path("{requisitosId: \\d+}/cambios")
    public Class<CambioResource> getCambioResource(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        if (reqLogic.getRequisito(objetivosId, requisitosId) == null) {
            throw new WebApplicationException("El recurso /requisitos/" + requisitosId + " no existe.", 404);
        }
        return CambioResource.class;
    }
    
    
    
}

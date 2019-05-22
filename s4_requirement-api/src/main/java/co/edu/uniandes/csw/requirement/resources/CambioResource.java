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
 * Recurso de un cambio
 *
 * @author Sofia Alvarez
 */
public class CambioResource {

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
     *
     * @param cambio a crear
     * @return cambio creado
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @POST
    public CambioDTO createCambio(CambioDTO cambio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CambioResource createCambio: input: {0}", cambio);
        CambioDTO cambioDTO = new CambioDTO(cambioLogic.createCambio(cambio.toEntity()));
        LOGGER.log(Level.INFO, "CambioResource createCambio: output: {0}", cambioDTO);
        return cambioDTO;
    }


    /**
     * Retorna un cambio con un id especifico
     *
     * @param id del cambio a buscar
     * @return el cambio buscado
     */
    @GET
    @Path("{id: \\d+}")
    public CambioDTO getCambioObj(@PathParam ("objetivosId") Long objetivosId, @PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "CambioResource getCambio: input: {0}", objetivosId);
        CambioEntity entity = cambioLogic.findCambioByIdObjetivo(id, objetivosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso objetivos/" + objetivosId + "cambios/" + id + " no existe.", 404);
        }
        return new CambioDTO(entity);
    }
    
    @GET
    @Path("{id: \\d+}")
    public CambioDTO getCambioReq(@PathParam ("requisitosId") Long requisitosId, @PathParam("id") Long id) {
        LOGGER.log(Level.INFO, "CambioResource getCambio: input: {0}", requisitosId);
        CambioEntity entity = cambioLogic.findCambioByIdRequisito(id, requisitosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso requisito/" + requisitosId + "cambios/" + id + " no existe.", 404);
        }
        CambioDTO cDTO = new CambioDTO(entity);
        LOGGER.log(Level.INFO, "CambioResource getCambio: output: {0}", cDTO);
        
        return cDTO;
    }
    
    @GET
    public List<CambioDTO> getCambiosOfObj(@PathParam ("proyectosId") Long proyectosId, @PathParam ("objetivosId") Long objetivosId)
    {
        LOGGER.info("RequisitoResource getRequisitos: input: void");
        List<CambioDTO> listaReqs = listEntity2DetailDTO(cambioLogic.getCambiosOfObj(proyectosId, objetivosId));
        LOGGER.log(Level.INFO, "RequisitoResource getRequisitos: output: {0}", listaReqs);
        return listaReqs;
    }
    
    @GET
    public List<CambioDTO> getCambiosOfReq(@PathParam ("objetivosId") Long objetivosId, @PathParam ("requisitosId") Long requisitosId)
    {
        LOGGER.info("RequisitoResource getRequisitos: input: void");
        List<CambioDTO> listaReqs = listEntity2DetailDTO(cambioLogic.getCambiosOfReq(objetivosId, requisitosId));
        LOGGER.log(Level.INFO, "RequisitoResource getRequisitos: output: {0}", listaReqs);
        return listaReqs;
    }

    /**
     * Borra el cambio con el id asociado recibido en la URL.
     *
     * @param requisitosId Identificador del requisito que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     * cuando el requisito tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @DELETE
    @Path("{cambiosId: \\d+}")
    public void deleteCambioOfReq(@PathParam("objetivosId") Long objetivosId,  @PathParam("requisitosId") Long requisitosId, @PathParam("cambiosId") Long cambiosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CambioResource deleteCambio: input: {0}", cambiosId);
        CambioEntity entity = cambioLogic.findCambioByIdRequisito(cambiosId, requisitosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /requisitos/" + requisitosId + "/cambios/" + cambiosId + " no existe.", 404);
        }
        cambioLogic.deleteCambio(cambiosId);
        LOGGER.info("CambioResource deleteCambio: output: void");
    }
    
    /**
     * Borra el cambio con el id asociado recibido en la URL.
     *
     * @param requisitosId Identificador del requisito que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     * cuando el requisito tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @DELETE
    @Path("{cambiosId: \\d+}")
    public void deleteCambioOfObj(@PathParam("objetivosId") Long objetivosId, @PathParam("cambiosId") Long cambiosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "CambioResource deleteCambio: input: {0}", cambiosId);
        CambioEntity entity = cambioLogic.findCambioByIdObjetivo(cambiosId, objetivosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/cambios/" + cambiosId + " no existe.", 404);
        }
        cambioLogic.deleteCambio(cambiosId);
        LOGGER.info("CambioResource deleteCambio: output: void");
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
    @Path("{cambiosId: \\d+}")
    public CambioDTO putCambio(@PathParam ("objetivosId") Long objetivosId, @PathParam("cambiosId") Long cambiosId,  CambioDTO dto) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "RequisitoResource updateRequisito: input: objetivo: {0},  requisitoId: {1} , requisito: {2}", new Object[]{objetivosId, dto});
        CambioEntity entity = cambioLogic.findCambioByIdObjetivo(cambiosId, objetivosId);
        
        
        if (entity == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/cambios/" + cambiosId + " no existe.", 404);
        }
        dto.setId(cambiosId);
        CambioDTO current = new CambioDTO(entity);
        if (dto.getDescripcion() == null || dto.getDescripcion().equals(""))
        {
            dto.setDescripcion(current.getDescripcion());
        }
        if (current.getObjetivo() != null)
        {
            dto.setObjetivo(current.getObjetivo());
        }
        else
        {
            dto.setRequisito(current.getRequisito());
        }
        if (dto.getTipo() == null || dto.getTipo().equals(""))
        {
            dto.setTipo(current.getTipo());
        }
        if (dto.getStakeholder() == null)
        {
            dto.setStakeholder(current.getStakeholder());
        }
        
        
        
            
        
        CambioDTO detailDTO = new CambioDTO(cambioLogic.updateCambio(cambiosId, objetivosId, dto.toEntity()));
        LOGGER.log(Level.INFO, "CambioResource updateCambio: output: {0}", detailDTO);
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
    private List<CambioDTO> listEntity2DetailDTO(List<CambioEntity> entityList)
    {
        List<CambioDTO> list = new ArrayList<>();
        for (CambioEntity entity : entityList)
        {
            list.add(new CambioDTO(entity));
        }
        return list;
    }
}

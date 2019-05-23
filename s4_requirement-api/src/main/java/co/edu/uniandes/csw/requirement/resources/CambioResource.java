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
 * Produce un json
 */
@Produces("application/json")
/**
 * Consume un json
 */
@Consumes("application/json")

/**
 * Recurso de un cambio
 *
 * @author Sofia Alvarez, Jorge Esguerra, David Manosalva
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
     * Crea un nuevo cambio.
     *
     * @param cambio a crear
     * @return cambio creado
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @POST
    public CambioDTO createCambio(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, CambioDTO cambio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CambioResource createCambio: input: {0}", cambio);
        CambioDTO cambioDTO;
        if (requisitosId == null) {
            cambioDTO = new CambioDTO(cambioLogic.createCambioObjetivo(proyectosId, objetivosId, cambio.toEntity()));
        } else {
            cambioDTO = new CambioDTO(cambioLogic.createCambioRequisito(objetivosId, requisitosId, cambio.toEntity()));
        }

        LOGGER.log(Level.INFO, "CambioResource createCambio: output: {0}", cambioDTO);
        return cambioDTO;
    }

    /**
     * Consigue un cambio dado un id
     *
     * @param id el id de cambio a obtener
     * @return el cambio que se quiere.
     */
    @GET
    @Path("{cambiosId: \\d+}")
    public CambioDTO getCambio(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, @PathParam("cambiosId") Long cambiosId) {
        LOGGER.log(Level.INFO, "CambioResource getcambio: input: {0}", cambiosId);
        CambioEntity cambioEntity;
        if (requisitosId != null) {
            cambioEntity = cambioLogic.findCambioByIdRequisito(cambiosId, requisitosId);
            if (cambioEntity == null) {
                throw new WebApplicationException("El recurso /requisitos/" + requisitosId + "/cambios/" + cambiosId + " no existe.", 404);
            }
        } else {
            cambioEntity = cambioLogic.findCambioByIdObjetivo(cambiosId, objetivosId);
            if (cambioEntity == null) {
                throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/cambios/" + cambiosId + " no existe.", 404);
            }
        }
        CambioDTO cambioDTO = new CambioDTO(cambioEntity);
        LOGGER.log(Level.INFO, "CambioResource getCambio: output: {0}", cambioDTO);
        return cambioDTO;
    }

    @GET
    public List<CambioDTO> getCambios(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        LOGGER.info("CambioResource getCambios: input: void");
        List<CambioDTO> listaCambios;
        if (requisitosId != null) {
            listaCambios = listEntity2DetailDTO(cambioLogic.getCambiosOfReq(objetivosId, requisitosId));
        } else {
            listaCambios = listEntity2DetailDTO(cambioLogic.getCambiosOfObj(proyectosId, objetivosId));
        }
        LOGGER.log(Level.INFO, "CambioResource getCambios: output: {0}", listaCambios);
        return listaCambios;
    }

    /**
     * Borra el cambio con el id asociado recibido en la URL.
     *
     * @param requisitosId Identificador del requisito que se desea borrar. Este
     * debe ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     * cuando el requisito tiene autores asociados.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @DELETE
    @Path("{cambiosId: \\d+}")
    public void deleteCambio(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, @PathParam("cambiosId") Long cambiosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CambioResource deleteCambio: input: {0}", cambiosId);
        CambioEntity entity;
        if (requisitosId != null) {
            entity = cambioLogic.findCambioByIdRequisito(cambiosId, requisitosId);
            if (entity == null) {
                throw new WebApplicationException("El recurso /requisitos/" + requisitosId + "/cambios/" + cambiosId + " no existe.", 404);
            }
            cambioLogic.deleteCambioRequisito(requisitosId, cambiosId);
        } else {
            entity = cambioLogic.findCambioByIdObjetivo(cambiosId, objetivosId);
            if (entity == null) {
                throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/cambios/" + cambiosId + " no existe.", 404);
            }
            cambioLogic.deleteCambioObjetivo(objetivosId, cambiosId);
        }

        LOGGER.info("CambioResource deleteCambio: output: void");
    }

    /**
     * Actualiza el requisito con el id recibido en la URL con la información
     * que se recibe en el cuerpo de la petición.
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
    public CambioDTO putCambio(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("cambiosId") Long cambiosId, CambioDTO dto, @PathParam("requisitosId") Long requisitosId ) throws BusinessLogicException {
        boolean objetivo = false;
        CambioEntity entity;
        if (requisitosId == null)
        {
            objetivo = true;
            entity = cambioLogic.findCambioByIdObjetivo(cambiosId, objetivosId);
        }
        else
        {
            entity = cambioLogic.findCambioByIdRequisito(cambiosId, requisitosId);
        }

        if (entity == null) {
            if (objetivo)
            {
                throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/cambios/" + cambiosId + " no existe.", 404);
            }
            else
            {
                throw new WebApplicationException("El recurso /requisitos/" + requisitosId + "/cambios/" + cambiosId + " no existe.", 404);
            }   
        }
        
        dto.setId(cambiosId);
        CambioDTO current = new CambioDTO(entity);
        if (dto.getDescripcion() == null || dto.getDescripcion().equals("")) {
            dto.setDescripcion(current.getDescripcion());
        }
        if (current.getObjetivo() != null) {
            dto.setObjetivo(current.getObjetivo());
        } else {
            dto.setRequisito(current.getRequisito());
        }
        if (dto.getTipo() == null || dto.getTipo().equals("")) {
            dto.setTipo(current.getTipo());
        }
        if (dto.getAutor() == null || dto.getAutor().equals("")) {
            dto.setAutor(current.getAutor());
        }
        CambioDTO cambioDTO;
        if (objetivo)
        {
            cambioDTO = new CambioDTO(cambioLogic.updateCambioObjetivo(proyectosId, objetivosId, dto.toEntity()));
        }
        else
        {
             cambioDTO = new CambioDTO(cambioLogic.updateCambioRequisito(objetivosId, requisitosId, dto.toEntity()));
        }
        LOGGER.log(Level.INFO, "CambioResource updateCambio: output: {0}", cambioDTO);
        return cambioDTO;
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
    private List<CambioDTO> listEntity2DetailDTO(List<CambioEntity> entityList) {
        List<CambioDTO> list = new ArrayList<>();
        for (CambioEntity entity : entityList) {
            list.add(new CambioDTO(entity));
        }
        return list;
    }
}

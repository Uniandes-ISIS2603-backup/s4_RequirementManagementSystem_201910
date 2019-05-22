/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.AprobacionDTO;
import co.edu.uniandes.csw.requirement.ejb.AprobacionLogic;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
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
 * Produce jsons
 */
@Produces("application/json")
/**
 * Lee jsons
 */
@Consumes("application/json")

/**
 * Recurso de una aprobacion
 *
 * @author Sofia Alvarez
 */
public class AprobacionResource {

    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(AprobacionResource.class.getName());

    /**
     * Inyección de la logica de la aprobacion
     */
    @Inject
    private AprobacionLogic aprobacionLogic;

    /**
     * Metodo para crear una aprobacion.
     *
     * @param aprobacion es el DTO de la aprobacion a crear
     * @return json de la aprobacion creada
     * @throws BusinessLogicException si se incumple alguna
     */
    @POST
    public AprobacionDTO createAprobacion(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, AprobacionDTO aprobacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AprobacionResource createAprobacion: input: {0}", aprobacion);
        AprobacionDTO reqDTO;
        if (requisitosId == null) {
            reqDTO = new AprobacionDTO(aprobacionLogic.createAprobacionObjetivo(proyectosId, objetivosId, aprobacion.toEntity()));
        } else {
            reqDTO = new AprobacionDTO(aprobacionLogic.createAprobacionRequisito(objetivosId, requisitosId, aprobacion.toEntity()));
        }
        LOGGER.log(Level.INFO, "AprobacionResource createAprobacion: output: {0}", reqDTO);
        return reqDTO;
    }

    /**
     * Lista de aprobaciones
     *
     * @return una lista con todas las aprobaciones.
     */
    @GET
    public List<AprobacionDTO> getAprobaciones(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        LOGGER.info("AprobacionResource getAprobaciones: input: void");
        List<AprobacionDTO> listaAps;
        if (requisitosId != null) {
            listaAps = listEntity2DTO(aprobacionLogic.getAprobacionesRequisito(objetivosId, requisitosId));
        } else {
            listaAps = listEntity2DTO(aprobacionLogic.getAprobacionesObjetivo(proyectosId, objetivosId));

        }
        LOGGER.log(Level.INFO, "AprobacionResource getAprobaciones: output: {0}", listaAps);
        return listaAps;
    }

    /**
     * Consigue una sola aprobacion dado un id
     *
     * @param id el id de la aprobacion a obtener
     * @return la aprobacion que se quiere.
     */
    @GET
    @Path("{aprobacionesId: \\d+}")
    public AprobacionDTO getAprobacion(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, @PathParam("aprobacionesId") Long aprobacionesId) {
        LOGGER.log(Level.INFO, "AprobacionResource getAprobacion: input: {0}", aprobacionesId);
        AprobacionEntity aprobacionEntity;
        if (requisitosId != null) {
            aprobacionEntity = aprobacionLogic.getAprobacionRequisito(requisitosId, aprobacionesId);
            if (aprobacionEntity == null) {
                throw new WebApplicationException("El recurso /requisitos/" + requisitosId + "/aprobaciones/" + aprobacionesId + " no existe.", 404);
            }
        } else {
            aprobacionEntity = aprobacionLogic.getAprobacionObjetivo(objetivosId, aprobacionesId);
            if (aprobacionEntity == null) {
                throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/aprobaciones/" + aprobacionesId + " no existe.", 404);
            }
        }
        AprobacionDTO aprobacionDTO = new AprobacionDTO(aprobacionEntity);
        LOGGER.log(Level.INFO, "AprobacionResource getAprobacion: output: {0}", aprobacionDTO);
        return aprobacionDTO;
    }

    /**
     * Elimina una aprobacion con un id dado.
     *
     * @param aprobacionesId de la aprobacion para eliminar
     * @return la aprobacion eliminada
     */
    @DELETE
    @Path("{aprobacionesId: \\d+}")
    public void deleteAprobacion(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, @PathParam("aprobacionesId") Long aprobacionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "AprobacionResource deleteAprobacion: input: {0}", aprobacionesId);
        if (requisitosId == null) {
            AprobacionEntity entity = aprobacionLogic.getAprobacionObjetivo(objetivosId, aprobacionesId);
            if (entity == null) {
                throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/aprobaciones/" + aprobacionesId + " no existe.", 404);
            }
            aprobacionLogic.deleteAprobacionObjetivo(objetivosId, aprobacionesId);
        } else {
            AprobacionEntity entity = aprobacionLogic.getAprobacionRequisito(requisitosId, aprobacionesId);
            if (entity == null) {
                throw new WebApplicationException("El recurso /requisitos/" + requisitosId + "/aprobaciones/" + aprobacionesId + " no existe.", 404);
            }
            aprobacionLogic.deleteAprobacionRequisito(requisitosId, aprobacionesId);
        }
        LOGGER.info("RequisitoResource deleteRequisito: output: void");
    }

    /**
     * Actualiza una aprobacion
     *
     * @param aprobacionId id de la aprobacion
     * @param aprobacion aprobacion a actualizar
     * @return aprobacion actualizada
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @PUT
    @Path("{aprobacionesId: \\d+}")
    public AprobacionDTO updateAprobacion(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, AprobacionDTO dto, @PathParam("aprobacionesId") Long aprobacionId) throws BusinessLogicException {
        dto.setId(aprobacionId);
        AprobacionEntity e;
        boolean objetivo = false;
        if (requisitosId == null) {
            objetivo = true;
            e = aprobacionLogic.getAprobacionObjetivo(objetivosId, aprobacionId);
        } else {
            e = aprobacionLogic.getAprobacionRequisito(requisitosId, aprobacionId);
        }
        if (e == null) {
            throw new WebApplicationException("El recurso /aprobaciones/" + aprobacionId + " no existe.", 404);
        }
        AprobacionDTO current = new AprobacionDTO(e);
        if (dto.getComentario() == null || dto.getComentario().equals("")) {
            dto.setComentario(current.getComentario());
        }
        if (dto.getEstado() == null || dto.getEstado().equals("")) {
            dto.setEstado(current.getEstado());
        }
        if (dto.getFechaYHora() == null || dto.getFechaYHora().equals("")) {
            dto.setFechaYHora(current.getFechaYHora());
        }
        if (dto.getStakeholder() == null) {
            dto.setStakeholder(current.getStakeholder());
        }
        AprobacionDTO aprobDTO;
        if (objetivo) {
            aprobDTO = new AprobacionDTO(aprobacionLogic.updateAprobacionObjetivo(proyectosId, objetivosId, dto.toEntity()));
        } else {
            aprobDTO = new AprobacionDTO(aprobacionLogic.updateAprobacionRequisito(objetivosId, requisitosId, dto.toEntity()));
        }
        return aprobDTO;

    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos AprobacionEntity a una lista
     * de objetos AprobacionDTO (json)
     *
     * @param entityList corresponde a la lista de aprobaciones de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de aprobaciones en forma DTO (json)
     */
    private List<AprobacionDTO> listEntity2DTO(List<AprobacionEntity> entityList) {
        List<AprobacionDTO> list = new ArrayList<>();
        for (AprobacionEntity entity : entityList) {
            list.add(new AprobacionDTO(entity));
        }
        return list;
    }
}

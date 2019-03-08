/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this requisitolate file, choose Tools | Requisitolates
 * and open the requisitolate in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.RequisitoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.RequisitoLogic;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoRequisitoLogic;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.mappers.WebApplicationExceptionMapper;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "objetivos/{id}/requisitos".
 *
 * @objetivo jorgeandresesguerraalarcon
 * @version 1.0
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObjetivoRequisitoResource {

    private static final Logger LOGGER = Logger.getLogger(ObjetivoRequisitoResource.class.getName());

    @Inject
    private ObjetivoRequisitoLogic objetivoRequisitoLogic;

    @Inject
    private RequisitoLogic requisitoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un requisito existente con un objetivo existente
     *
     * @param objetivosId El ID del objetivo al cual se le va a asociar el requisito
     * @param requisitosId El ID del requisito que se asocia
     * @return JSON {@link RequisitoDetailDTO} - El requisito asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @POST
    @Path("{requisitosId: \\d+}")
    public RequisitoDetailDTO addRequisito(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource addRequisito: input: objetivosId {0} , requisitosId {1}", new Object[]{objetivosId, requisitosId});
        if (requisitoLogic.getRequisito(requisitosId) == null) {
            throw new WebApplicationException("El recurso /requisitos/" + requisitosId + " no existe.", 404);
        }
        RequisitoDetailDTO detailDTO = new RequisitoDetailDTO(objetivoRequisitoLogic.addRequisito(objetivosId, requisitosId));
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource addRequisito: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los requisitos que existen en un objetivo.
     *
     * @param objetivosId El ID del objetivo del cual se buscan los requisitos
     * @return JSONArray {@link RequisitoDetailDTO} - Los requisitos encontrados en el
     * objetivo. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<RequisitoDetailDTO> getRequisitos(@PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource getRequisitos: input: {0}", objetivosId);
        List<RequisitoDetailDTO> lista = requisitosListEntity2DTO(objetivoRequisitoLogic.getRequisitos(objetivosId));
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource getRequisitos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el requisito con el ID recibido en la URL, relativo a un
     * objetivo.
     *
     * @param objetivosId El ID del objetivo del cual se busca el requisito
     * @param requisitosId El ID del requisito que se busca
     * @return {@link RequisitoDetailDTO} - El requisito encontrado en el objetivo.
     * @throws co.edu.uniandes.csw.requisitostore.exceptions.BusinessLogicException
     * si el requisito no está asociado al objetivo
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @GET
    @Path("{requisitosId: \\d+}")
    public RequisitoDetailDTO getRequisito(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource getRequisito: input: objetivosId {0} , requisitosId {1}", new Object[]{objetivosId, requisitosId});
        if (requisitoLogic.getRequisito(requisitosId) == null) {
            throw new WebApplicationException("El recurso /requisitos/" + requisitosId + " no existe.", 404);
        }
        RequisitoDetailDTO detailDTO = new RequisitoDetailDTO(objetivoRequisitoLogic.getRequisito(objetivosId, requisitosId));
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource getRequisito: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de requisitos de un objetivo con la lista que se recibe en el
     * cuerpo
     *
     * @param objetivosId El ID del objetivo al cual se le va a asociar el requisito
     * @param requisitos JSONArray {@link RequisitoDetailDTO} - La lista de requisitos que se
     * desea guardar.
     * @return JSONArray {@link RequisitoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @PUT
    public List<RequisitoDetailDTO> replaceRequisitos(@PathParam("objetivosId") Long objetivosId, List<RequisitoDetailDTO> requisitos) {
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource replaceRequisitos: input: objetivosId {0} , requisitos {1}", new Object[]{objetivosId, requisitos});
        for (RequisitoDetailDTO requisito : requisitos) {
            if (requisitoLogic.getRequisito(requisito.getId()) == null) {
                throw new WebApplicationException("El recurso /requisitos/" + requisito.getId() + " no existe.", 404);
            }
        }
        List<RequisitoDetailDTO> lista = requisitosListEntity2DTO(objetivoRequisitoLogic.replaceRequisitos(objetivosId, requisitosListDTO2Entity(requisitos)));
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource replaceRequisitos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el requisito y e objetivo recibidos en la URL.
     *
     * @param objetivosId El ID del objetivo al cual se le va a desasociar el requisito
     * @param requisitosId El ID del requisito que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el requisito.
     */
    @DELETE
    @Path("{requisitosId: \\d+}")
    public void removeRequisito(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        LOGGER.log(Level.INFO, "ObjetivoRequisitoResource deleteRequisito: input: objetivosId {0} , requisitosId {1}", new Object[]{objetivosId, requisitosId});
        if (requisitoLogic.getRequisito(requisitosId) == null) {
            throw new WebApplicationException("El recurso /requisitos/" + requisitosId + " no existe.", 404);
        }
        objetivoRequisitoLogic.removeRequisito(objetivosId, requisitosId);
        LOGGER.info("ObjetivoRequisitoResource deleteRequisito: output: void");
    }

    /**
     * Convierte una lista de RequisitoEntity a una lista de RequisitoDetailDTO.
     *
     * @param entityList Lista de RequisitoEntity a convertir.
     * @return Lista de RequisitoDetailDTO convertida.
     */
    private List<RequisitoDetailDTO> requisitosListEntity2DTO(List<RequisitoEntity> entityList) {
        List<RequisitoDetailDTO> list = new ArrayList<>();
        for (RequisitoEntity entity : entityList) {
            list.add(new RequisitoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de RequisitoDetailDTO a una lista de RequisitoEntity.
     *
     * @param dtos Lista de RequisitoDetailDTO a convertir.
     * @return Lista de RequisitoEntity convertida.
     */
    private List<RequisitoEntity> requisitosListDTO2Entity(List<RequisitoDetailDTO> dtos) {
        List<RequisitoEntity> list = new ArrayList<>();
        for (RequisitoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

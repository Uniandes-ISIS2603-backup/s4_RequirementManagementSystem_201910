/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
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
 * Clase que implementa el recurso "requisitos/{id}/objetivos".
 *
 * @requisito jorgeandresesguerraalarcon
 * @version 1.0
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequisitoObjetivoResource {

    private static final Logger LOGGER = Logger.getLogger(RequisitoObjetivoResource.class.getName());

    @Inject
    private RequisitoObjetivoLogic requisitoObjetivoLogic;

    @Inject
    private ObjetivoLogic objetivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un objetivo existente con un requisito existente
     *
     * @param requisitosId El ID del requisito al cual se le va a asociar el objetivo
     * @param objetivosId El ID del objetivo que se asocia
     * @return JSON {@link ObjetivoDetailDTO} - El objetivo asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @POST
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO addObjetivo(@PathParam("requisitosId") Long requisitosId, @PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource addObjetivo: input: requisitosId {0} , objetivosId {1}", new Object[]{requisitosId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO detailDTO = new ObjetivoDetailDTO(requisitoObjetivoLogic.addObjetivo(requisitosId, objetivosId));
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource addObjetivo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los objetivos que existen en un requisito.
     *
     * @param requisitosId El ID del requisito del cual se buscan los objetivos
     * @return JSONArray {@link ObjetivoDetailDTO} - Los objetivos encontrados en el
     * requisito. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ObjetivoDetailDTO> getObjetivos(@PathParam("requisitosId") Long requisitosId) {
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource getObjetivos: input: {0}", requisitosId);
        List<ObjetivoDetailDTO> lista = objetivosListEntity2DTO(requisitoObjetivoLogic.getObjetivos(requisitosId));
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource getObjetivos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el objetivo con el ID recibido en la URL, relativo a un
     * requisito.
     *
     * @param requisitosId El ID del requisito del cual se busca el objetivo
     * @param objetivosId El ID del objetivo que se busca
     * @return {@link ObjetivoDetailDTO} - El objetivo encontrado en el requisito.
     * @throws co.edu.uniandes.csw.objetivostore.exceptions.BusinessLogicException
     * si el objetivo no está asociado al requisito
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @GET
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO getObjetivo(@PathParam("requisitosId") Long requisitosId, @PathParam("objetivosId") Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource getObjetivo: input: requisitosId {0} , objetivosId {1}", new Object[]{requisitosId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO detailDTO = new ObjetivoDetailDTO(requisitoObjetivoLogic.getObjetivo(requisitosId, objetivosId));
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource getObjetivo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de objetivos de un requisito con la lista que se recibe en el
     * cuerpo
     *
     * @param requisitosId El ID del requisito al cual se le va a asociar el objetivo
     * @param objetivos JSONArray {@link ObjetivoDetailDTO} - La lista de objetivos que se
     * desea guardar.
     * @return JSONArray {@link ObjetivoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @PUT
    public List<ObjetivoDetailDTO> replaceObjetivos(@PathParam("requisitosId") Long requisitosId, List<ObjetivoDetailDTO> objetivos) {
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource replaceObjetivos: input: requisitosId {0} , objetivos {1}", new Object[]{requisitosId, objetivos});
        for (ObjetivoDetailDTO objetivo : objetivos) {
            if (objetivoLogic.getObjetivo(objetivo.getId()) == null) {
                throw new WebApplicationException("El recurso /objetivos/" + objetivo.getId() + " no existe.", 404);
            }
        }
        List<ObjetivoDetailDTO> lista = objetivosListEntity2DTO(requisitoObjetivoLogic.replaceObjetivos(requisitosId, objetivosListDTO2Entity(objetivos)));
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource replaceObjetivos: output: {0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el objetivo y e requisito recibidos en la URL.
     *
     * @param requisitosId El ID del requisito al cual se le va a desasociar el objetivo
     * @param objetivosId El ID del objetivo que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @DELETE
    @Path("{objetivosId: \\d+}")
    public void removeObjetivo(@PathParam("requisitosId") Long requisitosId, @PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "RequisitoObjetivoResource deleteObjetivo: input: requisitosId {0} , objetivosId {1}", new Object[]{requisitosId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        requisitoObjetivoLogic.removeObjetivo(requisitosId, objetivosId);
        LOGGER.info("RequisitoObjetivoResource deleteObjetivo: output: void");
    }

    /**
     * Convierte una lista de ObjetivoEntity a una lista de ObjetivoDetailDTO.
     *
     * @param entityList Lista de ObjetivoEntity a convertir.
     * @return Lista de ObjetivoDetailDTO convertida.
     */
    private List<ObjetivoDetailDTO> objetivosListEntity2DTO(List<ObjetivoEntity> entityList) {
        List<ObjetivoDetailDTO> list = new ArrayList<>();
        for (ObjetivoEntity entity : entityList) {
            list.add(new ObjetivoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ObjetivoDetailDTO a una lista de ObjetivoEntity.
     *
     * @param dtos Lista de ObjetivoDetailDTO a convertir.
     * @return Lista de ObjetivoEntity convertida.
     */
    private List<ObjetivoEntity> objetivosListDTO2Entity(List<ObjetivoDetailDTO> dtos) {
        List<ObjetivoEntity> list = new ArrayList<>();
        for (ObjetivoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}

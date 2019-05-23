/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.ObjetivoDTO;
import co.edu.uniandes.csw.requirement.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * Clase que representa el Recurso para obtener DTOS de tipo Objetivo
 *
 * @author David Manosalva
 */
@Produces("application/json")
@Consumes("application/json")
public class ObjetivoResource {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(ObjetivoResource.class.getName());

    /**
     * Logica de la clase
     */
    @Inject
    private ObjetivoLogic objetivoLogic;

    /**
     * Crea un nuevo Objetivo con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param proyectosId El ID del proyecto del cual se le agrega la objetivo
     * @param objetivo{@link ObjetivoDTO} - La objetivo que se desea guardar.
     * @return JSON {@link ObjetivoDTO} - El objetivo guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el Objetivo.
     */
    @POST
    public ObjetivoDetailDTO createObjetivo(@PathParam("proyectosId") Long proyectosId, ObjetivoDTO objetivo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoResource createObjetivo: input: {0}", objetivo);
        ObjetivoDetailDTO objetivoDTO = new ObjetivoDetailDTO(objetivoLogic.createObjetivo(proyectosId, objetivo.toEntity()));
        LOGGER.log(Level.INFO, "ObjetivoResource createObjetivo: output: {0}", objetivoDTO);
        return objetivoDTO;
    }

    /**
     * Metodo que retorna todoslos objetivos en objetos DTO
     *
     * @param proyectosId el id del proyecto papá.
     * @return Lista con los ObjetivosDetailDTO
     */
    @GET
    public List<ObjetivoDetailDTO> getObjetivos(@PathParam("proyectosId") Long proyectosId) {
        LOGGER.info("ObjetivoResource getObjetivos: input: void");
        List<ObjetivoDetailDTO> listaObjetivos = listEntity2DTO(objetivoLogic.getObjetivos(proyectosId));
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivos: output: {0}", listaObjetivos);
        return listaObjetivos;
    }

    /**
     * Metodo que retorna el objetivoDTO dado por parametro
     *
     * @param proyectosId el id del proyecto padre.
     * @param objetivosId Id del objetivo a consultar
     * @return Objetivo consultado
     */
    @GET
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO getObjetivo(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivo: input: {0}", objetivosId);
        ObjetivoEntity objetivoEntity = objetivoLogic.getObjetivo(proyectosId, objetivosId);
        if (objetivoEntity == null) {
            throw new WebApplicationException("El recurso /proyectos" + proyectosId + "/objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO detailDTO = new ObjetivoDetailDTO(objetivoEntity);
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza una reseña con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param proyectosId El ID del proyecto del cual se guarda el objetivo
     * @param objetivosId El ID del objetivo que se va a actualizar
     * @param objetivo {@link ObjetivoDTO} - el objetivo que se va a guardar.
     * @return JSON {@link ObjetivoDTO} - El objetivo actualizado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el objetivo.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    //**
    // WARNING! PREGUNTAR SI ES DETAIL O NO
    //
    @PUT
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO updateObjetivo(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId, ObjetivoDetailDTO objetivo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoResource updateObjetivo: input: proyectosId: {0} , objetivosId: {1} , objetivo: {2}", new Object[]{proyectosId, objetivosId, objetivo});
       
        ObjetivoEntity oe = objetivoLogic.getObjetivo(proyectosId, objetivosId);
        if (oe == null) {
            throw new WebApplicationException("El recurso proyectos/" + proyectosId + "/objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO current = new ObjetivoDetailDTO(oe);
        objetivo.setRequisitos(current.getRequisitos());
        objetivo.setAprobaciones(current.getAprobaciones());
        objetivo.setAutor(current.getAutor());
        objetivo.setFuentes(current.getFuentes());
        objetivo.setId(objetivosId);
        
        if (objetivo.getComentarios() == null || objetivo.getComentarios().equals(""))
        {
            objetivo.setComentarios(current.getComentarios());
        }
        if (objetivo.getImportancia() == null)
        {
            objetivo.setImportancia(current.getImportancia());
        }
        if (objetivo.getDescripcion() == null || objetivo.getDescripcion().equals(""))
        {
            objetivo.setDescripcion(current.getDescripcion());
        }
        if (objetivo.getEstabilidad() == null)
        {
            objetivo.setEstabilidad(current.getEstabilidad());
        }
      
        
        ObjetivoDetailDTO detailDTO = new ObjetivoDetailDTO(objetivoLogic.updateObjetivo(proyectosId, objetivo.toEntity()));
        LOGGER.log(Level.INFO, "ObjetivoResource updateObjetivo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra la reseña con el id asociado recibido en la URL.
     *
     * @param booksId El ID del libro del cual se va a eliminar la reseña.
     * @param reviewsId El ID de la reseña que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la reseña.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la reseña.
     */
    @DELETE
    @Path("{objetivosId: \\d+}")
    public void deleteObjetivo(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoResource deleteObjetivo: input: proyectosId: {0} , objetivosId: {1} , ", new Object[]{proyectosId, objetivosId});
        ObjetivoEntity entity = objetivoLogic.getObjetivo(proyectosId, objetivosId);
        if (!entity.getRequisitos().isEmpty())
        {
            throw new BusinessLogicException("No se puede borrar el objetivo con id " + objetivosId + " pues tiene requisitos dependientes ");
        }
        if (entity == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + "/objetivos/" + objetivosId + " no existe.", 404);
        }
        objetivoLogic.deleteObjetivo(proyectosId, objetivosId);
        LOGGER.info("ObjetivoResource deleteObjetivo: output: void");
    }

    @Path("{objetivosId: \\d+}/requisitos")
    public Class<RequisitoResource> getRequisitoResource(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId) {
        if (objetivoLogic.getObjetivo(proyectosId, objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        return RequisitoResource.class;
    }
    
    @Path("{objetivosId: \\d+}/cambios")
    public Class<CambioResource> getCambiosResource(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId) {
        if (objetivoLogic.getObjetivo(proyectosId, objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        return CambioResource.class;
    }

    /**
     * Lista que devuelve una lista de objetos de tipo DTO de una lista de
     * ObjetivoEntity
     *
     * @param entityList Lista de ObjetivoEmntity a cambiar a DTo
     * @return Lista cambiada de DTO
     */
    private List<ObjetivoDetailDTO> listEntity2DTO(List<ObjetivoEntity> entityList) {
        List<ObjetivoDetailDTO> list = new ArrayList<>();
        for (ObjetivoEntity entity : entityList) {
            list.add(new ObjetivoDetailDTO(entity));
        }
        return list;
    }
    
    @Path("{objetivosId: \\d+}/aprobaciones")
    public Class<AprobacionResource> getAprobacionResource(@PathParam("proyectosId") Long proyectosId, @PathParam("objetivosId") Long objetivosId) {
        if (objetivoLogic.getObjetivo(proyectosId, objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        return AprobacionResource.class;
    }

}

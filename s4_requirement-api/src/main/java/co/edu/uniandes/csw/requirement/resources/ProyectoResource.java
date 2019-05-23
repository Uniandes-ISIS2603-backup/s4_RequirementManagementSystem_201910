/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.ProyectoDTO;
import co.edu.uniandes.csw.requirement.dtos.ProyectoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.ProyectoLogic;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import co.edu.uniandes.csw.requirement.patch.PATCH;

/**
 * Clase que representa el Recurso para obtener DTOS de tipo Proyecto
 * @author David Manosalva, Jorge Esguerra
 */
@Path("proyectos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProyectoResource {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(ProyectoResource.class.getName());

     /**
     * Logica de la clase
     */
    @Inject
    private ProyectoLogic proyectoLogic;

    /**
     * Metodo que realiza el POST
     * @param proyecto Proyecto a añadir
     * @return DTO que fue creado
     * @throws BusinessLogicException  Si no cumple con las reglas de estabilidad, impotancia y descripcion
     */
    @POST
    public ProyectoDTO createProyecto(ProyectoDTO proyecto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProyectoResource createProyecto: input: {0}", proyecto);
        ProyectoDTO proyectoDTO = new ProyectoDTO(proyectoLogic.createProyecto(proyecto.toEntity()));
        LOGGER.log(Level.INFO, "ProyectoResource createProyecto: output: {0}", proyectoDTO);
        return proyectoDTO;
    }

    /**
     * Metodo que retorna todoslos proyectos en objetos DTO
     * @return Lista con los Objetos DTO
     */
    @GET
    public List<ProyectoDetailDTO> getProyectos() {
        LOGGER.info("ProyectoResource getProyectos: input: void");
        List<ProyectoDetailDTO> listaProyectos = listEntity2DTO(proyectoLogic.getProyectos());
        LOGGER.log(Level.INFO, "ProyectoResource getProyectos: output: {0}", listaProyectos);
        return listaProyectos;
    }
    
    
    /**
     * Metodo que retorna el proyectoDTO dado por parametro
     * @param proyectosId Id del proyecto a consultar
     * @return Proyecto consultado
     */
    @GET
    @Path("{proyectosId: \\d+}")
    public ProyectoDetailDTO getProyecto(@PathParam("proyectosId") Long proyectosId) {
        LOGGER.log(Level.INFO, "ProyectoResource getProyecto: input: {0}", proyectosId);
        ProyectoEntity proyectoEntity = proyectoLogic.getProyecto(proyectosId);
        if (proyectoEntity == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        ProyectoDetailDTO detailDTO = new ProyectoDetailDTO(proyectoEntity);
        LOGGER.log(Level.INFO, "ProyectoResource getProyecto: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Metodo para actualizar un proyecto ya creado
     * @param proyectosId Id del proyecto a actualizar
     * @param proyecto Proyecto con el cual se va a reemplazar
     * @return ProyectoDetail dado por la persistencia despues de pasar por la logica
     * @throws BusinessLogicException Si no cumple con las reglas de estabilidad, impotancia y descripcion
     */
    @PUT
    @Path("{proyectosId: \\d+}")
    public ProyectoDetailDTO updateProyecto(@PathParam("proyectosId") Long proyectosId, ProyectoDetailDTO proyecto) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "ProyectoResource updateProyecto: input: proyectosId: {0} , proyecto: {1}", new Object[]{proyectosId, proyecto});
        ProyectoEntity p = proyectoLogic.getProyecto(proyectosId);
        
        if (p == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        ProyectoDetailDTO current = new ProyectoDetailDTO(p);
        proyecto.setId(proyectosId);
        proyecto.setObjetivos(current.getObjetivos());
        if (proyecto.getDescripcion() == null || proyecto.getDescripcion().equals(""))
        {
            proyecto.setDescripcion(current.getDescripcion());
        }
        if (proyecto.getNombre() == null || proyecto.getNombre().equals(""))
        {
            proyecto.setNombre(current.getNombre());
        }
        
        ProyectoDetailDTO detailDTO = new ProyectoDetailDTO(proyectoLogic.updateProyecto(proyectosId, proyecto.toEntity()));
        LOGGER.log(Level.INFO, "ProyectoResource updateProyecto: output: {0}", detailDTO);
        return detailDTO;
    }
    
   
    
    
    /**
     * Metodo para eliminar un proyecto por id
     * @param proyectosId Id del proyecto a eliminar
     * @throws BusinessLogicException  Si el proyecto no existe
     */
    @DELETE
    @Path("{proyectosId: \\d+}")
    public void deleteProyecto(@PathParam("proyectosId") Long proyectosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ProyectoResource deleteProyecto: input: {0}", proyectosId);
        ProyectoEntity proyectoEntity = proyectoLogic.getProyecto(proyectosId);
        if (proyectoEntity == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        proyectoLogic.deleteProyecto(proyectosId);
        LOGGER.info("ProyectoResource deleteProyecto: output: void");
    }
    
    /**
     * Conexión con el servicio de objetivos para un proyecto.
     * {@link ObjetivoResource}
     *
     * Este método conecta la ruta de /proyectos con las rutas de /objetivos que
     * dependen del proyecto, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los objetivos.
     *
     * @param proyectosId El ID del proyecto con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese libro en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @Path("{proyectosId: \\d+}/objetivos")
    public Class<ObjetivoResource> getObjetivoResource(@PathParam("proyectosId") Long proyectosId) {
        if (proyectoLogic.getProyecto(proyectosId) == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        return ObjetivoResource.class;
    }
    
    /**
     * Lista que devuelve una lista de objetos de tipo DTO de una lista de ProyectoEntity
     * @param entityList Lista de ProyectoEmntity a cambiar a DTo
     * @return Lista cambiada de DTO
     */
    private List<ProyectoDetailDTO> listEntity2DTO(List<ProyectoEntity> entityList) {
        List<ProyectoDetailDTO> list = new ArrayList<>();
        for (ProyectoEntity entity : entityList) {
            list.add(new ProyectoDetailDTO(entity));
        }
        return list;
    }
    
}

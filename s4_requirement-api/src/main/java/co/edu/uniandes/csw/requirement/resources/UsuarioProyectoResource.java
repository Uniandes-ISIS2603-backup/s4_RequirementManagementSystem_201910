/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.ProyectoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.UsuarioProyectoLogic;
import co.edu.uniandes.csw.requirement.ejb.ProyectoLogic;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @proyecto estudiante
 */
@Path ("usuarios/{usuariosId}/proyectos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioProyectoResource {


    @Inject
    private UsuarioProyectoLogic usuarioProyectoLogic;

    @Inject
    private ProyectoLogic proyectoLogic;

    /**
     * Asocia un proyecto existente con un usuario existente
     *
     * @param proyectosId El ID del proyecto que se va a asociar
     * @param usuariosId El ID del usuario al cual se le va a asociar el proyecto
     * @return JSON {@link ProyectoDetailDTO} - El proyecto asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @POST
    @Path("{proyectosId: \\d+}")
    public ProyectoDetailDTO addProyecto(@PathParam("usuariosId") Long usuariosId, @PathParam("proyectosId") Long proyectosId) {
        if (proyectoLogic.getProyecto(proyectosId) == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        ProyectoDetailDTO detailDTO = new ProyectoDetailDTO(usuarioProyectoLogic.addProyecto(usuariosId, proyectosId));
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los proyectoes que existen en un usuario.
     *
     * @param usuariosId El ID del usuario del cual se buscan los proyectoes
     * @return JSONArray {@link ProyectoDetailDTO} - Los proyectoes encontrados en el
     * usuario. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProyectoDetailDTO> getProyectos(@PathParam("usuariosId") Long usuariosId) {
       List<ProyectoDetailDTO> lista = proyectosListEntity2DTO(usuarioProyectoLogic.getProyectos(usuariosId));
        return lista;
    }

    /**
     * Busca y devuelve el proyecto con el ID recibido en la URL, relativo a un
     * usuario.
     *
     * @param proyectosId El ID del proyecto que se busca
     * @param usuariosId El ID del usuario del cual se busca el proyecto
     * @return {@link ProyectoDetailDTO} - El proyecto encontrado en el usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @GET
    @Path("{proyectosId: \\d+}")
    public ProyectoDetailDTO getProyecto(@PathParam("usuariosId") Long usuariosId, @PathParam("proyectosId") Long proyectosId) {
        if (proyectoLogic.getProyecto(proyectosId) == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        ProyectoDetailDTO detailDTO = new ProyectoDetailDTO(usuarioProyectoLogic.getProyecto(usuariosId, proyectosId));
        return detailDTO;
    }

    /**
     * Actualiza la lista de proyectoes de un usuario con la lista que se recibe en
     * el cuerpo.
     *
     * @param usuariosId El ID del usuario al cual se le va a asociar la lista de
     * proyectoes
     * @param proyectos JSONArray {@link ProyectoDetailDTO} - La lista de proyectoes
     * que se desea guardar.
     * @return JSONArray {@link ProyectoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @PUT
    public List<ProyectoDetailDTO> replaceProyectos(@PathParam("usuariosId") Long usuariosId, List<ProyectoDetailDTO> proyectos) {
       for (ProyectoDetailDTO proyecto : proyectos) {
            if (proyectoLogic.getProyecto(proyecto.getId()) == null) {
                throw new WebApplicationException("El recurso /proyectos/" + proyecto.getId() + " no existe.", 404);
            }
        }
        List<ProyectoDetailDTO> lista = proyectosListEntity2DTO(usuarioProyectoLogic.replaceProyectos(usuariosId, proyectosListDTO2Entity(proyectos)));
        return lista;
    }

    /**
     * Elimina la conexión entre el proyecto y el usuario recibidos en la URL.
     *
     * @param usuariosId El ID del usuario al cual se le va a desasociar el proyecto
     * @param proyectosId El ID del proyecto que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el proyecto.
     */
    @DELETE
    @Path("{proyectosId: \\d+}")
    public void removeProyecto(@PathParam("usuariosId") Long usuariosId, @PathParam("proyectosId") Long proyectosId) {
        if (proyectoLogic.getProyecto(proyectosId) == null) {
            throw new WebApplicationException("El recurso /proyectos/" + proyectosId + " no existe.", 404);
        }
        usuarioProyectoLogic.removeProyecto(usuariosId, proyectosId);
    }

    /**
     * Convierte una lista de ProyectoEntity a una lista de ProyectoDetailDTO.
     *
     * @param entityList Lista de ProyectoEntity a convertir.
     * @return Lista de ProyectoDetailDTO convertida.
     */
    private List<ProyectoDetailDTO> proyectosListEntity2DTO(List<ProyectoEntity> entityList) {
        List<ProyectoDetailDTO> list = new ArrayList<>();
        for (ProyectoEntity entity : entityList) {
            list.add(new ProyectoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ProyectoDetailDTO a una lista de ProyectoEntity.
     *
     * @param dtos Lista de ProyectoDetailDTO a convertir.
     * @return Lista de ProyectoEntity convertida.
     */
    private List<ProyectoEntity> proyectosListDTO2Entity(List<ProyectoDetailDTO> dtos) {
        List<ProyectoEntity> list = new ArrayList<>();
        for (ProyectoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

}

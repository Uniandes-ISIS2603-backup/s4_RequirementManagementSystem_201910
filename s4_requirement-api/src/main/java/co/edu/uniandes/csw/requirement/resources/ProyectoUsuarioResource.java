/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.UsuarioLogic;
import co.edu.uniandes.csw.requirement.ejb.ProyectoUsuarioLogic;
import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
 * @proyecto Mateo Pedroza
 */
@Path ("proyectos/{proyectosId}/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProyectoUsuarioResource {
    
    

    @Inject
    private ProyectoUsuarioLogic proyectoUsuarioLogic;

    @Inject
    private UsuarioLogic usuariosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un usuario existente con un proyecto existente
     *
     * @param proyectosId El ID del proyecto al cual se le va a asociar el usuario
     * @param usuariosId El ID del usuario que se asocia
     * @return JSON {@link UsuarioDetailDTO} - El usuario asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @POST
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO addUsuario(@PathParam("proyectosId") Long proyectosId, @PathParam("usuariosId") Long usuariosId) {
        if (usuariosLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(proyectoUsuarioLogic.addUsuario(proyectosId, usuariosId));
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los usuarios que existen en un proyecto.
     *
     * @param proyectosId El ID del proyecto del cual se buscan los usuarios
     * @return JSONArray {@link UsuarioDetailDTO} - Los usuarios encontrados en el
     * proyecto. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios(@PathParam("proyectosId") Long proyectosId) {
        List<UsuarioDetailDTO> lista = usuariosListEntity2DTO(proyectoUsuarioLogic.getUsuarios(proyectosId));
        return lista;
    }

    /**
     * Busca y devuelve el usuario con el ID recibido en la URL, relativo a un
     * proyecto.
     *
     * @param proyectosId El ID del proyecto del cual se busca el usuario
     * @param usuariosId El ID del usuario que se busca
     * @return {@link UsuarioDetailDTO} - El usuario encontrado en el proyecto.
     * @throws co.edu.uniandes.csw.usuariostore.exceptions.BusinessLogicException
     * si el usuario no está asociado al proyecto
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("proyectosId") Long proyectosId, @PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        if (usuariosLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(proyectoUsuarioLogic.getUsuario(proyectosId, usuariosId));
        return detailDTO;
    }

    /**
     * Actualiza la lista de usuarios de un proyecto con la lista que se recibe en el
     * cuerpo
     *
     * @param proyectosId El ID del proyecto al cual se le va a asociar el usuario
     * @param usuarios JSONArray {@link UsuarioDetailDTO} - La lista de usuarios que se
     * desea guardar.
     * @return JSONArray {@link UsuarioDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @PUT
    public List<UsuarioDetailDTO> replaceUsuarios(@PathParam("proyectosId") Long proyectosId, List<UsuarioDetailDTO> usuarios) {
        for (UsuarioDetailDTO org : usuarios) {
            if (usuariosLogic.getUsuario(org.getId()) == null) {
                throw new WebApplicationException("El recurso /usuarios/" + org.getId() + " no existe.", 404);
            }
        }
        List<UsuarioDetailDTO> lista = usuariosListEntity2DTO(proyectoUsuarioLogic.replaceUsuarios(proyectosId, usuariosListDTO2Entity(usuarios)));
        return lista;
    }

    /**
     * Elimina la conexión entre el usuario y e proyecto recibidos en la URL.
     *
     * @param proyectosId El ID del proyecto al cual se le va a desasociar el usuario
     * @param usuariosId El ID del usuario que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void removeUsuario(@PathParam("proyectosId") Long proyectosId, @PathParam("usuariosId") Long usuariosId) {
        if (usuariosLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        proyectoUsuarioLogic.removeUsuario(proyectosId, usuariosId);
    }

    /**
     * Convierte una lista de UsuarioEntity a una lista de UsuarioDetailDTO.
     *
     * @param entityList Lista de UsuarioEntity a convertir.
     * @return Lista de UsuarioDetailDTO convertida.
     */
    private List<UsuarioDetailDTO> usuariosListEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de UsuarioDetailDTO a una lista de UsuarioEntity.
     *
     * @param dtos Lista de UsuarioDetailDTO a convertir.
     * @return Lista de UsuarioEntity convertida.
     */
    private List<UsuarioEntity> usuariosListDTO2Entity(List<UsuarioDetailDTO> dtos) {
        List<UsuarioEntity> list = new ArrayList<>();
        for (UsuarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}

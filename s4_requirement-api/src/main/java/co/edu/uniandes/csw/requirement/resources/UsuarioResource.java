package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.UsuarioDTO;
import co.edu.uniandes.csw.requirement.ejb.UsuarioLogic;
import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 *
 * @author Mateo Pedroza
 */
@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {

    @Inject
    private UsuarioLogic logica;

    /**
     * Agrega un usuario a la base de datos  
     * @param usuario
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public UsuarioDTO createUsuario(UsuarioDTO usuario) throws BusinessLogicException {

        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuario = logica.createUsuario(usuarioEntity);
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuario);
        return nuevoUsuarioDTO;

    }

    /**
     * retorna todos los usuarios existentes en base de datos
     * @return lista de usuarios
     */
    @GET
    public List<UsuarioDTO> getUsuarios() {
        List<UsuarioDTO> listaUsuarios = entity2DTO(logica.getUsuarios());
        return listaUsuarios;
    }

    /**
     * retorna el usuario con id por parametro, null si no existe
     * @param usuariosId
     * @return usuario con id por parametro, null si no existe
     * @throws WebApplicationException 
     */
    @GET
    @Path("{usuariosId: \\d+}")
    public UsuarioDTO getUsuario(@PathParam("usuariosId") Long usuariosId) throws WebApplicationException {
        UsuarioEntity usuarioEntity = logica.getUsuario(usuariosId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDTO detailDTO = new UsuarioDTO(usuarioEntity);
        return detailDTO;
    }

    /**
     * elimina el usuario con id pasado por parametro
     * @param usuariosId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{usuariosId: \\d+}")
    public void deleteUsuario(@PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        if (logica.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        logica.deleteUsuario(usuariosId);
    }

    /**
     * actualiza usuario identificado por id dado por parametro
     * @param usuariosId
     * @param usuario
     * @return el usuario actualizado
     * @throws WebApplicationException 
     */
    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDTO updateUsuario(@PathParam("usuariosId") Long usuariosId, UsuarioDTO usuario) throws WebApplicationException {
        usuario.setId(usuariosId);
        if (logica.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDTO detailDTO = new UsuarioDTO(logica.updateUsuario(usuariosId, usuario.toEntity()));
        return detailDTO;

    }
    
    /**
     * convierte entidades en DTOs
     * @param entityList
     * @return lista de DTOs
     */
        private List<UsuarioDTO> entity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDTO(entity));
        }
        return list;
    }

}

package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.UsuarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class UsuarioLogic {

    /**
     * Atributo persistence creado
     */
    @Inject
    private UsuarioPersistence persistence;

    /**
     * crea un usuario
     * @param usuario
     * @return usuario creado
     */
    public UsuarioEntity createUsuario(UsuarioEntity usuario) throws BusinessLogicException {

        if (usuario.getUsuario() == "" || usuario.getUsuario() == null) {
            throw new BusinessLogicException("El nombre del usuario no puede ser nulo");
        }
        
        if ((persistence.findByName(usuario.getUsuario())) != null) {
            throw new BusinessLogicException("Ya existe un usuario con el nombre: " + usuario.getUsuario());
        }

        if(persistence.find(usuario.getId()) != null){
            throw new BusinessLogicException("El id del usuario ya esta siendo usado: "+usuario.getId());
        }
        
        usuario = persistence.create(usuario);
        return usuario;
    }

    /**
     * Obtener un usuario por medio de su id.
     *
     * @param usuarioId: id del usuario para ser buscado.
     * @return el usuario solicitado por medio de su id.
     */
    public UsuarioEntity getUsuario(Long usuarioId) {
        UsuarioEntity entidad = persistence.find(usuarioId);
        if (entidad == null) {
        return null;
        }
        return entidad;
    }

    /**
     * Actualizar un usuario
     *
     * @param usuarioId: id del usuario buscado
     * @param usuarioEntity: usuario con los cambios
     * @return el usuario con los cambios actualizados en la base de datos.
     */
    public UsuarioEntity updateUsuario(Long usuarioId, UsuarioEntity usuarioEntity) {
        UsuarioEntity newEntity = persistence.update(usuarioEntity);
        return newEntity;
    }

    /**
     * Borrar un usuario
     *
     * @param usuarioId: id del usuario a borrar
     * @throws BusinessLogicException
     */
    public void deleteUsuario(Long usuarioId) throws BusinessLogicException {
        persistence.delete(usuarioId);
    }

    /**
     *
     * retorna los usuarios existentes
     * @return una lista de usuarios.
     */
    public List<UsuarioEntity> getUsuarios() {
        List<UsuarioEntity> usuarios = persistence.findAll();
        return usuarios;
    }

}

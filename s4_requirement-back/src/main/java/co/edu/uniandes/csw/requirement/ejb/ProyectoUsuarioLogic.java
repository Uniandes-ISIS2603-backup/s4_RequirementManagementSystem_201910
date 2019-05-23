/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.UsuarioPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @proyecto Mateo Pedroza
 */
@Stateless
public class ProyectoUsuarioLogic {
    
    
    @Inject
    private UsuarioPersistence usuarioPersistence;

    @Inject
    private ProyectoPersistence proyectoPersistence;

    /**
     * Asocia un Usuario existente a un Proyecto
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @param usuariosId Identificador de la instancia de Usuario
     * @return Instancia de UsuarioEntity que fue asociada a Proyecto
     */
    public UsuarioEntity addUsuario(Long proyectosId, Long usuariosId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        usuarioEntity.getProyectos().add(proyectoEntity);
        return usuarioPersistence.find(usuariosId);
    }

    /**
     * Obtiene una colección de instancias de UsuarioEntity asociadas a una
     * instancia de Proyecto
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @return Colección de instancias de UsuarioEntity asociadas a la instancia de
     * Proyecto
     */
    public List<UsuarioEntity> getUsuarios(Long proyectosId) {
        return proyectoPersistence.find(proyectosId).getUsuarios();
    }

    /**
     * Obtiene una instancia de UsuarioEntity asociada a una instancia de Proyecto
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @param usuariosId Identificador de la instancia de Usuario
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public UsuarioEntity getUsuario(Long proyectosId, Long usuariosId) throws BusinessLogicException {
        List<UsuarioEntity> usuarios = proyectoPersistence.find(proyectosId).getUsuarios();
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        int index = usuarios.indexOf(usuarioEntity);
        if (index >= 0) {
            return usuarios.get(index);
        }
        throw new BusinessLogicException("La usuario no está asociado al proyecto");
    }

    /**
     * Remplaza las instancias de Usuario asociadas a una instancia de Proyecto
     *
     * @param proyectoId Identificador de la instancia de Proyecto
     * @param usuarios Colección de instancias de UsuarioEntity a asociar a instancia
     * de Proyecto
     * @return Nueva colección de UsuarioEntity asociada a la instancia de Proyecto
     */
    public List<UsuarioEntity> replaceUsuarios(Long proyectoId, List<UsuarioEntity> usuarios) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectoId);
        List<UsuarioEntity> usuarioList = usuarioPersistence.findAll();
        for (UsuarioEntity usuario : usuarioList) {
            if (usuarios.contains(usuario)) {
                if (!usuario.getProyectos().contains(proyectoEntity)) {
                    usuario.getProyectos().add(proyectoEntity);
                }
            } else {
                usuario.getProyectos().remove(proyectoEntity);
            }
        }
        proyectoEntity.setUsuarios(usuarios);
        return proyectoEntity.getUsuarios();
    }

    /**
     * Desasocia un Usuario existente de un Proyecto existente
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @param usuariosId Identificador de la instancia de Usuario
     */
    public void removeUsuario(Long proyectosId, Long usuariosId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        usuarioEntity.getProyectos().remove(proyectoEntity);
    }
}

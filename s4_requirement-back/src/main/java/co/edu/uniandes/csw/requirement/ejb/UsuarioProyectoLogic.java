/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.persistence.UsuarioPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @proyecto estudiante
 */
@Stateless
public class UsuarioProyectoLogic {

    @Inject
    private UsuarioPersistence usuarioPersistence;

    @Inject
    private ProyectoPersistence proyectoPersistence;

    /**
     * Asocia un Proyecto existente a un Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param proyectosId Identificador de la instancia de Proyecto
     * @return Instancia de ProyectoEntity que fue asociada a Usuario
     */
    public ProyectoEntity addProyecto(Long usuarioId, Long proyectosId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuarioId);
        usuarioEntity.getProyectos().add(proyectoEntity);
        return proyectoPersistence.find(proyectosId);
    }

    /**
     * Obtiene una colección de instancias de ProyectoEntity asociadas a una
     * instancia de Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @return Colección de instancias de ProyectoEntity asociadas a la
     * instancia de Usuario
     */
    public List<ProyectoEntity> getProyectos(Long usuarioId) {
        return usuarioPersistence.find(usuarioId).getProyectos();
    }

    /**
     * Obtiene una instancia de ProyectoEntity asociada a una instancia de
     * Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param proyectosId Identificador de la instancia de Proyecto
     * @return La entidad del Autor asociada al libro
     */
    public ProyectoEntity getProyecto(Long usuarioId, Long proyectosId) {
        List<ProyectoEntity> proyectos = usuarioPersistence.find(usuarioId).getProyectos();
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        int index = proyectos.indexOf(proyectoEntity);
        if (index >= 0) {
            return proyectos.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Proyecto asociadas a una instancia de
     * Usuario
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param list Colección de instancias de ProyectoEntity a asociar a
     * instancia de Usuario
     * @return Nueva colección de ProyectoEntity asociada a la instancia de
     * Usuario
     */
    public List<ProyectoEntity> replaceProyectos(Long usuarioId, List<ProyectoEntity> list) {
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuarioId);
        usuarioEntity.setProyectos(list);
        return usuarioPersistence.find(usuarioId).getProyectos();
    }

    /**
     * Desasocia un Proyecto existente de un Usuario existente
     *
     * @param usuarioId Identificador de la instancia de Usuario
     * @param proyectosId Identificador de la instancia de Proyecto
     */
    public void removeProyecto(Long usuarioId, Long proyectosId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuarioId);
        usuarioEntity.getProyectos().remove(proyectoEntity);
    }

}

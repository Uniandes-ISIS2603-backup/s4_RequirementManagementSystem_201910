/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @proyecto estudiante
 */
@Stateless
public class OrganizacionProyectoLogic {

    @Inject
    private OrganizacionPersistence organizacionPersistence;

    @Inject
    private ProyectoPersistence proyectoPersistence;

    /**
     * Asocia un Proyecto existente a un Organizacion
     *
     * @param organizacionId Identificador de la instancia de Organizacion
     * @param proyectosId Identificador de la instancia de Proyecto
     * @return Instancia de ProyectoEntity que fue asociada a Organizacion
     */
    public ProyectoEntity addProyecto(Long organizacionId, Long proyectosId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        OrganizacionEntity organizacionEntity = organizacionPersistence.find(organizacionId);
        organizacionEntity.getProyectos().add(proyectoEntity);
        return proyectoPersistence.find(proyectosId);
    }

    /**
     * Obtiene una colección de instancias de ProyectoEntity asociadas a una
     * instancia de Organizacion
     *
     * @param organizacionId Identificador de la instancia de Organizacion
     * @return Colección de instancias de ProyectoEntity asociadas a la instancia
     * de Organizacion
     */
    public List<ProyectoEntity> getProyectos(Long organizacionId) {
        return organizacionPersistence.find(organizacionId).getProyectos();
    }

    /**
     * Obtiene una instancia de ProyectoEntity asociada a una instancia de Organizacion
     *
     * @param organizacionId Identificador de la instancia de Organizacion
     * @param proyectosId Identificador de la instancia de Proyecto
     * @return La entidad del Autor asociada al libro
     */
    public ProyectoEntity getProyecto(Long organizacionId, Long proyectosId) {
        List<ProyectoEntity> proyectos = organizacionPersistence.find(organizacionId).getProyectos();
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        int index = proyectos.indexOf(proyectoEntity);
        if (index >= 0) {
            return proyectos.get(index);
        }
        return null;
    }

    /**
     * Remplaza las instancias de Proyecto asociadas a una instancia de Organizacion
     *
     * @param organizacionId Identificador de la instancia de Organizacion
     * @param list Colección de instancias de ProyectoEntity a asociar a instancia
     * de Organizacion
     * @return Nueva colección de ProyectoEntity asociada a la instancia de Organizacion
     */
    public List<ProyectoEntity> replaceProyectos(Long organizacionId, List<ProyectoEntity> list) {
        OrganizacionEntity organizacionEntity = organizacionPersistence.find(organizacionId);
        organizacionEntity.setProyectos(list);
        return organizacionPersistence.find(organizacionId).getProyectos();
    }

    /**
     * Desasocia un Proyecto existente de un Organizacion existente
     *
     * @param organizacionId Identificador de la instancia de Organizacion
     * @param proyectosId Identificador de la instancia de Proyecto
     */
    public void removeProyecto(Long organizacionId, Long proyectosId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        OrganizacionEntity organizacionEntity = organizacionPersistence.find(organizacionId);
        organizacionEntity.getProyectos().remove(proyectoEntity);
    }
    
}

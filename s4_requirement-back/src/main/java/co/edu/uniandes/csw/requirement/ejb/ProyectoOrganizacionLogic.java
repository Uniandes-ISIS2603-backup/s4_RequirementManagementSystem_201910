/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @proyecto Mateo Pedroza
 */
@Stateless
public class ProyectoOrganizacionLogic {
    
    
    @Inject
    private OrganizacionPersistence organizacionPersistence;

    @Inject
    private ProyectoPersistence proyectoPersistence;

    /**
     * Asocia un Organizacion existente a un Proyecto
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @param organizacionsId Identificador de la instancia de Organizacion
     * @return Instancia de OrganizacionEntity que fue asociada a Proyecto
     */
    public OrganizacionEntity addOrganizacion(Long proyectosId, Long organizacionsId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        OrganizacionEntity organizacionEntity = organizacionPersistence.find(organizacionsId);
        organizacionEntity.getProyectos().add(proyectoEntity);
        return organizacionPersistence.find(organizacionsId);
    }

    /**
     * Obtiene una colección de instancias de OrganizacionEntity asociadas a una
     * instancia de Proyecto
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @return Colección de instancias de OrganizacionEntity asociadas a la instancia de
     * Proyecto
     */
    public List<OrganizacionEntity> getOrganizaciones(Long proyectosId) {
        return proyectoPersistence.find(proyectosId).getOrganizaciones();
    }

    /**
     * Obtiene una instancia de OrganizacionEntity asociada a una instancia de Proyecto
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @param organizacionsId Identificador de la instancia de Organizacion
     * @return La entidadd de Libro del autor
     * @throws BusinessLogicException Si el libro no está asociado al autor
     */
    public OrganizacionEntity getOrganizacion(Long proyectosId, Long organizacionsId) throws BusinessLogicException {
        List<OrganizacionEntity> organizacions = proyectoPersistence.find(proyectosId).getOrganizaciones();
        OrganizacionEntity organizacionEntity = organizacionPersistence.find(organizacionsId);
        int index = organizacions.indexOf(organizacionEntity);
        if (index >= 0) {
            return organizacions.get(index);
        }
        throw new BusinessLogicException("La organizacion no está asociada al proyecto");
    }

    /**
     * Remplaza las instancias de Organizacion asociadas a una instancia de Proyecto
     *
     * @param proyectoId Identificador de la instancia de Proyecto
     * @param organizacions Colección de instancias de OrganizacionEntity a asociar a instancia
     * de Proyecto
     * @return Nueva colección de OrganizacionEntity asociada a la instancia de Proyecto
     */
    public List<OrganizacionEntity> replaceOrganizaciones(Long proyectoId, List<OrganizacionEntity> organizacions) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectoId);
        List<OrganizacionEntity> organizacionList = organizacionPersistence.findAll();
        for (OrganizacionEntity organizacion : organizacionList) {
            if (organizacions.contains(organizacion)) {
                if (!organizacion.getProyectos().contains(proyectoEntity)) {
                    organizacion.getProyectos().add(proyectoEntity);
                }
            } else {
                organizacion.getProyectos().remove(proyectoEntity);
            }
        }
        proyectoEntity.setOrganizaciones(organizacions);
        return proyectoEntity.getOrganizaciones();
    }

    /**
     * Desasocia un Organizacion existente de un Proyecto existente
     *
     * @param proyectosId Identificador de la instancia de Proyecto
     * @param organizacionsId Identificador de la instancia de Organizacion
     */
    public void removeOrganizacion(Long proyectosId, Long organizacionsId) {
        ProyectoEntity proyectoEntity = proyectoPersistence.find(proyectosId);
        OrganizacionEntity organizacionEntity = organizacionPersistence.find(organizacionsId);
        organizacionEntity.getProyectos().remove(proyectoEntity);
    }
}

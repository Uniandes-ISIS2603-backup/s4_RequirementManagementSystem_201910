/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Esguerra, David Manosalva
 */
@Stateless
public class ProyectoLogic {

    private static final Logger LOGGER = Logger.getLogger(ProyectoLogic.class.getName());
    @Inject
    private ProyectoPersistence persistence;

    /**
     * Método para crear un proyecto, validando sus reglas de negocio. Reglas
     * de negocio: 1. La estabilidad de un proyecto debe estar en el rango
     * [1,3], discreto. 2. La importancia de un proyecto debe estar en el rango
     * [1,3], discreto. 3. El tipo solo puede ser "FUNCIONAL" o "NOFUNCIONAL",
     * no es nullable. 4. La descripción no puede ser null, o vacía.
     *
     * Reglas de negocio a verificar en otras dos clases de lógica con las
     * asociaciones respectivas: 5. Solamente los proyectos con tipo FUNCIONAL
     * pueden tener casos de uso 6. El proyecto no puede ser nulo (proyecto es un
     * Stakeholder)
     *
     * @param x la entidad a crear
     * @return la entidad creada y persistida
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    public ProyectoEntity createProyecto(ProyectoEntity x) throws BusinessLogicException {
        // Aquí ponemos todas las validaciones que hay que hacer al momento de crear un nuevo proyecto, según reglas de negocio. 
        LOGGER.log(Level.INFO, "Inicia proceso de creación del proyecto");
        if (x.getDescripcion() == null || x.getDescripcion().equals("")) 
        {
            throw new BusinessLogicException("La descripcion no puede ser vacía o nula");
        }
        if (x.getNombre() == null || x.getNombre().equals("")) 
        {
            throw new BusinessLogicException("El nombre no puede ser vacía o nula");
        }

        LOGGER.log(Level.INFO, "Termina proceso de creación del proyecto");
        x = persistence.create(x);
        return x;
    }

    /**
     * Consulta de todos los proyectos guardados en el sistema.
     *
     * @return Lista con todos los proyectos en el sistema.
     */
    public List<ProyectoEntity> getProyectos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proyectos");
        List<ProyectoEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los proyectos");
        return lista;
    }

    /**
     * Consulta de un proyecto entity a partir de su identificador unico
     *
     * @param id, el identificador único del proyecto a consultar
     * @return el proyecto con dicho identificador.
     */
    public ProyectoEntity getProyecto(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proyecto con id = {0}", id);
        ProyectoEntity x = persistence.find(id);
        if (x == null) {
            LOGGER.log(Level.SEVERE, "El proyecto con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el proyecto con id = {0}", id);
        return x;
    }
    
    /**
     * Actualización de un proyecto entity identificado con el parámetro id.
     * @param id
     * @param x el proyecto
     * @return
     * @throws BusinessLogicException 
     */

    public ProyectoEntity updateProyecto(Long id, ProyectoEntity x) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el proyecto con id = {0}", id);
        
        if (x.getDescripcion() == null || x.getDescripcion().equals("")) {
            throw new BusinessLogicException("La descripcion no puede ser vacía o nula");
        }
        if (x.getNombre() == null || x.getNombre().equals("")) {
            throw new BusinessLogicException("La descripcion no puede ser vacía o nula");
        }

        ProyectoEntity y = persistence.update(x);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el proyecto con id = {0}", id);
        return y;
    }

    /**
     * Método para borrar un proyecto
     * @param id identificador del proyecto a borrar
     * @throws BusinessLogicException si el proyecto tiene objetivos asociados, porque se entiende que no ha sido completado.
     */
    public void deleteProyecto(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el proyecto con id = {0}", id);
        List<ObjetivoEntity> objetivos = getProyecto(id).getObjetivos();
        if (objetivos != null && !objetivos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el proyecto con id = " + id + " porque tiene objetivos asociados");
        }
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el proyecto con id = {0}", id);
    }

}

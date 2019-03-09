/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

/**
 *
 * @author Sofia Alvarez
 */
import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * DRS.
 *
 * @author Sofia Alvarez
 */
@Stateless
public class DRSLogic {

    private static final Logger LOGGER = Logger.getLogger(DRSLogic.class.getName());

    @Inject
    private DRSPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un DRS en la persistencia.
     *
     * @param drsEntity La entidad que representa el drs a
     * persistir.
     * @return La entiddad del drs luego de persistirla.
     * @throws BusinessLogicException Si la versión del drs a persistir ya existe.
     */
    public DRSEntity createDRS(DRSEntity drsEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la editorial");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (persistence.findByVersion(drsEntity.getVersion()) != null) {
            throw new BusinessLogicException("Ya existe un Camino con la version \"" + drsEntity.getVersion() + "\"");
        }
        // Invoca la persistencia para crear la editorial
        persistence.create(drsEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del camino");
        return drsEntity;
    }

    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<DRSEntity> getDRSs() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los caminos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<DRSEntity> drs = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los caminos");
        return drs;
    }

    /**
     *
     * Obtener una editorial por medio de su id.
     *
     * @param drsId: id de la editorial para ser buscada.
     * @return la editorial solicitada por medio de su id.
     */
    public DRSEntity getDRS(Long drsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el drs con id = {0}", drsId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        DRSEntity drsEntity = persistence.find(drsId);
        if (drsEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", drsId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la editorial con id = {0}", drsId);
        return drsEntity;
    }

    /**
     *
     * Actualizar una editorial.
     *
     * @param editorialsId: id de la editorial para buscarla en la base de
     * datos.
     * @param editorialEntity: editorial con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @return la editorial con los cambios actualizados en la base de datos.
     */
    public DRSEntity updateDRS(Long drsId, DRSEntity drsEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", drsId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        DRSEntity newEntity = persistence.update(drsEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", drsEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un editorial
     *
     * @param editorialsId: id de la editorial a borrar
     * @throws BusinessLogicException Si la editorial a eliminar tiene libros.
     */
    public void deleteDRS(Long drsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", drsId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
       
        persistence.delete(drsId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", drsId);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;


import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.entities.CaminoEntity.TipoCamino;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CaminoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Editorial.
 *
 * @author Sofia Alvarez
 */
@Stateless
public class CaminoLogic {

    private static final Logger LOGGER = Logger.getLogger(CaminoLogic.class.getName());

    @Inject
    private CaminoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una editorial en la persistencia.
     *
     * @param caminoEntity La entidad que representa la editorial a
     * persistir.
     * @return La entiddad de la editorial luego de persistirla.
     * @throws BusinessLogicException Si la editorial a persistir ya existe.
     */
    public CaminoEntity createCamino(CaminoEntity caminoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la editorial");
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (persistence.find(caminoEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe un Camino con el id \"" + caminoEntity.getId() + "\"");
        }
        if (caminoEntity.getTipoCamino() == null)
        {
           throw new BusinessLogicException("El tipo de camino no puede ser null");
        }
        if (!(caminoEntity.getTipoCamino().equals(TipoCamino.BASICO) || caminoEntity.getTipoCamino().equals(TipoCamino.ALTERNATIVO) || caminoEntity.getTipoCamino().equals(TipoCamino.EXCEPCION)) ){
            throw new BusinessLogicException("El tipo de camino sólo puede ser básico, de excepción o alternativo");
        }
        if(caminoEntity.getPasos().isEmpty()){
            throw new BusinessLogicException("Debe haber por lo menos un paso");
        }
        for (int i = 0; i < caminoEntity.getPasos().size(); i++) {
            for (int j = i+1; j < caminoEntity.getPasos().size(); j++) {
                if(caminoEntity.getPasos().get(i).equals(caminoEntity.getPasos().get(j)))
                {
                    throw new BusinessLogicException("No puede haber pasos repetidos");
                }
            }
        }
        // Invoca la persistencia para crear la editorial
        persistence.create(caminoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del camino");
        return caminoEntity;
    }

    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<CaminoEntity> getCaminos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los caminos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<CaminoEntity> editorials = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los caminos");
        return editorials;
    }

    /**
     *
     * Obtener una editorial por medio de su id.
     *
     * @param editorialsId: id de la editorial para ser buscada.
     * @return la editorial solicitada por medio de su id.
     */
    public CaminoEntity getCamino(Long caminoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el camino con id = {0}", caminoId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        CaminoEntity caminoEntity = persistence.find(caminoId);
        if (caminoEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", caminoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la editorial con id = {0}", caminoId);
        return caminoEntity;
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
    public CaminoEntity updateCamino(Long caminoId, CaminoEntity caminoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", caminoId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CaminoEntity newEntity = persistence.update(caminoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", caminoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un editorial
     *
     * @param editorialsId: id de la editorial a borrar
     * @throws BusinessLogicException Si la editorial a eliminar tiene libros.
     */
    public void deleteCamino(Long caminoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", caminoId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
       
        persistence.delete(caminoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", caminoId);
    }
}

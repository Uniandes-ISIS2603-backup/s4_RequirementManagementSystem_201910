/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;


import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CaminoPersistence;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
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
    private CaminoPersistence caminopersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private CasoDeUsoPersistence casopersistence;
    
    /**
     * Crea una editorial en la persistencia.
     *
     * @param caminoEntity La entidad que representa la editorial a
     * persistir.
     * @param requisitoId 
     * @param casoDeUsoId 
     * @return La entiddad de la editorial luego de persistirla.
     * @throws BusinessLogicException Si la editorial a persistir ya existe.
     */
    public CaminoEntity createCamino(Long requisitoId, Long casoDeUsoId, CaminoEntity caminoEntity) throws BusinessLogicException {
        // Verifica la regla de negocio que dice que no puede haber dos editoriales con el mismo nombre
        if (caminopersistence.find(casoDeUsoId, caminoEntity.getId()) != null) {
            throw new BusinessLogicException("Ya existe un Camino con el id \"" + caminoEntity.getId() + "\"");
        }
        if (caminoEntity.getTipoPaso() == null)
        {
           throw new BusinessLogicException("El tipo de camino no puede ser null");
        }
        if (!(caminoEntity.getTipoPaso().equals("BASICO") || caminoEntity.getTipoPaso().equals("ALTERNATIVO") || caminoEntity.getTipoPaso().equals("EXCEPCION")) ){
            throw new BusinessLogicException("El tipo de camino sólo puede ser básico, de excepción o alternativo");
        }
        // Invoca la persistencia para crear el camino
        CasoDeUsoEntity caso = casopersistence.find(requisitoId, casoDeUsoId);
        caminoEntity.setCasos(caso);
        LOGGER.log(Level.INFO, "Termina proceso de creación del objetivo");
        return caminopersistence.create(caminoEntity);
    }

    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *@param requisitoId 
     * @param casoDeUsoId 
     * @return una lista de editoriales.
     */
    public List<CaminoEntity> getCaminos(Long requisitoId, Long casoDeUsoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los caminos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        CasoDeUsoEntity caso = casopersistence.find(requisitoId, casoDeUsoId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los caminos");
        return caso.getCaminos();
    }

    /**
     *
     * Obtener una editorial por medio de su id.
     *
     * @param casoDeUsoId : id de la editorial para ser buscada.
     * @param caminoId
     * @return la editorial solicitada por medio de su id.
     */
    public CaminoEntity getCamino(Long casoDeUsoId, Long caminoId) {
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        CaminoEntity caminoEntity = caminopersistence.find(casoDeUsoId, caminoId);
        return caminoEntity;
    }

    /**
     *
     * Actualizar un camino.
     *
     * @param casoDeUsoId: id de la editorial para buscarla en la base de
     * datos.
     * @param caminoEntity: editorial con los cambios para ser actualizada,
     * por ejemplo el nombre.
     * @param requisitoId 
     * @return la editorial con los cambios actualizados en la base de datos.
     */
    public CaminoEntity updateCamino(Long requisitoId, Long casoDeUsoId, CaminoEntity caminoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la editorial con id = {0}", casoDeUsoId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        CasoDeUsoEntity caso = casopersistence.find(requisitoId, casoDeUsoId);
        caminoEntity.setCasos(caso);
        caminopersistence.update(caminoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la editorial con id = {0}", caminoEntity.getId());
        return caminoEntity;
    }

    /**
     * Borrar un editorial
     *@param casoDeUsoId
     * @param caminoId: id de la editorial a borrar
     * @throws BusinessLogicException Si la editorial a eliminar tiene libros.
     */
    public void deleteCamino(Long casoDeUsoId, Long caminoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", caminoId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        CaminoEntity camino = getCamino(casoDeUsoId, caminoId);
        if(camino == null)
        {
            throw new BusinessLogicException("La condicion con id = " + caminoId + " no esta asociado a el caso de uso con id = " + casoDeUsoId);
        }
        caminopersistence.delete(camino.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", caminoId);
    }
}

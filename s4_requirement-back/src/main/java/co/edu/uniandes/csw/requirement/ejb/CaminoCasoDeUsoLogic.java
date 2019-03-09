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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Sofia Sarmiento
 */
@Stateless
public class CaminoCasoDeUsoLogic {
    private static final Logger LOGGER = Logger.getLogger(CaminoCasoDeUsoLogic.class.getName());

    @Inject
    private CasoDeUsoPersistence casoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private CaminoPersistence caminoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un autor a un premio
     *
     * @param caminosId El id premio a guardar
     * @param casosId El id del autor al cual se le va a guardar el premio.
     * @return El premio que fue agregado al autor.
     */
    public CasoDeUsoEntity addCasoDeUso(Long casosId, Long caminosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el caso de uso con id = {0} al camino con id = " + caminosId, casosId);
        CasoDeUsoEntity casoEntity = casoPersistence.find(casosId);
        CaminoEntity caminoEntity = caminoPersistence.find(caminosId);
        caminoEntity.setCasos(casoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el caso con id = {0} al camino con id = " + caminosId, casosId);
        return casoPersistence.find(casosId);
    }

    /**
     *
     * Obtener un premio por medio de su id y el de su autor.
     *
     * @param caminosId id del premio a ser buscado.
     * @return el autor solicitada por medio de su id.
     */
    public CasoDeUsoEntity getCasoDeUso(Long caminosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el caso del camino con id = {0}", caminosId);
        CasoDeUsoEntity casoEntity = caminoPersistence.find(caminosId).getCasos();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor del premio con id = {0}", caminosId);
        return casoEntity;
    }

    /**
     * Borrar el autor de un premio
     *
     * @param caminosId El premio que se desea borrar del autor.
     * @throws BusinessLogicException si el premio no tiene autor
     */
    public void removeCasoDeUso(Long caminosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el caso del camino con id = {0}", caminosId);
        CaminoEntity caminoEntity = caminoPersistence.find(caminosId);
        if (caminoEntity.getCasos() == null) {
            throw new BusinessLogicException("El camino no tiene caso de uso");
        }
        CasoDeUsoEntity casoEntity = casoPersistence.find(caminoEntity.getCasos().getId());
        caminoEntity.setCasos(null);
        casoEntity.getCaminos().remove(caminoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el caso con id = {0} del camino con id = " + caminosId, casoEntity.getId());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Sofia Sarmiento
 */
@Stateless
public class CondicionCasoDeUsoLogic {
    @Inject
    private CasoDeUsoPersistence casoPersistence;
    @Inject
    private CondicionPersistence condicionPersistence;
    
    /**
     * Agregar un autor a un premio
     *
     * @param condicionesId El id premio a guardar
     * @param casosId El id del autor al cual se le va a guardar el premio.
     * @return El premio que fue agregado al autor.
     */
    public CasoDeUsoEntity addCasoDeUso(Long casosId, Long condicionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el caso de uso con id = {0} a la condicion con id = " + condicionesId, casosId);
        CasoDeUsoEntity casoEntity = casoPersistence.find(casosId);
        CondicionEntity condicionEntity = condicionPersistence.find(condicionesId);
        condicionEntity.setCasos(casoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el caso con id = {0} al camino con id = " + condicionesId, casosId);
        return casoPersistence.find(casosId);
    }

    /**
     *
     * Obtener un premio por medio de su id y el de su autor.
     *
     * @param condicionesId id del premio a ser buscado.
     * @return el autor solicitada por medio de su id.
     */
    public CasoDeUsoEntity getCasoDeUso(Long condicionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el caso de la condicion con id = {0}", condicionesId);
        CasoDeUsoEntity casoEntity = condicionPersistence.find(condicionesId).getCasos();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el caso de la condicion con id = {0}", condicionesId);
        return casoEntity;
    }

    /**
     * Borrar el autor de un premio
     *
     * @param condicionesId El premio que se desea borrar del autor.
     * @throws BusinessLogicException si el premio no tiene autor
     */
    public void removeCasoDeUso(Long condicionesId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el caso del camino con id = {0}", condicionesId);
        CondicionEntity condicionEntity = condicionPersistence.find(condicionesId);
        if (condicionEntity.getCasos() == null) {
            throw new BusinessLogicException("La condicion no tiene caso de uso");
        }
        CasoDeUsoEntity casoEntity = casoPersistence.find(condicionEntity.getCasos().getId());
        condicionEntity.setCasos(null);
        casoEntity.getCondiciones().remove(condicionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el caso con id = {0} del camino con id = " + condicionesId, casoEntity.getId());
    }
    
    
}

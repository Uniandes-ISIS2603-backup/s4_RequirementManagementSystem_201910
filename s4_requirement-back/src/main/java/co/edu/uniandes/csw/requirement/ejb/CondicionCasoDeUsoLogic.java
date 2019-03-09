/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CondicionPersistence;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
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
        CasoDeUsoEntity casoEntity = casoPersistence.find(casosId);
        CondicionEntity condicionEntity = condicionPersistence.find(condicionesId);
        condicionEntity.setCasos(casoEntity);
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
        CasoDeUsoEntity casoEntity = condicionPersistence.find(condicionesId).getCasos();
        return casoEntity;
    }

    /**
     * Borrar el autor de un premio
     *
     * @param condicionesId El premio que se desea borrar del autor.
     * @throws BusinessLogicException si el premio no tiene autor
     */
    public void removeCasoDeUso(Long condicionesId) throws BusinessLogicException {
        CondicionEntity condicionEntity = condicionPersistence.find(condicionesId);
        if (condicionEntity.getCasos() == null) {
            throw new BusinessLogicException("La condicion no tiene caso de uso");
        }
        CasoDeUsoEntity casoEntity = casoPersistence.find(condicionEntity.getCasos().getId());
        condicionEntity.setCasos(null);
        casoEntity.getCondiciones().remove(condicionEntity);
    }
    
    
}

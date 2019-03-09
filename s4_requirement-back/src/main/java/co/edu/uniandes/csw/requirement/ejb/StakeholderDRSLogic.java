/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Sofia Alvarez
 */
@Stateless
public class StakeholderDRSLogic {
     @Inject
    private StakeHolderPersistence stakeholderPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private DRSPersistence drsPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un autor a un premio
     *
     * @param requisitoId El id requisito a guardar
     * @param drsId El id del drs al cual se le va a guardar el requisito.
     * @return El requisito que fue agregado al drs.
     */
    public DRSEntity addDrs(Long drsId, Long requisitoId) {
        DRSEntity drsEntity = drsPersistence.find(drsId);
        StakeHolderEntity requisitoEntity = stakeholderPersistence.find(requisitoId);
        requisitoEntity.setDrs(drsEntity);
        return drsPersistence.find(drsId);
    }

    /**
     *
     * Obtener un requisito por medio de su id y el de su drs.
     *
     * @param drsId id del drs a ser buscado.
     * @return el drs solicitado por medio de su id.
     */
    public DRSEntity getDrs(Long drsId) {
       DRSEntity drsEntity = stakeholderPersistence.find(drsId).getDrs();
        return drsEntity;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Requisito y Drs
 * @author Sofia Alvarez
 */
@Stateless
public class RequisitoDrsLogic {
    
    @Inject
    private RequisitoPersistence requisitoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

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
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitoId);
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
       DRSEntity drsEntity = requisitoPersistence.find(drsId).getDrs();
        return drsEntity;
    }
    
    public void removeRequisitos(Long drsId) throws BusinessLogicException {
        DRSEntity de = drsPersistence.find(drsId);
        if (de.getObjetivos() == null || de.getObjetivos().isEmpty()) {
            throw new BusinessLogicException("El requisito no tiene drs");
        }
        List<RequisitoEntity> oe = requisitoPersistence.findAll();
        for(int i = 0; i < oe.size(); i++){
        de.getObjetivos().remove(i);
        }
    }
    
     public List<RequisitoEntity> replaceRequisitos(Long reqsId, Long drsId) {
        List<RequisitoEntity> reqEnt = requisitoPersistence.findAll();
        DRSEntity drsEntity = drsPersistence.find(drsId);
        drsEntity.setRequisitos(reqEnt);
        return requisitoPersistence.findAll();
    }

    
}

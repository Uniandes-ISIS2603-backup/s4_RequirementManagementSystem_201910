/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexión entre cambio y stakeholder
 * @author sofiaalvarez
 */
@Stateless
public class CambioStakeholderLogic {
    
     /**
     * Inyeccion de dependencias del cambio
     */
    @Inject
    private CambioPersistence cambioPersistence;

     /**
     * Inyeccion de dependencias del stakeholder
     */
    @Inject
    private StakeHolderPersistence stakeholderPersistence;

    /**
     * Añade la relacion con stakeholder de una aprobacion.
     *
     * @param camsId id del aprobacion que se quiere actualizar.
     * @param stakeholdersId El id de la stakeholder que se será del aprobacion.
     * @return El StakeHolder agregado a la relación
     */
    public StakeHolderEntity addStakeHolder(Long camsId, Long stakeholdersId) {
        StakeHolderEntity stakeholderEntity = stakeholderPersistence.find(stakeholdersId);
        CambioEntity cambioEntity = cambioPersistence.find(camsId);
        cambioEntity.setStakeholder(stakeholderEntity);
        
        
        return stakeholderEntity;
    }

    /**
     * Borrar un cambio de una stakeholder. Este metodo se utiliza para borrar la
     * relacion de un cambio.
     *
     * @param cId El cambio que se desea borrar de la stakeholder.
     */
    public void removeStakeHolder(Long cId) {
        CambioEntity cambioEntity = cambioPersistence.find(cId);
        StakeHolderEntity stakeholderEntity = stakeholderPersistence.find(cambioEntity.getStakeholder().getId());
        cambioEntity.setStakeholder(null);
       
    }
    
    /**
     * Obtener un stakeholder asociado a un cambio
     * @param cid de la aprobacion
     * @return el stakeholder asociado
     */
    
    public StakeHolderEntity getStakeholder(Long cid)     
    {
        CambioEntity cambioEntity = cambioPersistence.find(cid);
        return cambioEntity.getStakeholder();
    }
}

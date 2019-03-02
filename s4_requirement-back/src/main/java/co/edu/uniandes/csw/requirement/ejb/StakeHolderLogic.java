/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class StakeHolderLogic {

    /**
     * Atributo persistence creado
     */
    @Inject
    private StakeHolderPersistence persistence;

    /**
     *
     * @param stakeHolder
     * @return stakeholder creado
     */
    public StakeHolderEntity createStakeHolder(StakeHolderEntity stakeHolder) throws BusinessLogicException {

        if (stakeHolder.getNombre() == null) {
            throw new BusinessLogicException("El nombre del stakeholder no puede ser nulo");
        }

        stakeHolder = persistence.create(stakeHolder);
        return stakeHolder;
    }

    /**
     * Obtener una organizacion por medio de su id.
     *
     * @param stakeholderId: id de la organizacion para ser buscada.
     * @return la organizacion solicitada por medio de su id.
     */
    public StakeHolderEntity getStakeHolder(Long stakeholderId) {
        StakeHolderEntity organizacion = persistence.find(stakeholderId);
        if (organizacion == null) {
        }
        return organizacion;
    }

    /**
     * Actualizar una organizacion.
     *
     * @param stakeholderId: id de la organizacion para buscarla en la base de
     * datos.
     * @param stakeholderEntity: organizacion con los cambios para ser
     * actualizada, por ejemplo el nombre.
     * @return la organizacion con los cambios actualizados en la base de datos.
     */
    public StakeHolderEntity updateStakeHolder(Long stakeholderId, StakeHolderEntity stakeholderEntity) {
        StakeHolderEntity newEntity = persistence.update(stakeholderEntity);
        return newEntity;
    }

    /**
     * Borrar un organizacion
     *
     * @param organizacionsId: id de la organizacion a borrar
     * @throws BusinessLogicException si la organizacion tiene un premio
     * asociado.
     */
    public void deleteStakeHolder(Long organizacionsId) throws BusinessLogicException {
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        StakeHolderEntity organizacionEntity = persistence.find(organizacionsId);
        persistence.delete(organizacionsId);
    }

    /**
     *
     * Obtener todas las editoriales existentes en la base de datos.
     *
     * @return una lista de editoriales.
     */
    public List<StakeHolderEntity> getStakeHolders() {
        List<StakeHolderEntity> stakeholders = persistence.findAll();
        return stakeholders;
    }

}

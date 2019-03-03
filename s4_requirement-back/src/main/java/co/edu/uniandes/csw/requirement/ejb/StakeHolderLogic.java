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
     * crea un stakeholder
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
     * Obtener un stakeholder por medio de su id.
     *
     * @param stakeholderId: id del stakeholder para ser buscado.
     * @return el stakeholder solicitado por medio de su id.
     */
    public StakeHolderEntity getStakeHolder(Long stakeholderId) {
        StakeHolderEntity organizacion = persistence.find(stakeholderId);
        if (organizacion == null) {
        }
        return organizacion;
    }

    /**
     * Actualizar un stakeholder
     *
     * @param stakeholderId: id del stakeholder buscado
     * @param stakeholderEntity: stakeholder con los cambios
     * @return el stakeholder con los cambios actualizados en la base de datos.
     */
    public StakeHolderEntity updateStakeHolder(Long stakeholderId, StakeHolderEntity stakeholderEntity) {
        StakeHolderEntity newEntity = persistence.update(stakeholderEntity);
        return newEntity;
    }

    /**
     * Borrar un stakeholder
     *
     * @param stakeholderId: id del stakeholder a borrar
     * @throws BusinessLogicException
     */
    public void deleteStakeHolder(Long stakeholderId) throws BusinessLogicException {
        StakeHolderEntity organizacionEntity = persistence.find(stakeholderId);
        persistence.delete(stakeholderId);
    }

    /**
     *
     * retorna los stakeholders existentes
     *
     * @return una lista de stakeholders.
     */
    public List<StakeHolderEntity> getStakeHolders() {
        List<StakeHolderEntity> stakeholders = persistence.findAll();
        return stakeholders;
    }

}

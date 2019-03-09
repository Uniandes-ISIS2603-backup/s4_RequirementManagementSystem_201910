package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class OrganizacionLogic {

    /**
     * Atributo persistence creado
     */
    @Inject
    private OrganizacionPersistence persistence;

    /**
     *
     * @param organizacion
     * @return organizacion creada
     */
    public OrganizacionEntity createOrganizacion(OrganizacionEntity organizacion) throws BusinessLogicException {

        if ((persistence.findByName(organizacion.getNombre())) != null) {
            throw new BusinessLogicException("Ya existe una organizacion con el nombre: " + organizacion.getNombre());
        }

        if (persistence.find(organizacion.getId()) != null) {
            throw new BusinessLogicException("Ya existe una organizacion con ese id: " + organizacion.getId());
        }
        organizacion = persistence.create(organizacion);
        return organizacion;
    }

    /**
     * Obtener una organizacion por medio de su id.
     *
     * @param StakeHolderEntity: id de la organizacion para ser buscada.
     * @return la organizacion solicitada por medio de su id.
     */
    public OrganizacionEntity getOrganizacion(Long StakeHolderEntity) {
        OrganizacionEntity organizacion = persistence.find(StakeHolderEntity);
        if (organizacion == null) {
            return null;
        }
        return organizacion;
    }

    /**
     * Actualizar una organizacion.
     *
     * @param organizationsId: id de la organizacion para buscarla en la base de
     * datos.
     * @param organizationEntity: organizacion con los cambios
     * @return la organizacion con los cambios actualizados en la base de datos.
     */
    public OrganizacionEntity updateOrganizacion(Long organizationsId, OrganizacionEntity organizationEntity) {
        OrganizacionEntity newEntity = persistence.update(organizationEntity);
        return newEntity;
    }

    /**
     * Borrar un organizacion
     *
     * @param organizacionsId: id de la organizacion a borrar
     * @throws BusinessLogicException
     */
    public void deleteOrganizacion(Long organizacionsId) throws BusinessLogicException {
        OrganizacionEntity organizacionEntity = persistence.find(organizacionsId);
        persistence.delete(organizacionsId);
    }

    /**
     * Obtener todas las organizaciones existentes en la base de datos.
     *
     * @return una lista de organizaciones.
     */
    public List<OrganizacionEntity> getOrganizaciones() {
        List<OrganizacionEntity> organizaciones = persistence.findAll();
        return organizaciones;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mateo Pedroza
 */
@Stateless
public class OrganizacionStakeHolderLogic {
//
//    @Inject
//    private OrganizacionPersistence organizacionPersistence;
//
//    @Inject
//    private StakeHolderPersistence stakeholderPersistence;
//
//    /**
//     * Retorna todos los stakeholders asociados a una organizacion
//     *
//     * @param StakeHolderEntity
//     * @return lista de stakeholders
//     */
//    public List<StakeHolderEntity> getStakeHolders(Long StakeHolderEntity) {
//        return organizacionPersistence.find(StakeHolderEntity).getStakeHolders();
//    }
//
//    /**
//     * retorna el stakeholder perteneciente a la organizacion de interes
//     *
//     * @param EntidadId
//     * @param entidadId
//     * @return
//     * @throws BusinessLogicException
//     */
//    public StakeHolderEntity getStakeHolder(Long EntidadId, Long entidadId) throws BusinessLogicException {
//        List<StakeHolderEntity> entidades = organizacionPersistence.find(EntidadId).getStakeHolders();
//        StakeHolderEntity entidad = stakeholderPersistence.find(entidadId);
//        int index = entidades.indexOf(entidad);
//        if (index >= 0) {
//            return entidades.get(index);
//        }
//        throw new BusinessLogicException("El stakeholder no está asociado a la organizacion");
//    }
//    
//     /**
//     * Remplazar stakeholders de una organizacion
//     *
//     * @param stakeholders 
//     * @param organizacionId 
//     * @return 
//     */
//    public List<StakeHolderEntity> replaceStakeHolders(Long organizacionId, List<StakeHolderEntity> stakeholders) {
//        OrganizacionEntity OrganizacionEntity = organizacionPersistence.find(organizacionId);
//        List<StakeHolderEntity> stakeholderList = stakeholderPersistence.findAll();
//        for (StakeHolderEntity stakeholder : stakeholderList) {
//            if (stakeholders.contains(stakeholder)) {
//                stakeholder.setOrganizacion(OrganizacionEntity);
//            } else if (stakeholder.getOrganizacion() != null && stakeholder.getOrganizacion().equals(OrganizacionEntity)) {
//                stakeholder.setOrganizacion(null);
//            }
//        }
//        return stakeholders;
//    }
//    
//     /**
//     * Agregar un stakeholder a la organizacion
//     *
//     * @param stakeholdersId 
//     * @param organizacionId 
//     * @return
//     */
//    public StakeHolderEntity addStakeHolder(Long stakeholdersId, Long organizacionId) {
//        OrganizacionEntity OrganizacionEntity = organizacionPersistence.find(organizacionId);
//        StakeHolderEntity StakeHolderEntity = stakeholderPersistence.find(stakeholdersId);
//        StakeHolderEntity.setOrganizacion(OrganizacionEntity);
//        return StakeHolderEntity;
//    }
//    
}
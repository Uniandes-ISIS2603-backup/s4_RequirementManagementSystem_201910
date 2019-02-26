/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
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
    public StakeHolderEntity createStakeHolder (StakeHolderEntity stakeHolder) throws BusinessLogicException{
       
        if (stakeHolder.getNombre()== null){
            throw new BusinessLogicException("El nombre del stakeholder no puede ser nulo");
        }
        
        stakeHolder = persistence.create(stakeHolder);
        return stakeHolder;
    }
    
    
}

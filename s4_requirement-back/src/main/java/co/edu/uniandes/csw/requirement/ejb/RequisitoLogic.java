/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
/**
 *
 * @author jorgeandresesguerraalarcon
 */
@Stateless
public class RequisitoLogic 
{
    
    @Inject
    private RequisitoPersistence persistence;
    
    public RequisitoEntity createRequisito(RequisitoEntity x)
    {
        // Aquí ponemos todas las validaciones que hay que hacer al momento de crear un nuevo requisito, según reglas de negocio. 
        
        
        
        x = persistence.create(x);
        return x;
    }
    
}

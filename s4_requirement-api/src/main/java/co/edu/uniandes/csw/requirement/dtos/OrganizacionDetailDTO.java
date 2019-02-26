/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class OrganizacionDetailDTO extends OrganizacionDTO implements Serializable{
    
    //private List<>
    //relacion 1 a 1
    private List<StakeHolderDTO> stakeHolders;
    
    public OrganizacionDetailDTO(){
        super();   
        
    }
    
    public OrganizacionDetailDTO(OrganizacionEntity organizacionEntity){
        super(organizacionEntity);
        if(organizacionEntity != null){
            if(organizacionEntity.getStakeHolders()!=null){
                stakeHolders = new ArrayList<>();
                for(StakeHolderEntity entityOrg : organizacionEntity.getStakeHolders()){
                    stakeHolders.add(new StakeHolderDTO(entityOrg));
                }
            }
        }
    }
    
    public OrganizacionEntity toEntity(){
        OrganizacionEntity entidad = super.toEntity();
        
        if(stakeHolders != null){
            List<StakeHolderEntity> stakeHolderEntity = new ArrayList();
            for(StakeHolderDTO dtoStake : stakeHolders){
                stakeHolderEntity.add(dtoStake.toEntity());
            }
            
            entidad.setStakeHolders(stakeHolderEntity);
        }
        return entidad;
    }
    
}

package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateo Pedroza
 */
public class OrganizacionDetailDTO extends OrganizacionDTO implements Serializable{
    
    //private List<>
    //relacion 1 a 1
    private List<StakeHolderDTO> stakeHolders;
    
    public OrganizacionDetailDTO(){
        super();   
        
    }
    
    /**
     * Metodo constructor, agregar todas las relaciones que dependen de organizacion
     * @param organizacionEntity 
     */
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
    
    /**
     * Convierte el DTO en una entidad
     * @return entidad organizacion
     */
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

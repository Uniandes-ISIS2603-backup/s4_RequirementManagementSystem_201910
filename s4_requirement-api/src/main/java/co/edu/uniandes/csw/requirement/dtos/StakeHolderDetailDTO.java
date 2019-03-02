/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateo Pedroza
 */
public class StakeHolderDetailDTO extends StakeHolderDTO implements Serializable{
    //private List<>
    //relacion 1 a 1
    private List<CambioDTO> cambios;
    private List<AprobacionDTO> aprobaciones;
    
    public StakeHolderDetailDTO(){
        super();   
        
    }
    
    public StakeHolderDetailDTO(StakeHolderEntity stakeholderEntity){
        super(stakeholderEntity);
        if(stakeholderEntity != null){
            if(stakeholderEntity.getCambios()!=null){
                cambios = new ArrayList<>();
                for(CambioEntity cambio : stakeholderEntity.getCambios()){
                    cambios.add(new CambioDTO(cambio));
                }
            }
            if(stakeholderEntity.getAprobaciones()!=null){
                aprobaciones = new ArrayList<>();
                for(AprobacionEntity aprobacion : stakeholderEntity.getAprobaciones()){
                    aprobaciones.add(new AprobacionDTO(aprobacion));
                }
            }
            
        }
    }
    
    public StakeHolderEntity toEntity(){
        StakeHolderEntity entidad = super.toEntity();
        
        if(cambios != null){
            List<CambioEntity> cambioEntity = new ArrayList();
            for(CambioDTO dtoCambio : cambios){
                //CambioEntity.add(dtoCambio.toEntity());
            }
            
            entidad.setCambios(cambioEntity);
        }
        return entidad;
    }
    
}

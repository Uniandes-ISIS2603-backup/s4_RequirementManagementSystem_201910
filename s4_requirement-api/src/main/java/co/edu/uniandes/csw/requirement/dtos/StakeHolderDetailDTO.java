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

    private List<CambioDTO> cambios;
    private List<AprobacionDTO> aprobaciones;
    
    public StakeHolderDetailDTO(){
        super();   
        
    }
    /**
     * Metodo constructor, agregar todas las relaciones que dependen de stakeholder
     * @param stakeholderEntity 
     */
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
    
    /*
    Convierte el DTO en una entidad
    */
    @Override
    public StakeHolderEntity toEntity(){
        StakeHolderEntity entidad = super.toEntity();
        
        if(cambios != null){
            List<CambioEntity> cambioEntity = new ArrayList();
            for(CambioDTO dtoCambio : cambios){
                cambioEntity.add(dtoCambio.toEntity());
            }
            
            entidad.setCambios(cambioEntity);
        }
        
        if(aprobaciones != null){
            List<AprobacionEntity> aprobacionEntity = new ArrayList();
            for(AprobacionDTO dtoCambio : aprobaciones){
                aprobacionEntity.add(dtoCambio.toEntity());
            }
            
            entidad.setAprobaciones(aprobacionEntity);
        }
        return entidad;
    }
    
}
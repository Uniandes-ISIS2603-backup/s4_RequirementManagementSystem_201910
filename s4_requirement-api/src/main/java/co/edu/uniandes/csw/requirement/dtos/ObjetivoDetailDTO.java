/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estudiante
 */
public class ObjetivoDetailDTO extends ObjetivoDTO implements Serializable {

    private List<AprobacionDTO> aprobaciones;
    private List<CambioDTO> cambios;
    private List<RequisitoDTO> requisitos;
    private StakeHolderDTO autor;
    private List<StakeHolderDTO> fuentes;
    
    public ObjetivoDetailDTO() {

    }

    /*public ObjetivoDetailDTO(ObjetivoEntity objetivoEntity) {
        super(objetivoEntity);
        if (objetivoEntity != null) {
            aprobaciones = new ArrayList<>();
            for (AprobacionEntity aprobacionEntities : objetivoEntity.getAprobaciones()) {
                aprobaciones.add(new AprobacionDTO(aprobacionEntities));
            }
            cambios = new ArrayList<>();
            for (CambioEntity cambioEntities : objetivoEntity.getCambios()) {
                cambios.add(new CambioDTO(cambioEntities));
            }
            requisitos = new ArrayList<>();
            for (RequisitoEntity requisitoEntities : objetivoEntity.getRequisitos()) {
                requisitos.add(new RequisitoDTO(requisitoEntities));
            }
            
            autor = new StakeHolderEntity(objetivoEntity.getAutor());
            
            fuentes = new ArrayList<>();
            for (StakeHolderEntity stakeHolderEntities : objetivoEntity.getFuentes()) {
                fuentes.add(new StakeHolderEntity(stakeHolderEntities));
            }
        }
    }*/
}

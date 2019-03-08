/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.entities.CondicionEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sofia Sarmiento
 */
public class CasoDeUsoDetail extends CasoDeUsoDTO implements Serializable
{
    
 
    private List<CaminoDTO> caminos;
    private List<CondicionDTO> precondiciones;
    private List<CondicionDTO> postcondiciones;

    public CasoDeUsoDetail()
    {
        super();
    }
    
    public CasoDeUsoDetail (CasoDeUsoEntity casoDeUsoEntity)
    {
        super(casoDeUsoEntity);
        if(casoDeUsoEntity!=null)
        {
            caminos = new ArrayList<>();
            for (CaminoEntity actual : casoDeUsoEntity.getCaminos()) {
                caminos.add(new CaminoDTO(actual));
            }
            precondiciones = new ArrayList<>();
            for (CondicionEntity actual : casoDeUsoEntity.getPrecondiciones()) {
                precondiciones.add(new CondicionDTO(actual));
            }
            postcondiciones = new ArrayList<>();
            for (CondicionEntity actual : casoDeUsoEntity.getPrecondiciones()) {
                postcondiciones.add(new CondicionDTO(actual));
            }

        }

    }

    @Override
    public CasoDeUsoEntity toEntity() {
        CasoDeUsoEntity casoEntity = super.toEntity();
        if (caminos != null) {
            List<CaminoEntity> caminosEEntity = new ArrayList<>();
            for (CaminoDTO dtoCaminoE : caminos) {
                caminosEEntity.add(dtoCaminoE.toEntity());
            }
            casoEntity.setCaminos(caminosEEntity);
        }

        if (precondiciones != null) {
            List<CondicionEntity> condicionesPreEntity = new ArrayList<>();
            for (CondicionDTO dtoCondicionPre : precondiciones) {
                condicionesPreEntity.add(dtoCondicionPre.toEntity());
            }
            casoEntity.setPrecondiciones(condicionesPreEntity);
        }
        if (postcondiciones != null) {
            List<CondicionEntity> condicionesPosEntity = new ArrayList<>();
            for (CondicionDTO dtoCondicionPos : precondiciones) {
                condicionesPosEntity.add(dtoCondicionPos.toEntity());
            }
            casoEntity.setPrecondiciones(condicionesPosEntity);
        }
        return casoEntity;
    }
    
  

  
    /**
     * @return the caminos
     */
    public List<CaminoDTO> getCaminos() {
        return caminos;
    }

    /**
     * @param caminosExcepcion the caminos to set
     */
    public void setCaminosExcepcion(List<CaminoDTO> caminos) {
        this.caminos = caminos;
    }

 

    /**
     * @return the precondiciones
     */
    public List<CondicionDTO> getPrecondiciones() {
        return precondiciones;
    }

    /**
     * @param precondiciones the precondiciones to set
     */
    public void setPrecondiciones(List<CondicionDTO> precondiciones) {
        this.precondiciones = precondiciones;
    }

    /**
     * @return the postcondiciones
     */
    public List<CondicionDTO> getPostcondiciones() {
        return postcondiciones;
    }

    /**
     * @param postcondiciones the postcondiciones to set
     */
    public void setPostcondiciones(List<CondicionDTO> postcondiciones) {
        this.postcondiciones = postcondiciones;
    }

}
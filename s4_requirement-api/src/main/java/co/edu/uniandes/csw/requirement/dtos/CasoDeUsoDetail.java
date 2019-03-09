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
    private List<CondicionDTO> condiciones;

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
            condiciones = new ArrayList<>();
            for (CondicionEntity actual : casoDeUsoEntity.getCondiciones()) {
                condiciones.add(new CondicionDTO(actual));
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

        if (condiciones != null) {
            List<CondicionEntity> condicionesEntity = new ArrayList<>();
            for (CondicionDTO dtoCondicion : condiciones) {
                condicionesEntity.add(dtoCondicion.toEntity());
            }
            casoEntity.setCondiciones(condicionesEntity);
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
    public List<CondicionDTO> getCondiciones() {
        return condiciones;
    }

    /**
     * @param precondiciones the precondiciones to set
     */
    public void setCondiciones(List<CondicionDTO> condiciones) {
        this.condiciones = condiciones;
    }

}
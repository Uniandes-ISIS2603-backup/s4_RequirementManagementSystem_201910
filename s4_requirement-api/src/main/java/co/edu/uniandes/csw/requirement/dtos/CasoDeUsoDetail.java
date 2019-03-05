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
 * @author Sofia Sarmiento
 */
public class CasoDeUsoDetail extends CasoDeUsoDTO implements Serializable
{
    private CaminoDTO caminoBasico;
    private List<CaminoDTO> caminosExcepcion;
    private List<CaminoDTO> caminosAlternativos;
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
            if (casoDeUsoEntity.getCursoBasicoDeEventos() != null) {
            this.caminoBasico = new CaminoDTO(casoDeUsoEntity.getCursoBasicoDeEventos());
            }
            caminosExcepcion = new ArrayList<>();
            for (CaminoEntity actual : casoDeUsoEntity.getCaminosDeExcepcion()) {
                caminosExcepcion.add(new CaminoDTO(actual));
            }
            caminosAlternativos = new ArrayList<>();
            for (CaminoEntity actual : casoDeUsoEntity.getCaminosAlternativos()) {
                caminosAlternativos.add(new CaminoDTO(actual));
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
        if (getCaminoBasico()!= null) {
            casoEntity.setCursoBasicoDeEventos(getCaminoBasico().toEntity());
        }
        if (caminosExcepcion != null) {
            List<CaminoEntity> caminosEEntity = new ArrayList<>();
            for (CaminoDTO dtoCaminoE : caminosExcepcion) {
                caminosEEntity.add(dtoCaminoE.toEntity());
            }
            casoEntity.setCaminosDeExcepcion(caminosEEntity);
        }
        if (caminosAlternativos != null) {
            List<CaminoEntity> caminosAEntity = new ArrayList<>();
            for (CaminoDTO dtoCaminoA : caminosAlternativos) {
                caminosAEntity.add(dtoCaminoA.toEntity());
            }
            casoEntity.setCaminosDeExcepcion(caminosAEntity);
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
     * @return the caminoBasico
     */
    public CaminoDTO getCaminoBasico() {
        return caminoBasico;
    }

    /**
     * @param caminoBasico the caminoBasico to set
     */
    public void setCaminoBasico(CaminoDTO caminoBasico) {
        this.caminoBasico = caminoBasico;
    }

    /**
     * @return the caminosExcepcion
     */
    public List<CaminoDTO> getCaminosExcepcion() {
        return caminosExcepcion;
    }

    /**
     * @param caminosExcepcion the caminosExcepcion to set
     */
    public void setCaminosExcepcion(List<CaminoDTO> caminosExcepcion) {
        this.caminosExcepcion = caminosExcepcion;
    }

    /**
     * @return the caminosAlternativos
     */
    public List<CaminoDTO> getCaminosAlternativos() {
        return caminosAlternativos;
    }

    /**
     * @param caminosAlternativos the caminosAlternativos to set
     */
    public void setCaminosAlternativos(List<CaminoDTO> caminosAlternativos) {
        this.caminosAlternativos = caminosAlternativos;
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import java.io.Serializable;

/**
 *
 * @author Emilio
 */
public class AprobacionDTO implements Serializable{
    
    private Long id;
    
    private String tipo;
    
    private boolean aprobado;
    
    private String comentario;
    
    private StakeHolderDTO stakeholder;
    
    private ObjetivoDTO objetivo;
    
    private RequisitoDTO requisito;
    
     /**
     * Constructor vacio
     */
    public AprobacionDTO(){
        
    }
    
    public AprobacionDTO(AprobacionEntity entity){
        if(entity != null){
            this.id = entity.getId();
            this.tipo = entity.getTipo();
            this.aprobado = entity.isAprobado();
            this.comentario = entity.getComentario();
            //this.autor = new StakeHolderDTO(entity.getAutor());
            this.objetivo = new ObjetivoDTO(entity.getObjetivo());
            //this.requisito = new RequisitoDTO(entity.getRequisito());
        }
    }
    
    public AprobacionEntity toEntity(){
        AprobacionEntity entity = new AprobacionEntity();
        entity.setId(this.id);
        entity.setTipo(this.tipo);
        entity.setAprobado(this.aprobado);
        entity.setComentario(this.comentario);
        //entity.setAutor(this.autor.toEntity());
        entity.setObjetivo(this.objetivo.toEntity());
        //entity.setRequisito(this.requisito.toEntity());
        return entity;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the aprobado
     */
    public boolean isAprobado() {
        return aprobado;
    }

    /**
     * @param aprobado the aprovado to set
     */
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the aprobador
     */
    public StakeHolderDTO getStakeholder() {
        return stakeholder;
    }

    /**
     * @param aprobador the aprobador to set
     */
    public void setStakeholder(StakeHolderDTO aprobador) {
        this.stakeholder = aprobador;
    }

    /**
     * @return the objetivo
     */
    public ObjetivoDTO getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(ObjetivoDTO objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the requisito
     */
    public RequisitoDTO getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(RequisitoDTO requisito) {
        this.requisito = requisito;
    }
}

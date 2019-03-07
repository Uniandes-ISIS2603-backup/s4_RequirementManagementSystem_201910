/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Emilio
 */
public class CambioDTO implements Serializable {
    private Long id;
    
    private String tipo;
    
    private Date fechaYHora;
    
    private String descripcion;
    
    private StakeHolderDTO autor;
    
    private ObjetivoDTO objetivo;
    
    private RequisitoDTO requisito;
    
     /**
     * Constructor vacio
     */
    public CambioDTO(){
        
    }
    
    public CambioDTO(CambioEntity entity){
        if(entity != null){
            this.id = entity.getId();
            this.tipo = entity.getTipo();
            this.fechaYHora = entity.getFechaYHora();
            this.descripcion = entity.getDescripcion();
            //this.autor = new StakeHolderDTO(entity.getAutor());
            this.objetivo = new ObjetivoDTO(entity.getObjetivo());
            //this.requisito = new RequisitoDTO(entity.getRequisito());
        }
    }
    
    public CambioEntity toEntity(){
        CambioEntity entity = new CambioEntity();
        entity.setId(this.id);
        entity.setTipo(this.tipo);
        entity.setFechaYHora(this.fechaYHora);
        entity.setDescripcion(this.descripcion);
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
     * @return the fechaYHora
     */
    public Date getFechaYHora() {
        return fechaYHora;
    }

    /**
     * @param fechaYHora the fechaYHora to set
     */
    public void setFechaYHora(Date fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the autor
     */
    public StakeHolderDTO getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(StakeHolderDTO autor) {
        this.autor = autor;
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

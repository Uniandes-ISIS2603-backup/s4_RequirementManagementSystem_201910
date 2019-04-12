/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Clase que representa un Objetivo en DTO
 * @objetivo David Manosalva
 */
public class ObjetivoDTO implements Serializable {

    /**
     * Id del objetivo
     */
    private Long id;
    
    /**
     * Descripcion del objetivo
     */
    private String descripcion;
    
    /**
     * Importancia del objetivo
     */
    private Integer importancia;
    
    /**
     * Estabilidad del objetivo
     */
    private Integer estabilidad;
    
    /**
     * Comentarios del objetivo
     */
    private String comentarios;

    /**
     * Autor del objetivo
     */
    private StakeHolderDTO autor;

    /**
     * Cosntruictor vacio para REST
     */
    public ObjetivoDTO() {

    }

    /**
     * Constructor que crea un DTO de un objeto entity
     */
    public ObjetivoDTO(ObjetivoEntity oe) {

        if (oe != null) {
            this.id = oe.getId();
            this.descripcion = oe.getDescripcion();
            this.importancia = oe.getImportancia();
            this.estabilidad = oe.getEstabilidad();
            this.comentarios = oe.getComentarios();
            if (oe.getAutor() != null) {
                this.autor = new StakeHolderDTO(oe.getAutor());
            } else {
                oe.setAutor(null);
            }
            
        }
    }

    /**
     * Metodo que crea un ObjetivoEntity a partir de este DTO
     * @return ObjetivoEntity con la informacion del DTO
     */
    public ObjetivoEntity toEntity() {
        ObjetivoEntity objetivoEntity = new ObjetivoEntity();
        objetivoEntity.setId(this.getId());
        objetivoEntity.setDescripcion(this.descripcion);
        objetivoEntity.setImportancia(this.importancia);
        objetivoEntity.setComentarios(this.comentarios);
        objetivoEntity.setEstabilidad(this.estabilidad);
        if (autor != null) {
            objetivoEntity.setAutor(autor.toEntity());
        }
        return objetivoEntity;
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
     * @return the importancia
     */
    public Integer getImportancia() {
        return importancia;
    }

    /**
     * @param importancia the importancia to set
     */
    public void setImportancia(Integer importancia) {
        this.importancia = importancia;
    }

    /**
     * @return the estabilidad
     */
    public Integer getEstabilidad() {
        return estabilidad;
    }

    /**
     * @param estabilidad the estabilidad to set
     */
    public void setEstabilidad(Integer estabilidad) {
        this.estabilidad = estabilidad;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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
     * Metodo que devuelve este objeto como un string
     * @return String en tipo JSON
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

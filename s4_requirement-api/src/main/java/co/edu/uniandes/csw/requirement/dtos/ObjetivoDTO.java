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
 *
 * @objetivo David Manosalva
 */
public class ObjetivoDTO implements Serializable {

    private Long id;
    private String descripcion;
    private Integer importancia;
    private Integer estabilidad;
    private String comentarios;

    public ObjetivoDTO() {

    }
    
    public ObjetivoDTO(ObjetivoEntity oe)
    {
        if(oe != null)
        {
            this.id = oe.getId();
            this.descripcion = oe.getDescripcion();
            this.importancia = oe.getImportancia();
            this.estabilidad = oe.getEstabilidad();
            this.comentarios = oe.getComentarios();
        }
    }
    
    public ObjetivoEntity toEntity() {
        ObjetivoEntity objetivoEntity = new ObjetivoEntity();
        objetivoEntity.setId(this.getId());
        objetivoEntity.setDescripcion(this.descripcion);
        objetivoEntity.setImportancia(this.importancia);
        objetivoEntity.setComentarios(this.comentarios);
        objetivoEntity.setEstabilidad(this.estabilidad);
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

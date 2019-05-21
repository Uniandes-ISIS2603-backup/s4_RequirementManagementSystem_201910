/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Representa el recurso requisito, en su versión básica.
 *
 * @author Jorge Esguerra
 */
public class RequisitoDTO implements Serializable {

    private String tipo, descripcion, comentarios;
    private Long id;
    private Integer importancia, estabilidad;

    private UsuarioDTO autor;

    private ObjetivoDTO objetivo;

    public RequisitoDTO() {

    }

    public RequisitoDTO(RequisitoEntity re) {
        if (re != null) {
            this.id = re.getId();
            this.descripcion = re.getDescripcion();
            this.importancia = re.getImportancia();
            this.estabilidad = re.getEstabilidad();
            this.comentarios = re.getComentarios();
            this.tipo = re.getTipo();
            if (re.getObjetivo() != null) {
                this.objetivo = new ObjetivoDTO(re.getObjetivo());
            } else {
                re.setAutor(null);
            }
            if (re.getAutor() != null) {
                this.autor = new UsuarioDTO(re.getAutor());
            } else {
                re.setAutor(null);
            }
        }
    }

    public RequisitoEntity toEntity() {
        RequisitoEntity requisitoEntity = new RequisitoEntity();
        requisitoEntity.setId(this.getId());
        requisitoEntity.setDescripcion(this.descripcion);
        requisitoEntity.setImportancia(this.importancia);
        requisitoEntity.setComentarios(this.comentarios);
        requisitoEntity.setEstabilidad(this.estabilidad);
        requisitoEntity.setTipo(this.tipo);
        if (getAutor() != null) {
            requisitoEntity.setAutor(getAutor().toEntity());
        }
        if (getObjetivo() != null) {
            requisitoEntity.setObjetivo(getObjetivo().toEntity());
        }
        return requisitoEntity;
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
     * @return the autor
     */
    public UsuarioDTO getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(UsuarioDTO autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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

}

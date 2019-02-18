/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;

/**
 *
 * @author estudiante
 */
@Entity
// La tabla en la base de datos se llama RequisitoEntity, porque no se usa anotación para cambiarle el nombre. 
// Lo mismo pasa con los atributos, pues tampoco son anotados. 
public class RequisitoEntity extends BaseEntity implements Serializable {

    private String autor, comentarios, descripcion, tipo;
    private int importancia, estabilidad;

    public RequisitoEntity() {

    }

    
    @OneToMany(mappedBy= "requisito")
    private List<AprobacionEntity> aprobaciones  = new ArrayList<AprobacionEntity>();
    
    @OneToMany(mappedBy="requisito")
    private List<CambioEntity> cambios = new ArrayList<CambioEntity>();
    
    @ManyToMany(mappedBy="requisito")
    private List<ObjetivoEntity> objetivos;
    
    @OneToMany(mappedBy="requisito")
    private List<CasoDeUsoEntity> casosDeUso;
    
    @ManyToOne
    private List<DRSEntity> drs;
    
    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
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
     * @return the importancia
     */
    public int getImportancia() {
        return importancia;
    }

    /**
     * @param importancia the importancia to set
     */
    public void setImportancia(int importancia) {
        this.importancia = importancia;
    }

    /**
     * @return the estabilidad
     */
    public int getEstabilidad() {
        return estabilidad;
    }

    /**
     * @param estabilidad the estabilidad to set
     */
    public void setEstabilidad(int estabilidad) {
        this.estabilidad = estabilidad;
    }
}

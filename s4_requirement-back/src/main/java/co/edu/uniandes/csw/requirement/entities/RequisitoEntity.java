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
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Jorge A Esguerra A
 */
@Entity
// La tabla en la base de datos se llama RequisitoEntity, porque no se usa anotación para cambiarle el nombre. 
// Lo mismo pasa con los atributos, pues tampoco son anotados. 
public class RequisitoEntity extends BaseEntity implements Serializable {

    private String comentarios, descripcion, tipo;
    private int importancia, estabilidad;

    public RequisitoEntity()
    {

    }
    
    @PodamExclude
    @OneToMany(mappedBy= "requisito", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AprobacionEntity> aprobaciones  = new ArrayList<AprobacionEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy="requisito", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CambioEntity> cambios = new ArrayList<CambioEntity>();
    
    @PodamExclude
    @ManyToOne
    private ObjetivoEntity objetivo;
    
    @PodamExclude
    @OneToMany(mappedBy="requisito", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CasoDeUsoEntity> casosDeUso;
    
    @PodamExclude
    @OneToOne(mappedBy = "autorRequisito", fetch = FetchType.LAZY)
    private UsuarioEntity autor;
    
    @PodamExclude
    @OneToMany(mappedBy = "fuenteRequisito", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<StakeHolderEntity> fuentes = new ArrayList<>();
    
   
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

    /**
     * @return the aprobaciones
     */
    public List<AprobacionEntity> getAprobaciones() {
        return aprobaciones;
    }

    /**
     * @param aprobaciones the aprobaciones to set
     */
    public void setAprobaciones(List<AprobacionEntity> aprobaciones) {
        this.aprobaciones = aprobaciones;
    }

    /**
     * @return the cambios
     */
    public List<CambioEntity> getCambios() {
        return cambios;
    }

    /**
     * @param cambios the cambios to set
     */
    public void setCambios(List<CambioEntity> cambios) {
        this.cambios = cambios;
    }

    /**
     * @return the casosDeUso
     */
    public List<CasoDeUsoEntity> getCasosDeUso() {
        return casosDeUso;
    }

    /**
     * @param casosDeUso the casosDeUso to set
     */
    public void setCasosDeUso(List<CasoDeUsoEntity> casosDeUso) {
        this.casosDeUso = casosDeUso;
    }

    /**
     * @return the autor
     */
    public UsuarioEntity getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(UsuarioEntity autor) {
        this.autor = autor;
    }

    /**
     * @return the fuentes
     */
    public List<StakeHolderEntity> getFuentes() {
        return fuentes;
    }

    /**
     * @param fuentes the fuentes to set
     */
    public void setFuentes(List<StakeHolderEntity> fuentes) {
        this.fuentes = fuentes;
    }

    /**
     * @return the objetivo
     */
    public ObjetivoEntity getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(ObjetivoEntity objetivo) {
        this.objetivo = objetivo;
    }
}

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
import javax.persistence.criteria.Fetch;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Sofia Sarmiento
 */
@Entity
public class CasoDeUsoEntity extends BaseEntity implements Serializable {

    private String nombre;

    @PodamExclude
    @ManyToOne
    private RequisitoEntity requisito;

    @PodamExclude
    @OneToOne(mappedBy="casoCursoBasicoDeEventos", fetch = FetchType.LAZY)
    private CaminoEntity cursoBasicoDeEventos;

    @PodamExclude
    @OneToMany(mappedBy = "casoCaminosDeExcepcion", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CaminoEntity> caminosDeExcepcion = new ArrayList<CaminoEntity>();

    @PodamExclude
    @OneToMany(mappedBy = "casoCaminosAlternativos", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CaminoEntity> caminosAlternativos = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "casoPrecondiciones", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CondicionEntity> precondiciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "casoPostcondiciones", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CondicionEntity> postcondiciones = new ArrayList<>();

    public CasoDeUsoEntity() {

    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
        /**
     * @return the requisito
     */
    public RequisitoEntity getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(RequisitoEntity requisito) {
        this.requisito = requisito;
    }

    /**
     * @return the cursoBasicoDeEventos
     */
    public CaminoEntity getCursoBasicoDeEventos() {
        return cursoBasicoDeEventos;
    }

    /**
     * @param cursoBasicoDeEventos the cursoBasicoDeEventos to set
     */
    public void setCursoBasicoDeEventos(CaminoEntity cursoBasicoDeEventos) {
        this.cursoBasicoDeEventos = cursoBasicoDeEventos;
    }

    /**
     * @return the caminosDeExcepcion
     */
    public List<CaminoEntity> getCaminosDeExcepcion() {
        return caminosDeExcepcion;
    }

    /**
     * @param caminosDeExcepcion the caminosDeExcepcion to set
     */
    public void setCaminosDeExcepcion(List<CaminoEntity> caminosDeExcepcion) {
        this.caminosDeExcepcion = caminosDeExcepcion;
    }

    /**
     * @return the caminosAlternativos
     */
    public List<CaminoEntity> getCaminosAlternativos() {
        return caminosAlternativos;
    }

    /**
     * @param caminosAlternativos the caminosAlternativos to set
     */
    public void setCaminosAlternativos(List<CaminoEntity> caminosAlternativos) {
        this.caminosAlternativos = caminosAlternativos;
    }

    /**
     * @return the precondiciones
     */
    public List<CondicionEntity> getPrecondiciones() {
        return precondiciones;
    }

    /**
     * @param precondiciones the precondiciones to set
     */
    public void setPrecondiciones(List<CondicionEntity> precondiciones) {
        this.precondiciones = precondiciones;
    }

    /**
     * @return the postcondiciones
     */
    public List<CondicionEntity> getPostcondiciones() {
        return postcondiciones;
    }

    /**
     * @param postcondiciones the postcondiciones to set
     */
    public void setPostcondiciones(List<CondicionEntity> postcondiciones) {
        this.postcondiciones = postcondiciones;
    }


}

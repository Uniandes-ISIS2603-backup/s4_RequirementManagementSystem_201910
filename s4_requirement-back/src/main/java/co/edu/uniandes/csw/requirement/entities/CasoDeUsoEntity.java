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
    @OneToMany(mappedBy = "caminos", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CaminoEntity> caminos = new ArrayList<CaminoEntity>();

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

    /**
     * @return the caminos
     */
    public List<CaminoEntity> getCaminos() {
        return caminos;
    }
    
     /**
     * @return the caminos
     */
    public void setCaminos(List<CaminoEntity> caminitos) {
       this.caminos = caminitos;
    }

}

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
    @OneToMany(mappedBy = "casos", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CaminoEntity> caminos = new ArrayList<CaminoEntity>();

    @PodamExclude
    @OneToMany(mappedBy = "casos", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CondicionEntity> condiciones = new ArrayList<>();

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

    /**
     * @return the condiciones
     */
    public List<CondicionEntity> getCondiciones() {
        return condiciones;
    }

    /**
     * @param condiciones the condiciones to set
     */
    public void setCondiciones(List<CondicionEntity> condiciones) {
        this.condiciones = condiciones;
    }

}

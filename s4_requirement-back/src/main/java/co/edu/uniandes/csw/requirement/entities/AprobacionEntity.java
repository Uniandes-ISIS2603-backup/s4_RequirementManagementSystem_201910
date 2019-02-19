/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Emilio
 */
@Entity
public class AprobacionEntity extends BaseEntity implements Serializable {

    private String tipo;

    private boolean aprobado;

    private String comentario;

    @PodamExclude
    @ManyToOne
    private StakeHolderEntity aprobador;

    @PodamExclude
    @ManyToOne
    private ObjetivoEntity objetivo;

    @PodamExclude
    @ManyToOne
    private RequisitoEntity requisito;

    /**
     * Consrtructor vacío AprobaciónEntity
     */
    public AprobacionEntity() {

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
     * @param aprobado the aprobado to set
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

}

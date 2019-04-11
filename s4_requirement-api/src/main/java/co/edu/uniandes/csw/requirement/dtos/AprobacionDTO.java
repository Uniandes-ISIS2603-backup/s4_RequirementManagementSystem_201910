/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import java.io.Serializable;

/**
 * Data Transfer Object (DTO) de una Aprobacion
 * @author Sofia Alvarez
 */
public class AprobacionDTO implements Serializable{
    /**
     * id de la aprobacion
     */
    private Long id;
    
    /**
     * Tipo de la aprobacion. Puede ser "OBJETIVO" o requisito
     */
    private String tipo;
    
    /**
     * Autor de la aprobacion
     */
    private String autor;
    
    /**
     * La organizacion del autor
     */
    private String organizacion;
    
    /**
     * Fecha y hora de la aprobacion
     */
    private String fechaYHora;
    
    /**
     * id del req/obj aprobado
     */
    private String id_aprobado;
    
    /**
     * nombre del req/obj aprobado
     */
    private String nombre_aprobado;
    
    /**
     * Estado de la aprobación. Puede ser: "Aprobado", "No aprobado" o "En revisión"
     */
    private String estado;
    
    /**
     * Comentario sobre la aprobacion.
     */
    private String comentario;
    
    /**
     * Data Transfer Object del stakeholder.
     */
    private StakeHolderDTO stakeholder;
    
    /**
     * Data Transfer Object del stakeholder.
     */
    private ObjetivoDTO objetivo;
    
    /**
     * Data Transfer Object del stakeholder.
     */
    private RequisitoDTO requisito;
    
     /**
     * Constructor vacio
     */
    public AprobacionDTO(){
        
    }
    
    /**
     * Construye un DTO de aprobacion con la entity recibida por parametro
     * @param entity 
     */
    public AprobacionDTO(AprobacionEntity entity){
        if(entity != null){
            this.id = entity.getId();
            this.tipo = entity.getTipo();
            this.autor = entity.getAutor();
            this.organizacion = entity.getOrganizacion();
            this.fechaYHora = entity.getFechaYHora();
            this.estado = entity.getEstado();
            this.comentario = entity.getComentario();
            this.id_aprobado = entity.getId_aprobado();
            this.nombre_aprobado = entity.getNombre_aprobado();
        }
    }
    /**
     * Metodo toEntity de una aprobacion
     * @return  vuelve un objetivo DTO en una entidad.
     */
    public AprobacionEntity toEntity(){
        AprobacionEntity entity = new AprobacionEntity();
        entity.setId(this.getId());
        entity.setTipo(this.getTipo());
        entity.setAutor(this.getAutor());
        entity.setOrganizacion(this.getOrganizacion());
        entity.setFechaYHora(this.getFechaYHora());
        entity.setEstado(this.getEstado());
        entity.setComentario(this.getComentario());
        entity.setId_aprobado(this.getId_aprobado());
        entity.setNombre_aprobado(this.getNombre_aprobado());
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

    /**
     * @return the aprobador
     */
    public StakeHolderDTO getStakeholder() {
        return stakeholder;
    }

    /**
     * @param aprobador the aprobador to set
     */
    public void setStakeholder(StakeHolderDTO aprobador) {
        this.stakeholder = aprobador;
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
     * @return the organizacion
     */
    public String getOrganizacion() {
        return organizacion;
    }

    /**
     * @param organizacion the organizacion to set
     */
    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    /**
     * @return the fechaYHora
     */
    public String getFechaYHora() {
        return fechaYHora;
    }

    /**
     * @param fechaYHora the fechaYHora to set
     */
    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    /**
     * @return the id_aprobado
     */
    public String getId_aprobado() {
        return id_aprobado;
    }

    /**
     * @param id_aprobado the id_aprobado to set
     */
    public void setId_aprobado(String id_aprobado) {
        this.id_aprobado = id_aprobado;
    }

    /**
     * @return the nombre_aprobado
     */
    public String getNombre_aprobado() {
        return nombre_aprobado;
    }

    /**
     * @param nombre_aprobado the nombre_aprobado to set
     */
    public void setNombre_aprobado(String nombre_aprobado) {
        this.nombre_aprobado = nombre_aprobado;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

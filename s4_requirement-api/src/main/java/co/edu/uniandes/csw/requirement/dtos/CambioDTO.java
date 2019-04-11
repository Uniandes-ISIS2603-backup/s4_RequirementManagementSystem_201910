/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import java.io.Serializable;

/**
 *Esta clase representa un data transfer object para un cambio
 * @author Sofia Alvarez
 */
public class CambioDTO implements Serializable {
    /**
     * Id del cambio.
     */
    private Long id;
    /**
     * Tipo del cambio.
     * Puede ser: "Aprobación", "Modificación" o "Eliminación"
     */
    private String tipo;
    
    /**
     * Autor del cambio.
     */
    private String autor;
    
    /**
     * Organizacion del autor
     */
    private String organizacion;
    
    /**
     * Fecha y hota en que se realiza el cambio
     */
    private String fechaYHora;
    
    /**
     * Descripcion del cambio
     */
    private String descripcion;
    
    /**
     * Id del req/obj sobre el cual se hace el cambio
     */
    private String id_aprobado;
    
    /**
     * Nombre del req/obj  sobre el cual se hace el cambio
     */
    private String nombre_aprobado;
    
   /**
    * Data transfer object del stakeholder
    */
    private StakeHolderDTO stakeholder;
    
    /**
     * Data transfer object del objetivo
     */
    private ObjetivoDTO objetivo;
    
    /**
     * Data transfer object del requisito
     */
    private RequisitoDTO requisito;
    
     /**
     * Constructor vacio
     */
    public CambioDTO(){
        
    }
    
    /**
     * Crea un Data transfer object (DTO) de cambio a partir de una entity de aprobacion
     * @param entity de aprobacion con la cual se creará el DTO
     */
    public CambioDTO(CambioEntity entity){
        if(entity != null){
            this.id = entity.getId();
            this.tipo = entity.getTipo();
            this.autor = entity.getAutor();
            this.organizacion = entity.getOrganizacion();
            this.fechaYHora = entity.getFechaYHora();
            this.descripcion = entity.getDescripcion();
            this.id_aprobado = entity.getId_aprobado();
            this.nombre_aprobado = entity.getNombre_aprobado();
        }
    }
    /**
     * Convierte un DTO de aprobacion en una entity de aprobacion
     * @return entity de aprobacion construida a partir del DTO
     */
    public CambioEntity toEntity(){
        CambioEntity entity = new CambioEntity();
        entity.setId(this.getId());
        entity.setTipo(this.getTipo());
        entity.setAutor(this.getAutor());
        entity.setOrganizacion(this.getOrganizacion());
        entity.setFechaYHora(this.getFechaYHora());
        entity.setDescripcion(this.getDescripcion());
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
     * @return the autor
     */
    public StakeHolderDTO getStakeholder() {
        return stakeholder;
    }

    /**
     * @param stakeholder the stakeholder to set
     */
    public void setStakeholder(StakeHolderDTO stakeholder) {
        this.stakeholder = stakeholder;
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
}

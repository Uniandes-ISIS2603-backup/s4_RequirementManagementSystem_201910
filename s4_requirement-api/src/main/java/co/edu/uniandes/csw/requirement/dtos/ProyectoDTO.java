/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import java.io.Serializable;

/**
 *
 * @author Sofia Alvarez, Jorge Esguerra, David Manosalva
 */
public class ProyectoDTO implements Serializable {
    /**
     * Id del Proyecto
     */
    private Long id;
    
    /**
     * Nombre del Proyecto
     */
    private String nombre;
    
    /**
     * Descripcion del Proyecto
     */
    private String descripcion;
    
    /**
     * Constructor por defecto 
     */
    public ProyectoDTO()
    {
        
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     * @param entity Es la entidad que se va a convertir a DTO
     */
    public Proyecto(ProyectoEntity entity)
    {
        if(entity != null)
        {
            this.setId(entity.getId());
            this.setNombre(entity.getNombre());
            this.setDescripcion(entity.getDescripcion());
        }
    }
    /**
     * Convertir Proyecto a Entity
     * @return Un entity con los valores del DTO
     */
    public ProyectoEntity toEntity() {
        ProyectoEntity proyectoEntity = new ProyectoEntity();
        proyectoEntity.setId(this.getId());
        proyectoEntity.setNombre(this.getNombre());
        proyectoEntity.setDescripcion(this.getDescripcion());
        return proyectoEntity;
    }
    /**
     * El id del Proyecto
     * @return el id del Proyecto
     */
    public Long getId(){
        return id;
    }
    /**
     * Modifica el id del Proyecto
     * @param pId el id del Proyecto
     */
    public void setId(Long pId){
        id = pId;
    }
    
    /**
     * Nombre del proyecto
     * @return el Nombre del proyecto
     */
     public String getNombre(){
        return nombre;
    }
    /**
     * Modifica el nombre del proyecto
     * @param pReporte el reporte por el que se quiere modificar
     */
    public void setNombre(String pNombre){
        nombre = pNombre;
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
    
    
}

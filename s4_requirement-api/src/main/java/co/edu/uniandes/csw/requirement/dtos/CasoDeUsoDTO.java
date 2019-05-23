/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import java.io.Serializable;

/**
 *
 * @author Sofia Sarmiento
 */
public class CasoDeUsoDTO implements Serializable {
     /**
     * nombre del caso de uso.
     */
    private String nombre;
     /**
     * Id del caso de uso.
     */
    private Long id;
    
    private RequisitoDTO requisito;
    
    /**
     * Constructor vacio
     */
    public CasoDeUsoDTO(){
        
    }

    /**
     * Crea un Data transfer object (DTO) de caso de uso a partir de una entity 
     * @param entidad con la cual se creará el DTO
     */
    public CasoDeUsoDTO(CasoDeUsoEntity entidad){
        if (entidad != null) {
            this.id = entidad.getId();
            this.nombre = entidad.getNombre();
            if (entidad.getRequisito() != null) {
                this.requisito = new RequisitoDTO(entidad.getRequisito());
            } else {
                entidad.setRequisito(null);
            }
        }
    }
    

    /**
     * Convierte un DTO en una entity de 
     * @return entity construida a partir del DTO
     */
   public CasoDeUsoEntity toEntity()
   {
       CasoDeUsoEntity entidad = new CasoDeUsoEntity();
       entidad.setId(this.getId());
       entidad.setNombre(this.getNombre());
       return entidad;
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
    
    
    
}

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
    
    private String nombre;
    private Long id;
    //private List<CaminoDTO> cursoBasicoEventos;
    
    public CasoDeUsoDTO(){
        
    }

        public CasoDeUsoDTO(CasoDeUsoEntity entidad){
        if (entidad != null) {
            this.id = entidad.getId();
            this.nombre = entidad.getNombre();

        }
    }

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
    
    
    
}

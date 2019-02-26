/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class OrganizacionDTO implements Serializable{
    
    private String sector;
    private String nombre;
    private long id;

    public OrganizacionDTO() {

    }
    
    public OrganizacionDTO(OrganizacionEntity entidad) {
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setSector(entidad.getSector());
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrganizacionEntity toEntity() {
        OrganizacionEntity entidad = new OrganizacionEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setSector(this.getSector());
        return entidad;
    }
    
    
}

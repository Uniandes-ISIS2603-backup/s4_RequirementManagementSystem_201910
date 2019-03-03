package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import java.io.Serializable;

/**
 *
 * @author Mateo Pedroza
 */
public class OrganizacionDTO implements Serializable{
    
    private String sector;
    private String nombre;
    private long id;

    public OrganizacionDTO() {

    }
    
    /**
     * Constructor a partir de la entidad con pertenencias necesarias
     * @param entidad 
     */
    public OrganizacionDTO(OrganizacionEntity entidad) {
        setId(entidad.getId());
        setNombre(entidad.getNombre());
        setSector(entidad.getSector());
    }

    /*
    Getters y Setter de atributos
    */
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

    /**
     * Método para transformar el DTO a una entidad.
     * @return entidad organizacion
     */
    public OrganizacionEntity toEntity() {
        OrganizacionEntity entidad = new OrganizacionEntity();
        entidad.setId(this.getId());
        entidad.setNombre(this.getNombre());
        entidad.setSector(this.getSector());
        return entidad;
    }
    
    
}

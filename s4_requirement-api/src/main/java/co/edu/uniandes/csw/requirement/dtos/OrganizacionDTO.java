package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import java.io.Serializable;

/**
 *
 * @author Mateo Pedroza
 */
public class OrganizacionDTO implements Serializable {

    private String sector;
    private String nombre;
    private Long id;

    public OrganizacionDTO() {

    }

    /**
     * Constructor a partir de la entidad con pertenencias necesarias
     *
     * @param entidad
     */
    public OrganizacionDTO(OrganizacionEntity entidad) {
        if (entidad != null) {
            this.id = entidad.getId();
            this.nombre = entidad.getNombre();
            this.sector = entidad.getSector();
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return entidad organizacion
     */
    public OrganizacionEntity toEntity() {
        OrganizacionEntity entidad = new OrganizacionEntity();
        entidad.setId(this.id);
        entidad.setNombre(this.nombre);
        entidad.setSector(this.sector);
        return entidad;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

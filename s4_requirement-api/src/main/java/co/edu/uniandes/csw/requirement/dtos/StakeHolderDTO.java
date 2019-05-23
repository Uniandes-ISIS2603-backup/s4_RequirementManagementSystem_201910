package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;

/**
 *
 * @author Mateo Pedroza
 */
public class StakeHolderDTO implements Serializable {

    private String tipo;
    private String nombre;
    private Long id;
    
    private OrganizacionDTO organizacion;
    
    public StakeHolderDTO() {
    }
    
    
    /**
     * Constructor a partir de la entidad con pertenencias necesarias
     *
     * @param stakeHolderEntity La entidad del premio
     */
    public StakeHolderDTO(StakeHolderEntity stakeHolderEntity) {
        if (stakeHolderEntity != null) {
            this.id = stakeHolderEntity.getId();
            this.nombre = stakeHolderEntity.getNombre();
            this.tipo = stakeHolderEntity.getTipo();
//            if (stakeHolderEntity.getOrganizacion() != null) {
//                this.organizacion = new OrganizacionDTO(stakeHolderEntity.getOrganizacion());
//            } else {
//                stakeHolderEntity.setOrganizacion(null);
//            }
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del del premio.
     */
    public StakeHolderEntity toEntity() {
        StakeHolderEntity stakeHolderEntity = new StakeHolderEntity();
        stakeHolderEntity.setId(this.id);
        stakeHolderEntity.setNombre(this.nombre);
        stakeHolderEntity.setTipo(this.tipo);
//        if (organizacion != null) {
//            stakeHolderEntity.setOrganizacion(organizacion.toEntity());
//        }
        return stakeHolderEntity;
    }
    
    //Getter y Setters de atributos
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public OrganizacionDTO getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionDTO organizacion) {
        this.organizacion = organizacion;
    }   
}
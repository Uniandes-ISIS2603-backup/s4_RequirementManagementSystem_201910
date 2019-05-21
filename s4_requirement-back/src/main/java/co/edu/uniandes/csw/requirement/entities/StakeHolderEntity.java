package co.edu.uniandes.csw.requirement.entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Mateo Pedroza
 */
@Entity
public class StakeHolderEntity extends BaseEntity implements Serializable {

 /**
 *
 * Atributos de la clase
 */
    private String tipo;
    private String nombre;

   
////Relacion cambios
//    @PodamExclude
//    @OneToMany(mappedBy = "stakeholder", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<CambioEntity> cambios = new ArrayList<>();
//
////Relacion aprobaciones
//
//    @PodamExclude
//    @OneToMany(mappedBy = "stakeholder", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<AprobacionEntity> aprobaciones = new ArrayList<>();

//Relacion organizacion
    @PodamExclude
    @ManyToOne
    private OrganizacionEntity organizacion;
    
//Relacion autorObjetivo
    @PodamExclude
    @OneToOne
    private ObjetivoEntity autorObjetivo;

//Relacion fuenteObjetivo    
    @PodamExclude
    @ManyToOne
    private ObjetivoEntity fuenteObjetivo;

//Relacion autorRequisito
    @PodamExclude
    @OneToOne
    private RequisitoEntity autorRequisito;

//Relacion fuenteRequisito
    @PodamExclude
    @ManyToOne
    private RequisitoEntity fuenteRequisito;

    public StakeHolderEntity() {

    }

    
//Getter and Setters
    
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
    
//    public List<CambioEntity> getCambios() {
//        return cambios;
//    }
//
//    public void setCambios(List<CambioEntity> cambios) {
//        this.cambios = cambios;
//    }
//
//    public List<AprobacionEntity> getAprobaciones() {
//        return aprobaciones;
//    }
//
//    public void setAprobaciones(List<AprobacionEntity> aprobaciones) {
//        this.aprobaciones = aprobaciones;
//    }

    public ObjetivoEntity getAutorObjetivo() {
        return autorObjetivo;
    }

    public void setAutorObjetivo(ObjetivoEntity autorObjetivo) {
        this.autorObjetivo = autorObjetivo;
    }

    public ObjetivoEntity getFuenteObjetivo() {
        return fuenteObjetivo;
    }

    public void setFuenteObjetivo(ObjetivoEntity fuenteObjetivo) {
        this.fuenteObjetivo = fuenteObjetivo;
    }

    public RequisitoEntity getAutorRequisito() {
        return autorRequisito;
    }

    public void setAutorRequisito(RequisitoEntity autorRequisito) {
        this.autorRequisito = autorRequisito;
    }

    public RequisitoEntity getFuenteRequisito() {
        return fuenteRequisito;
    }

    public void setFuenteRequisito(RequisitoEntity fuenteRequisito) {
        this.fuenteRequisito = fuenteRequisito;
    }
    
    
    public OrganizacionEntity getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionEntity organizacion) {
        this.organizacion = organizacion;
    }

}

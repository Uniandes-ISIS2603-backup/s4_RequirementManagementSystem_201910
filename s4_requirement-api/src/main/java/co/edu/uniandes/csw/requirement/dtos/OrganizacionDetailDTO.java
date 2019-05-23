package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateo Pedroza
 */
public class OrganizacionDetailDTO extends OrganizacionDTO implements Serializable {

    //private List<>
    //relacion 1 a 1
    private List<StakeHolderDTO> stakeHolders;

    private List<ProyectoDTO> proyectos;

    public OrganizacionDetailDTO() {
        super();

    }

    /**
     * Metodo constructor, agregar todas las relaciones que dependen de
     * organizacion
     *
     * @param organizacionEntity
     */
    public OrganizacionDetailDTO(OrganizacionEntity organizacionEntity) {
        super(organizacionEntity);
        if (organizacionEntity != null) {
            if (organizacionEntity.getStakeHolders() != null) {
                stakeHolders = new ArrayList<>();
                for (StakeHolderEntity entityOrg : organizacionEntity.getStakeHolders()) {
                    stakeHolders.add(new StakeHolderDTO(entityOrg));
                }
            }

            if (organizacionEntity.getProyectos() != null) {
                proyectos = new ArrayList<>();
                for (ProyectoEntity entityProy : organizacionEntity.getProyectos()) {
                    proyectos.add(new ProyectoDTO(entityProy));
                }
            }
        }
    }

    /**
     * Convierte el DTO en una entidad
     *
     * @return entidad organizacion
     */
    @Override
    public OrganizacionEntity toEntity() {
        OrganizacionEntity entidad = super.toEntity();

        if (stakeHolders != null) {
            List<StakeHolderEntity> stakeHolderEntity = new ArrayList();
            for (StakeHolderDTO dtoStake : stakeHolders) {
                stakeHolderEntity.add(dtoStake.toEntity());
            }

            entidad.setStakeHolders(stakeHolderEntity);
        }

        if (proyectos != null) {
            List<ProyectoEntity> proyectoEntity = new ArrayList();
            for (ProyectoDTO dtoStake : proyectos) {
                proyectoEntity.add(dtoStake.toEntity());
            }

            entidad.setProyectos(proyectoEntity);
        }

        return entidad;
    }

    public List<StakeHolderDTO> getStakeHolders() {
        return stakeHolders;
    }

    public void setStakeHolders(List<StakeHolderDTO> stakeHolders) {
        this.stakeHolders = stakeHolders;
    }

    public List<ProyectoDTO> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<ProyectoDTO> proyectos) {
        this.proyectos = proyectos;
    }
    
}

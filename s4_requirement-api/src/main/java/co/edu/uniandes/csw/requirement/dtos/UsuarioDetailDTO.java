package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mateo Pedroza
 */
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable {

    //private List<>
    //relacion 1 a 1
    private List<ProyectoDTO> proyectos;

    public UsuarioDetailDTO() {
        super();

    }

    /**
     * Metodo constructor, agregar todas las relaciones que dependen de
     * usuario
     *
     * @param usuarioEntity
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        if (usuarioEntity != null) {

            if (usuarioEntity.getProyectos() != null) {
                proyectos = new ArrayList<>();
                for (ProyectoEntity entityProy : usuarioEntity.getProyectos()) {
                    proyectos.add(new ProyectoDTO(entityProy));
                }
            }
        }
    }

    /**
     * Convierte el DTO en una entidad
     *
     * @return entidad usuario
     */
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity entidad = super.toEntity();
        if (proyectos != null) {
            List<ProyectoEntity> proyectoEntity = new ArrayList();
            for (ProyectoDTO dtoStake : proyectos) {
                proyectoEntity.add(dtoStake.toEntity());
            }

            entidad.setProyectos(proyectoEntity);
        }

        return entidad;
    }

    public List<ProyectoDTO> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<ProyectoDTO> proyectos) {
        this.proyectos = proyectos;
    }


}

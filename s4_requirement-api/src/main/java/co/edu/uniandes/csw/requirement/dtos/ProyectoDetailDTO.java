/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;

import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Sofia Alvarez, Jorge Esguerra, David Manosalva
 */
public class ProyectoDetailDTO extends ProyectoDTO implements Serializable {

    //Relacion cero a muchos
    private List<ObjetivoDTO> objetivos;
    //Relacion cero a muchos
    private List<OrganizacionDTO> organizaciones;
    // Relacion cero a muchos
    private List<UsuarioDTO> usuarios; 

    /**
     * Constructor por defecto
     */
    public ProyectoDetailDTO() {

    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param proyectoEntity La entidad de la cual se construye el DTO
     */
    public ProyectoDetailDTO(ProyectoEntity proyectoEntity) {
        super(proyectoEntity);
        if (proyectoEntity != null) {
            if (proyectoEntity.getObjetivos() != null) {
                objetivos = new ArrayList<>();

                for (ObjetivoEntity entityObjetivo : proyectoEntity.getObjetivos()) {
                    objetivos.add(new ObjetivoDTO(entityObjetivo));
                }
            }

            if (proyectoEntity.getObjetivos() != null) {
                organizaciones = new ArrayList<>();
                for (OrganizacionEntity entityOrganizacion : proyectoEntity.getOrganizaciones()) {
                    organizaciones.add(new OrganizacionDTO(entityOrganizacion));
                }
            }
            if (proyectoEntity.getUsuarios() != null) {
                usuarios = new ArrayList<>();
                for (UsuarioEntity usuarioEntity : proyectoEntity.getUsuarios()) {
                    usuarios.add(new UsuarioDTO(usuarioEntity));
                }
            
        }

    }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el proyecto.
     */
    @Override
    public ProyectoEntity toEntity() {
        ProyectoEntity proyectoEntity = super.toEntity();
        if (getObjetivos() != null) {
            List<ObjetivoEntity> objetivosEntity = new ArrayList<>();
            for (ObjetivoDTO dtoObjetivo : getObjetivos()) {
                objetivosEntity.add(dtoObjetivo.toEntity());
            }
            proyectoEntity.setObjetivos(objetivosEntity);
        }
      if (getOrganizaciones() != null) {
            List<OrganizacionEntity> organizacionesEntity = new ArrayList<>();
            for (OrganizacionDTO dtoOrganizacion : getOrganizaciones()) {
                organizacionesEntity.add(dtoOrganizacion.toEntity());
            }
            proyectoEntity.setOrganizaciones(organizacionesEntity);
        }
        if (getUsuarios() != null) {
            List<UsuarioEntity> usuariosEntity = new ArrayList<>();
            for (UsuarioDTO dtoUsuario : getUsuarios()) {
                usuariosEntity.add(dtoUsuario.toEntity());
            }
            proyectoEntity.setUsuarios(usuariosEntity);
        }

        return proyectoEntity;
    }

    /**
     * @return the objetivos
     */
    public List<ObjetivoDTO> getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(List<ObjetivoDTO> objetivos) {
        this.objetivos = objetivos;
    }

    
    /**
     * @return the organizaciones
     */
    public List<OrganizacionDTO> getOrganizaciones() {
        return organizaciones;
    }

    /**
     * @param organizaciones the organizaciones to set
     */
    public void setOrganizaciones(List<OrganizacionDTO> organizaciones) {
        this.organizaciones = organizaciones;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
    
    
}

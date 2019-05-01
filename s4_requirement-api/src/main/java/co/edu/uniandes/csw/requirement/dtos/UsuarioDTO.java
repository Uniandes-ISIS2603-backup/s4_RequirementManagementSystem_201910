/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;

/**
 *
 * @author estudiante
 */
public class UsuarioDTO {
    
    private String tipo;
    private String usuario;
    private Long id;
    private String contrasena;
    
    //private ProyectoDTO proyecto;
    
    public UsuarioDTO() {
    }
    
    
    /**
     * Constructor a partir de la entidad con pertenencias necesarias
     *
     * @param usuarioEntity La entidad del premio
     */
    public UsuarioDTO(UsuarioEntity usuarioEntity) {
        if (usuarioEntity != null) {
            this.id = usuarioEntity.getId();
            this.usuario = usuarioEntity.getUsuario();
            this.tipo = usuarioEntity.getTipo();
            //if (usuarioEntity.getProyecto() != null) {
                //this.proyecto = new ProyectoDTO(usuarioEntity.getProyecto());
            //} else {
                //usuarioEntity.setProyecto(null);
            //}
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del del premio.
     */
    public UsuarioEntity toEntity() {
        UsuarioEntity UsuarioEntity = new UsuarioEntity();
        UsuarioEntity.setId(this.id);
        UsuarioEntity.setUsuario(this.usuario);
        UsuarioEntity.setTipo(this.tipo);
        UsuarioEntity.setContrasena(this.contrasena);
        //if (proyecto != null) {
            //UsuarioEntity.setProyecto(proyecto.toEntity());
        //}
        return UsuarioEntity;
    }
    
    //Getter y Setters de atributos
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    

    /*
    public ProyectoDTO getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoDTO proyecto) {
        this.proyecto = proyecto;
    }
    */


}

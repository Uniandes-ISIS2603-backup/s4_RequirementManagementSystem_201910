/*
MIT License

Copyright (c) 2019 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.requirement.dtos;

import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import java.io.Serializable;

/**
 * CascaraDTO Objeto de transferencia de datos de la cascara. Los DTO contienen
 * las representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 *
 * @author ISIS2603
 */
public class StakeHolderDTO implements Serializable {

    private String tipo;
    private String nombre;
    private Long id;
    
    private OrganizacionDTO organizacion;
    
    public StakeHolderDTO() {
    }
    
    
    /**
     * Constructor a partir de la entidad
     *
     * @param stakeHolderEntity La entidad del premio
     */
    public StakeHolderDTO(StakeHolderEntity stakeHolderEntity) {
        if (stakeHolderEntity != null) {
            this.id = stakeHolderEntity.getId();
            this.nombre = stakeHolderEntity.getNombre();
            this.tipo = stakeHolderEntity.getTipo();
            if (stakeHolderEntity.getOrganizacion() != null) {
                this.organizacion = new OrganizacionDTO(stakeHolderEntity.getOrganizacion());
            } else {
                stakeHolderEntity.setOrganizacion(null);
            }
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
        if (organizacion != null) {
            stakeHolderEntity.setOrganizacion(organizacion.toEntity());
        }
        return stakeHolderEntity;
    }

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
    
    
}
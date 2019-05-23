/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.OrganizacionDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.OrganizacionLogic;
import co.edu.uniandes.csw.requirement.ejb.ProyectoOrganizacionLogic;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @proyecto estudiante
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProyectoOrganizacionResource {
    
    

    @Inject
    private ProyectoOrganizacionLogic proyectoOrganizacionLogic;

    @Inject
    private OrganizacionLogic organizacionesLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Asocia un organizacion existente con un proyecto existente
     *
     * @param proyectosId El ID del proyecto al cual se le va a asociar el organizacion
     * @param organizacionesId El ID del organizacion que se asocia
     * @return JSON {@link OrganizacionDetailDTO} - El organizacion asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizacion.
     */
    @POST
    @Path("{organizacionId: \\d+}")
    public OrganizacionDetailDTO addOrganizacion(@PathParam("proyectosId") Long proyectosId, @PathParam("organizacionId") Long organizacionesId) {
        if (organizacionesLogic.getOrganizacion(organizacionesId) == null) {
            throw new WebApplicationException("El recurso /organizaciones/" + organizacionesId + " no existe.", 404);
        }
        OrganizacionDetailDTO detailDTO = new OrganizacionDetailDTO(proyectoOrganizacionLogic.addOrganizacion(proyectosId, organizacionesId));
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los organizacions que existen en un proyecto.
     *
     * @param proyectosId El ID del proyecto del cual se buscan los organizacions
     * @return JSONArray {@link OrganizacionDetailDTO} - Los organizacions encontrados en el
     * proyecto. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<OrganizacionDetailDTO> getOrganizaciones(@PathParam("proyectosId") Long proyectosId) {
        List<OrganizacionDetailDTO> lista = organizacionesListEntity2DTO(proyectoOrganizacionLogic.getOrganizaciones(proyectosId));
        return lista;
    }

    /**
     * Busca y devuelve el organizacion con el ID recibido en la URL, relativo a un
     * proyecto.
     *
     * @param proyectosId El ID del proyecto del cual se busca el organizacion
     * @param organizacionesId El ID del organizacion que se busca
     * @return {@link OrganizacionDetailDTO} - El organizacion encontrado en el proyecto.
     * @throws co.edu.uniandes.csw.organizacionestore.exceptions.BusinessLogicException
     * si el organizacion no está asociado al proyecto
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizacion.
     */
    @GET
    @Path("{organizacionId: \\d+}")
    public OrganizacionDetailDTO getOrganizacion(@PathParam("proyectosId") Long proyectosId, @PathParam("organizacionId") Long organizacionesId) throws BusinessLogicException {
        if (organizacionesLogic.getOrganizacion(organizacionesId) == null) {
            throw new WebApplicationException("El recurso /organizaciones/" + organizacionesId + " no existe.", 404);
        }
        OrganizacionDetailDTO detailDTO = new OrganizacionDetailDTO(proyectoOrganizacionLogic.getOrganizacion(proyectosId, organizacionesId));
        return detailDTO;
    }

    /**
     * Actualiza la lista de organizacions de un proyecto con la lista que se recibe en el
     * cuerpo
     *
     * @param proyectosId El ID del proyecto al cual se le va a asociar el organizacion
     * @param organizaciones JSONArray {@link OrganizacionDetailDTO} - La lista de organizacions que se
     * desea guardar.
     * @return JSONArray {@link OrganizacionDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizacion.
     */
    @PUT
    public List<OrganizacionDetailDTO> replaceOrganizaciones(@PathParam("proyectosId") Long proyectosId, List<OrganizacionDetailDTO> organizaciones) {
        for (OrganizacionDetailDTO org : organizaciones) {
            if (organizacionesLogic.getOrganizacion(org.getId()) == null) {
                throw new WebApplicationException("El recurso /organizaciones/" + org.getId() + " no existe.", 404);
            }
        }
        List<OrganizacionDetailDTO> lista = organizacionesListEntity2DTO(proyectoOrganizacionLogic.replaceOrganizaciones(proyectosId, organizacionesListDTO2Entity(organizaciones)));
        return lista;
    }

    /**
     * Elimina la conexión entre el organizacion y e proyecto recibidos en la URL.
     *
     * @param proyectosId El ID del proyecto al cual se le va a desasociar el organizacion
     * @param organizacionesId El ID del organizacion que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el organizacion.
     */
    @DELETE
    @Path("{organizacionId: \\d+}")
    public void removeOrganizacion(@PathParam("proyectosId") Long proyectosId, @PathParam("organizacionId") Long organizacionesId) {
        if (organizacionesLogic.getOrganizacion(organizacionesId) == null) {
            throw new WebApplicationException("El recurso /organizaciones/" + organizacionesId + " no existe.", 404);
        }
        proyectoOrganizacionLogic.removeOrganizacion(proyectosId, organizacionesId);
    }

    /**
     * Convierte una lista de OrganizacionEntity a una lista de OrganizacionDetailDTO.
     *
     * @param entityList Lista de OrganizacionEntity a convertir.
     * @return Lista de OrganizacionDetailDTO convertida.
     */
    private List<OrganizacionDetailDTO> organizacionesListEntity2DTO(List<OrganizacionEntity> entityList) {
        List<OrganizacionDetailDTO> list = new ArrayList<>();
        for (OrganizacionEntity entity : entityList) {
            list.add(new OrganizacionDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de OrganizacionDetailDTO a una lista de OrganizacionEntity.
     *
     * @param dtos Lista de OrganizacionDetailDTO a convertir.
     * @return Lista de OrganizacionEntity convertida.
     */
    private List<OrganizacionEntity> organizacionesListDTO2Entity(List<OrganizacionDetailDTO> dtos) {
        List<OrganizacionEntity> list = new ArrayList<>();
        for (OrganizacionDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}

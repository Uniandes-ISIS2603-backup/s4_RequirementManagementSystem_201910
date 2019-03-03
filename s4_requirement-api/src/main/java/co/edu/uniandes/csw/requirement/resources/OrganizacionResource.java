package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.OrganizacionDTO;
import co.edu.uniandes.csw.requirement.dtos.OrganizacionDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.OrganizacionLogic;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
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

/**
 *
 * @author Mateo Pedroza
 */
@Path("organizaciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class OrganizacionResource {

    @Inject
    private OrganizacionLogic logica;

    /**
     * Agrega una organizacion a la base de datos
     * @param organizacion
     * @return organizacion creada
     * @throws BusinessLogicException 
     */
    @POST
    public OrganizacionDTO createOrganizacion(OrganizacionDTO organizacion) throws BusinessLogicException {

        OrganizacionEntity organizacionntity = organizacion.toEntity();
        OrganizacionEntity nuevaOrganizacion = logica.createOrganizacion(organizacionntity);
        OrganizacionDTO nuevaOrganizacionDTO = new OrganizacionDTO(nuevaOrganizacion);
        return nuevaOrganizacionDTO;
    }

    /**
     * retorna todas las organizaciones existentes en base de datos
     * @return lista de organizaciones
     */
    @GET
    public List<OrganizacionDetailDTO> getStakeholders() {
        List<OrganizacionDetailDTO> listaOrganizacion = entity2DTO(logica.getOrganizaciones());
        return listaOrganizacion;
    }

    /**
     * retorna la organizacion con el id del parametro, si no existe retorna null
     * @param organizacionId
     * @return organizacion encontrada, null si no existe
     * @throws WebApplicationException 
     */
    @GET
    @Path("{organizacionId: \\d+}")
    public OrganizacionDetailDTO getOrganizacion(@PathParam("organizacionId") Long organizacionId) throws WebApplicationException {
        OrganizacionEntity organizacionEntity = logica.getOrganizacion(organizacionId);
        if (organizacionEntity == null) {
            throw new WebApplicationException("El recurso /organizaciones/" + organizacionId + " no existe.", 404);
        }
        OrganizacionDetailDTO detailDTO = new OrganizacionDetailDTO(organizacionEntity);
        return detailDTO;
    }

    /**
     * elimina la organizacion identificada por un id pasado por parametro
     * @param organizacionId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{organizacionId: \\d+}")
    public void deleteOrganizacion(@PathParam("organizacionId") Long organizacionId) throws BusinessLogicException {
        if (logica.getOrganizacion(organizacionId) == null) {
            throw new WebApplicationException("El recurso /organizaciones/" + organizacionId + " no existe.", 404);
        }
        logica.deleteOrganizacion(organizacionId);
    }

    /**
     * actualiza organizacion existente con valores pasados por parametro
     * @param organizacionId
     * @param organizacion
     * @return la organizacion actualizada
     * @throws WebApplicationException 
     */
    @PUT
    @Path("{organizacionId: \\d+}")
    public OrganizacionDetailDTO updateOrganizacion(@PathParam("organizacionId") Long organizacionId, OrganizacionDetailDTO organizacion) throws WebApplicationException {
        organizacion.setId(organizacionId);
        if (logica.getOrganizacion(organizacionId) == null) {
            throw new WebApplicationException("El recurso /organizaciones/" + organizacionId + " no existe.", 404);
        }
        OrganizacionDetailDTO detailDTO = new OrganizacionDetailDTO(logica.updateOrganizacion(organizacionId, organizacion.toEntity()));
        return detailDTO;

    }

    /**
     * convierte entidades en DTOS
     * @param entityList
     * @return lista de DTO
     */
    private List<OrganizacionDetailDTO> entity2DTO(List<OrganizacionEntity> entityList) {
        List<OrganizacionDetailDTO> list = new ArrayList<>();
        for (OrganizacionEntity entity : entityList) {
            list.add(new OrganizacionDetailDTO(entity));
        }
        return list;
    }

}

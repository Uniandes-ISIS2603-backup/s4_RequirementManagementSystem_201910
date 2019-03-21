/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;
import co.edu.uniandes.csw.requirement.dtos.DrsDTO;
import co.edu.uniandes.csw.requirement.dtos.DrsDTODetail;
import co.edu.uniandes.csw.requirement.ejb.DRSLogic;
import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
 * @author Sofia Alvarez
 */
@Path("drs")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class DrsResource 
{
     private static final Logger LOGGER = Logger.getLogger(DrsResource.class.getName());
     
     @Inject
     private DRSLogic drsLogic;

     @POST
    public DrsDTO createDrs(DrsDTO drs) throws BusinessLogicException{
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        DRSEntity drsEntity = drs.toEntity();
        // Invoca la lógica para crear un drs nuevo
        DRSEntity nuevoDrsEntity = drsLogic.createDRS(drsEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        DrsDTO nuevoDrsDTO = new DrsDTO(nuevoDrsEntity);
        return nuevoDrsDTO;
    }
    
     /**
     * Busca y devuelve todos los drs que existen en la aplicacion.
     *
     * @return JSONArray  - Los Drs
     * encontradas en la aplicación. Si no hay ninguno, retorna una lista vacía.
     */
    @GET
    public List<DrsDTODetail> getDRSs() {
        List<DrsDTODetail> listaEditoriales = listEntity2DetailDTO(drsLogic.getDRSs());
        return listaEditoriales;
    }

    /**
     * Busca el DRS con el id asociado recibido en la URL y la devuelve.
     *
     * @param id Identificador del DRS que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON - El DRS buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
     @GET
    @Path("{id: \\d+}")
    public DrsDTODetail getDrs (@PathParam("id") Long id) throws WebApplicationException {
        DRSEntity drsEntity = drsLogic.getDRS(id);
        if (drsEntity == null) {
            throw new WebApplicationException("El recurso /drs/" + id + " no existe.", 404);
        }
        DrsDTODetail detailDTO = new DrsDTODetail(drsEntity);
        return detailDTO;
    }
    
    /**
     * Borra el drs con el id asociado recibido en la URL.
     *
     * @param id Identificador del drs que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el drs.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el drs.
     */
     @DELETE
    @Path("{id: \\d+}")
    public void deleteDrs (@PathParam("id") Long id) throws BusinessLogicException
    {
        if (drsLogic.getDRS(id) == null) {
            throw new WebApplicationException("El recurso /drs/" + id + " no existe.", 404);
        }
        drsLogic.deleteDRS(id);
    }
    /**
     * Actualiza el drs con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param id Identificador de la editorial que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param dto {@link DrsDTODetail} El drs que se desea
     * guardar.
     * @return JSON {@link DRSDetailDTO} - El drs guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el drs a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public DrsDTO updateDrs (@PathParam("id") Long id, DrsDTODetail dto) throws WebApplicationException {
        dto.setId(id);
        if (drsLogic.getDRS(id) == null) {
            throw new WebApplicationException("El recurso /drs/" + id + " no existe.", 404);
        }
        DrsDTODetail detailDTO = new DrsDTODetail(drsLogic.updateDRS(id, dto.toEntity()));
        return detailDTO;

    }
    
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos DRSEntity a una lista de
     * objetos DRSDetailDTO (json)
     *
     * @param drsList corresponde a la lista de DRSs de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de DRSs en forma DTO (json)
     */
    private List<DrsDTODetail> listEntity2DetailDTO(List<DRSEntity> drsList) {
        List<DrsDTODetail> list = new ArrayList<>();
        for (DRSEntity entity : drsList) {
            list.add(new DrsDTODetail(entity));
        }
        return list;
    }
}

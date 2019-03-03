package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.StakeHolderDTO;
import co.edu.uniandes.csw.requirement.dtos.StakeHolderDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
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
@Path("stakeholders")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class StakeHolderResource {

    @Inject
    private StakeHolderLogic logica;

    /**
     * Agrega una organizacion a la base de datos  
     * @param stakeHolder
     * @return
     * @throws BusinessLogicException 
     */
    @POST
    public StakeHolderDTO createStakeHolder(StakeHolderDTO stakeHolder) throws BusinessLogicException {

        StakeHolderEntity stakeHolderEntity = stakeHolder.toEntity();
        StakeHolderEntity nuevoStakeHolder = logica.createStakeHolder(stakeHolderEntity);
        StakeHolderDTO nuevoStakeHolderDTO = new StakeHolderDTO(nuevoStakeHolder);
        return nuevoStakeHolderDTO;

    }

    /**
     * retorna todos los stakeholders existentes en base de datos
     * @return lista de stakeholders
     */
    @GET
    public List<StakeHolderDetailDTO> getStakeholders() {
        List<StakeHolderDetailDTO> listaStakeHolders = entity2DTO(logica.getStakeHolders());
        return listaStakeHolders;
    }

    /**
     * retorna el stakeholder con id por parametro, null si no existe
     * @param stakeholdersId
     * @return stakeholder con id por parametro, null si no existe
     * @throws WebApplicationException 
     */
    @GET
    @Path("{stakeholdersId: \\d+}")
    public StakeHolderDetailDTO getStakeHolder(@PathParam("stakeholdersId") Long stakeholdersId) throws WebApplicationException {
        StakeHolderEntity stakeholderEntity = logica.getStakeHolder(stakeholdersId);
        if (stakeholderEntity == null) {
            throw new WebApplicationException("El recurso /stakeholders/" + stakeholdersId + " no existe.", 404);
        }
        StakeHolderDetailDTO detailDTO = new StakeHolderDetailDTO(stakeholderEntity);
        return detailDTO;
    }

    /**
     * elimina el stakeholder con id pasado por parametro
     * @param stakeholdersId
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{stakeholdersId: \\d+}")
    public void deleteStakeHolder(@PathParam("stakeholdersId") Long stakeholdersId) throws BusinessLogicException {
        if (logica.getStakeHolder(stakeholdersId) == null) {
            throw new WebApplicationException("El recurso /stakeholders/" + stakeholdersId + " no existe.", 404);
        }
        logica.deleteStakeHolder(stakeholdersId);
    }

    /**
     * actualiza stakeholder identificado por id dado por parametro
     * @param stakeholdersId
     * @param stakeholder
     * @return el stakeholder actualizado
     * @throws WebApplicationException 
     */
    @PUT
    @Path("{stakeholdersId: \\d+}")
    public StakeHolderDetailDTO updateStakeHolder(@PathParam("stakeholdersId") Long stakeholdersId, StakeHolderDetailDTO stakeholder) throws WebApplicationException {
        stakeholder.setId(stakeholdersId);
        if (logica.getStakeHolder(stakeholdersId) == null) {
            throw new WebApplicationException("El recurso /stakeholders/" + stakeholdersId + " no existe.", 404);
        }
        StakeHolderDetailDTO detailDTO = new StakeHolderDetailDTO(logica.updateStakeHolder(stakeholdersId, stakeholder.toEntity()));
        return detailDTO;

    }
    
    /**
     * convierte entidades en DTOs
     * @param entityList
     * @return lista de DTOs
     */
        private List<StakeHolderDetailDTO> entity2DTO(List<StakeHolderEntity> entityList) {
        List<StakeHolderDetailDTO> list = new ArrayList<>();
        for (StakeHolderEntity entity : entityList) {
            list.add(new StakeHolderDetailDTO(entity));
        }
        return list;
    }

}

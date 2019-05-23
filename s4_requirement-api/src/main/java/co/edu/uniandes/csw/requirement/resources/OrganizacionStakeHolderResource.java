/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.StakeHolderDTO;
import co.edu.uniandes.csw.requirement.ejb.OrganizacionStakeHolderLogic;
import co.edu.uniandes.csw.requirement.ejb.StakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.requirement.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
 * @author Mateo Pedroza
 */
@Path("stakeholders/organizaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizacionStakeHolderResource {
//
//    @Inject
//    private OrganizacionStakeHolderLogic relacionLogic;
//
//    @Inject
//    private StakeHolderLogic stakeholderLogic;
//
//    /**
//     * Busca y devuelve todos los stakeholders que existen en la organizacion.
//     *
//     * @param organizacionId Identificador de la organizacion que se esta buscando.
//     * Este debe ser una cadena de dígitos.
//     * @return JSONArray {@link StakeHolderDTO}
//     */
//    @GET
//    public List<StakeHolderDTO> getStakeHolders(@PathParam("organizacionId") Long organizacionId) {
//        List<StakeHolderDTO> listaDetailDTOs = StakeHolderListEntity2DTO(relacionLogic.getStakeHolders(organizacionId));
//        return listaDetailDTOs;
//    }
//
//    /**
//     * Busca el stakeholder con el id asociado dentro de la organizacion con id
//     * asociado.
//     *
//     * @param organizacionId Identificador de la organizacion que se esta
//     * buscando. Este debe ser una cadena de dígitos.
//     * @param stakeholderId Identificador del stakeholder que se esta buscando.
//     * Este debe ser una cadena de dígitos.
//     * @return JSON {@link StakeHolderDTO}
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
//     */
//    @GET
//    @Path("{stakeholdersId: \\d+}")
//    public StakeHolderDTO getStakeHolder(@PathParam("organizacionId") Long organizacionId, @PathParam("stakeholdersId") Long stakeholderId) throws BusinessLogicException {
//        if (stakeholderLogic.getStakeHolder(stakeholderId) == null) {
//            throw new WebApplicationException("El recurso /organizacion/" + organizacionId + "/stakeholder/" + stakeholderId + " no existe.", 404);
//        }
//        StakeHolderDTO StakeHolderDTO = new StakeHolderDTO(relacionLogic.getStakeHolder(organizacionId, stakeholderId));
//        return StakeHolderDTO;
//    }
//    
//     /**
//     * Remplaza las instancias de stakeholder asociadas a una instancia de Organizacion
//     *
//     * @param organizacionId Identificador de la organizacion que se esta
//     * remplazando. Este debe ser una cadena de dígitos.
//     * @param stakeholders JSONArray {@link stakeholderDTO} El arreglo de stakeholders nuevo para la
//     * organizacion.
//     * @return JSON {@link stakeholderDTO} - El arreglo de stakeholders guardado en la
//     * organizacion.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el stakeholder.
//     */
//    @PUT
//    public List<StakeHolderDTO> replacestakeholders(@PathParam("organizacionId") Long organizacionId, List<StakeHolderDTO> stakeholders) {
//        for (StakeHolderDTO stakeholder : stakeholders) {
//            if (stakeholderLogic.getStakeHolder(stakeholder.getId()) == null) {
//                throw new WebApplicationException("El recurso /stakeholders/" + stakeholder.getId() + " no existe.", 404);
//            }
//        }
//        List<StakeHolderDTO> listaDTO = StakeHolderListEntity2DTO(relacionLogic.replaceStakeHolders(organizacionId, StakeHoldersListDTO2Entity(stakeholders)));
//        return listaDTO;
//    }
//    
//    
//    /**
//     * Guarda un stakeholder dentro de una organizacion con la informacion que recibe el
//     * la URL. Se devuelve el stakeholder que se guarda en la organizacion.
//     *
//     * @param organizacionId Identificador de la organizacion que se esta
//     * actualizando. Este debe ser una cadena de dígitos.
//     * @param stakeholdersId Identificador del stakeholder que se desea guardar. Este debe
//     * ser una cadena de dígitos.
//     * @return JSON {@link stakeholderDTO} - El stakeholder guardado en la organizacion.
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el stakeholder.
//     */
//    @POST
//    @Path("{stakeholdersId: \\d+}")
//    public StakeHolderDTO addstakeholder(@PathParam("organizacionId") Long organizacionId, @PathParam("stakeholdersId") Long stakeholdersId) {
//        if (stakeholderLogic.getStakeHolder(stakeholdersId) == null) {
//            throw new WebApplicationException("El recurso /stakeholders/" + stakeholdersId + " no existe.", 404);
//        }
//        StakeHolderDTO stakeholderDTO = new StakeHolderDTO(relacionLogic.addStakeHolder(stakeholdersId, organizacionId));
//        return stakeholderDTO;
//    }
//
//    /**
//     * Convierte una lista de StakeHolderEntity a una lista de
//     * StakeHolderDTO.
//     *
//     * @param entityList Lista de StakeHolderEntity a convertir.
//     * @return Lista de StakeHolderDTO convertida.
//     */
//    private List<StakeHolderDTO> StakeHolderListEntity2DTO(List<StakeHolderEntity> entityList) {
//        List<StakeHolderDTO> list = new ArrayList();
//        for (StakeHolderEntity entity : entityList) {
//            list.add(new StakeHolderDTO(entity));
//        }
//        return list;
//    }
//    
//        /**
//     * Convierte una lista de StakeHolderDTO a una lista de stakeholderEntity.
//     *
//     * @param dtos Lista de StakeHolderDTO a convertir.
//     * @return Lista de stakeholderEntity convertida.
//     */
//    private List<StakeHolderEntity> StakeHoldersListDTO2Entity(List<StakeHolderDTO> dtos) {
//        List<StakeHolderEntity> list = new ArrayList<>();
//        for (StakeHolderDTO dto : dtos) {
//            list.add(dto.toEntity());
//        }
//        return list;
//    }
//    
//    
}

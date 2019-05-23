/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CaminoDTO;
import co.edu.uniandes.csw.requirement.ejb.CaminoLogic;
import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
@Produces("application/json")
@Consumes("application/json")
public class CaminoResource {

    @Inject
    private CaminoLogic caminoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    private static final Logger LOGGER = Logger.getLogger(CaminoResource.class.getName());

    /**
     * Crea un nuevo camino con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param camino {@link CaminoDTO} - El camino que se desea guardar.
     * @return JSON {@link CaminoDTO} - El camino guardado con el atributo id
     * autogenerado.
     */
    @POST
    public CaminoDTO createCamino(@PathParam("requisitosId") Long requisitosId, @PathParam("casosDeUsoId") Long casosDeUsoId, CaminoDTO camino) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CaminoResource createCamino: input: {0}", camino);
        CaminoDTO nuevoCaminoDTO = new CaminoDTO(caminoLogic.createCamino(requisitosId, casosDeUsoId, camino.toEntity()));
        LOGGER.log(Level.INFO, "CaminoResource createCamino: output: {0}", nuevoCaminoDTO);
        return nuevoCaminoDTO;
    }

    /**
     * Busca el camino con el id asociado recibido en la URL y la devuelve.
     *
     * @param caminosId Identificador del camino que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link CaminoDTO} - El camino buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{caminosId: \\d+}")
    public CaminoDTO getCamino(@PathParam("casosDeUsoId") Long casosDeUsoId, @PathParam("caminosId") Long caminosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CaminoResource getCamino: input: {0}", caminosId);
        CaminoEntity caminoEntity = caminoLogic.getCamino(casosDeUsoId, caminosId);
        if (caminoEntity == null) {
            throw new WebApplicationException("El recurso /camino/" + caminosId + " no existe.", 404);
        }
        CaminoDTO caminoDTO = new CaminoDTO(caminoEntity);
        LOGGER.log(Level.INFO, "CaminoResource getCamino: output: {0}", caminoDTO);
        return caminoDTO;
    }

    @GET
    public List<CaminoDTO> getCaminos(@PathParam("requisitosId") Long requisitosId, @PathParam("casosDeUsoId") Long casosDeUsoId) {
        LOGGER.info("CaminoResource getCaminos: input: void");
        List<CaminoDTO> listaCaminos = listEntity2DTO(caminoLogic.getCaminos(requisitosId, casosDeUsoId));
        LOGGER.log(Level.INFO, "CaminoResource getCaminos: output: {0}", listaCaminos);
        return listaCaminos;
    }

    /**
     * Borra el camino con el id asociado recibido en la URL
     *
     * @param caminosId Identificador del camino que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el camino.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el camino.
     */
    @DELETE
    @Path("{caminosId: \\d+}")
    public void deleteCamino(@PathParam("casosDeUsoId") Long casosDeUsoId, @PathParam("caminosId") Long caminosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CaminoResource deleteCamino: input: {0}", caminosId);
        CaminoEntity entity = caminoLogic.getCamino(casosDeUsoId, caminosId);
        if (entity == null) {
            throw new WebApplicationException("El camino con id = " + caminosId + " no existe", 404);
        }
        caminoLogic.deleteCamino(casosDeUsoId, caminosId);
        LOGGER.log(Level.INFO, "CaminoResource deleteCamino: output: void");
    }

    /**
     * Actualiza el camino con el id recibido en la URL con la informacion que
     * se recibe en el cuerpo de la petición.
     *
     * @param caminosId Identificador de el camino que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param camino el camino que se desea guardar
     * @return JSON {@link CaminoDTO} - El camino guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el camino a
     * actualizar.
     */
    @PUT
    @Path("{caminosId: \\d+}")
    public CaminoDTO updateCamino(@PathParam("casosDeUsoId") Long casosDeUsoId, @PathParam("caminosId") Long caminosId, CaminoDTO camino) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CaminoResource putCamino: input: {0}, dto = {1}", new Object[]{caminosId, camino});
        camino.setIdPaso(caminosId);
        CaminoEntity ant = caminoLogic.getCamino(casosDeUsoId, caminosId);
        if (ant == null) {
            throw new WebApplicationException("El camino con id = " + caminosId + " no existe", 404);
        }
        CaminoDTO actual = new CaminoDTO(ant);
        System.out.println();
        if (camino.getPasos() == null || camino.getPasos().isEmpty()) {
            camino.setPasos(actual.getPasos());
        }
        if (camino.getTipoPaso()== null || camino.getTipoPaso().isEmpty()) {
            camino.setTipoPaso(actual.getTipoPaso());
        }
        CaminoDTO dto = new CaminoDTO(caminoLogic.updateCamino(casosDeUsoId, caminosId, camino.toEntity()));
        LOGGER.log(Level.INFO, "CaminoResource putCamino: input: {0}", dto);
        return dto;
    }

    private List<CaminoDTO> listEntity2DTO(List<CaminoEntity> entityList) {
        List<CaminoDTO> list = new ArrayList<>();
        for (CaminoEntity entity : entityList) {
            list.add(new CaminoDTO(entity));
        }
        return list;
    }

}

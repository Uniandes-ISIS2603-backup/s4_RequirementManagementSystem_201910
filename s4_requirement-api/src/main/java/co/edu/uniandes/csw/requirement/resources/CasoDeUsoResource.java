/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CasoDeUsoDTO;
import co.edu.uniandes.csw.requirement.dtos.CasoDeUsoDetailDTO;
import co.edu.uniandes.csw.requirement.ejb.CasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Sofia Sarmiento
 */
/**
 * Ruta de los casos de uso
 */
@Produces("application/json")
@Consumes("application/json")
public class CasoDeUsoResource {

    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(CasoDeUsoResource.class.getName());

    /**
     * Inyeccion de las dependencias de los casos de uso
     */
    @Inject
    private CasoDeUsoLogic casoDeUsoLogic;

    /**
     * Crea un nuevo caso de uso.
     *
     * @param objetivosId
     * @param casoDeUso de a crear
     * @return caso de uso creado
     * @throws BusinessLogicException si no se cumplen las reglas de negocio
     */
    @POST
    public CasoDeUsoDTO crearCasoDeUso(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, CasoDeUsoDTO casoDeUso) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CasoDeUsoResource createCasoDeUso: input: {0}", casoDeUso);
        CasoDeUsoDetailDTO casoDTO = new CasoDeUsoDetailDTO(casoDeUsoLogic.createCasoDeUso(objetivosId, requisitosId, casoDeUso.toEntity()));
        LOGGER.log(Level.INFO, "CasoDeUsoResource createCasoDeUso: output: {0}", casoDTO);
        return casoDTO;
    }

    /**
     * Retorna un caso de uso con un id especifico
     *
     * @param casosDeUsoId del caso de uso a buscar
     * @return el caso de uso buscado
     */
    @GET
    @Path("{casosDeUsoId: \\d+}")
    public CasoDeUsoDetailDTO getCasoDeUso(@PathParam ("requisitosId") Long requisitosId, @PathParam("casosDeUsoId") Long casosDeUsoId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CasoDeUsoResource getCasoDeUso: input: {0}", casosDeUsoId);
        CasoDeUsoEntity casoEntity = casoDeUsoLogic.getCasoDeUso(requisitosId, casosDeUsoId);
        if (casoEntity == null) {
            throw new WebApplicationException("El recurso /casos/" + casosDeUsoId + " no existe.", 404);
        }
        CasoDeUsoDetailDTO detailDTO = new CasoDeUsoDetailDTO(casoEntity);
        LOGGER.log(Level.INFO, "CasoDeUsoResource getCasoDeUso: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Retorna todos los casos de uso
     *
     * @return todos los casos de uso
     */
    @GET
    public List<CasoDeUsoDetailDTO> getCasosDeUso(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId) {
        LOGGER.info("CasoDeUsoResource getCasosDeUso: input: void");
        List<CasoDeUsoDetailDTO> listaCasos = listEntity2DTO(casoDeUsoLogic.getCasosDeUso(objetivosId, requisitosId));
        LOGGER.log(Level.INFO, "CasoDeUsoResource getCasosDeUso: output: {0}", listaCasos);
        return listaCasos;
    }

    /**
     * Elimina un caso de uso
     *
     * @param id del caso de uso a eliminar
     */
    @DELETE
    @Path("{casosDeUsoId: \\d+}")
    public void deleteCasoDeUso(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, @PathParam("casosDeUsoId") Long casosDeUsoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CasoDeUsoResource deleteCasoDeUso: input: {0}", casosDeUsoId);
        CasoDeUsoEntity entity = casoDeUsoLogic.getCasoDeUso(requisitosId, casosDeUsoId);
        casoDeUsoLogic.deleteCasoDeUso(requisitosId, casosDeUsoId);
        LOGGER.info("CasoDeUsoResource deleteCasoDeUso: output: void");
    }

    /**
     * Actualiza un caso de uso
     *
     * @param casosDeUsoId id del caso de uso
     * @param dto a actualizar
     * @return caso de uso actualizada
     */
    @PUT
    @Path("{casosDeUsoId: \\d+}")
    public CasoDeUsoDetailDTO putCasoDeUso(@PathParam("objetivosId") Long objetivosId, @PathParam("requisitosId") Long requisitosId, @PathParam("casosDeUsoId") Long casosDeUsoId, CasoDeUsoDetailDTO dto) throws WebApplicationException {
        LOGGER.log(Level.INFO, "CasoDeUsoResource putCasoDeUso: input: id:{0} , dto: {1}", new Object[]{casosDeUsoId, dto});
        dto.setId(casosDeUsoId);
        CasoDeUsoEntity e = casoDeUsoLogic.getCasoDeUso(requisitosId, casosDeUsoId);
        if (e == null) {
            throw new WebApplicationException("El recurso /casos/" + casosDeUsoId + " no existe.", 404);
        }
        CasoDeUsoDetailDTO current = new CasoDeUsoDetailDTO(e);
        dto.setCaminosExcepcion(current.getCaminos());
        dto.setCondiciones(current.getCondiciones());
        CasoDeUsoDetailDTO detailDTO = new CasoDeUsoDetailDTO(casoDeUsoLogic.updateCasoDeUso(objetivosId, requisitosId, dto.toEntity()));
        LOGGER.log(Level.INFO, "CasoDeUsoResource putCasoDeUso: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Conexión con el servicio de objetivos para un proyecto.
     * {@link CondicionResource}
     *
     * Este método conecta la ruta de /casos con las rutas de /condiciones que
     * dependen del caso de uso, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los objetivos.
     *
     * @param id El ID del proyecto con respecto al cual se accede al servicio.
     * @return El servicio de autores para ese libro en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
//    @Path("{id: \\d+}/condiciones")
//    public Class<CondicionResource> getCondicionResource(@PathParam("id") Long id) {
//        if (casoDeUsoLogic.getCasoDeUso(id) == null) {
//            throw new WebApplicationException("El recurso /casos/" + id + " no existe.", 404);
//        }
//        return CondicionResource.class;
//    }
//
//    /**
//     * Conexión con el servicio de objetivos para un proyecto.
//     * {@link CaminoResource}
//     *
//     * Este método conecta la ruta de /casos con las rutas de /camino que
//     * dependen del caso de uso, es una redirección al servicio que maneja el
//     * segmento de la URL que se encarga de los objetivos.
//     *
//     * @param id El ID del proyecto con respecto al cual se accede al servicio.
//     * @return El servicio de autores para ese libro en paricular.\
//     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
//     * Error de lógica que se genera cuando no se encuentra el libro.
//     */
//    @Path("{id: \\d+}/camino")
//    public Class<CaminoResource> getCaminoResource(@PathParam("id") Long id) {
//        if (casoDeUsoLogic.getCasoDeUso(id) == null) {
//            throw new WebApplicationException("El recurso /casos/" + id + " no existe.", 404);
//        }
//        return CaminoResource.class;
//    }
//
    private List<CasoDeUsoDetailDTO> listEntity2DTO(List<CasoDeUsoEntity> entityList) {
        List<CasoDeUsoDetailDTO> list = new ArrayList<>();
        for (CasoDeUsoEntity entity : entityList) {
            list.add(new CasoDeUsoDetailDTO(entity));
        }
        return list;
    }
}

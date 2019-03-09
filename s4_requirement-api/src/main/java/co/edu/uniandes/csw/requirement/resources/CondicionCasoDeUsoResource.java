/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CasoDeUsoDTO;
import co.edu.uniandes.csw.requirement.ejb.CasoDeUsoLogic;
import co.edu.uniandes.csw.requirement.ejb.CondicionCasoDeUsoLogic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Sofia Sarmiento
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CondicionCasoDeUsoResource {

    private static final Logger LOGGER = Logger.getLogger(CondicionCasoDeUsoResource.class.getName());

    @Inject
    private CondicionCasoDeUsoLogic condicionCasoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private CasoDeUsoLogic casoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un author dentro de un premio con la informacion que recibe el la
     * URL.
     *
     * @param condicionesId Identificador de el premio que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param casosId Identificador del autor que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link AuthorDTO} - El autor guardado en el premio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @POST
    @Path("{id: \\d+}")
    public CasoDeUsoDTO addCasoDeUso(@PathParam("condiconesId") Long condicionesId, @PathParam("casosId") Long casosId) {
        LOGGER.log(Level.INFO, "CondicionCasoResource addCasoDeUso: input: condicionesID: {0} , casosId: {1}", new Object[]{condicionesId, casosId});
        if (casoLogic.getCasoDeUso(casosId) == null) {
            throw new WebApplicationException("El recurso /casos/" + casosId + " no existe.", 404);
        }
        CasoDeUsoDTO casoDTO = new CasoDeUsoDTO(condicionCasoLogic.addCasoDeUso(condicionesId, casosId));
        LOGGER.log(Level.INFO, "CondicionCasoDeUsoResource addCasoDeUso: output: {0}", casoDTO);
        return casoDTO;
    }
}

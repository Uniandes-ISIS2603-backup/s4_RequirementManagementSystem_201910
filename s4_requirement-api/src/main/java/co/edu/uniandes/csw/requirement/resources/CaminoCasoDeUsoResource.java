/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.CaminoDTO;
import co.edu.uniandes.csw.requirement.ejb.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author davidmanosalva
 */

@Consumes("application/json")
@Produces("application/json")
public class CaminoCasoDeUsoResource {
    
    /*private static final Logger LOGGER = Logger.getLogger(CaminoCasoDeUsoResource.class.getName());

    @Inject
    private CaminoCasoDeUsoLogic caminoCasoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private CaminoLogic caminoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @POST
    @Path("{caminoId: \\d+}")
    public CaminoDTO addAuthor(@PathParam("casoId") Long casosId, @PathParam("caminoId") Long caminosId) {
        LOGGER.log(Level.INFO, "CaminoCasoDeUsoResource addAuthor: input: casosId: {0} , caminosId: {1}", new Object[]{casosId, caminosId});
        if (caminoLogic.getCamino(caminosId) == null) 
        {
            throw new WebApplicationException("El recurso /caminos/" + caminosId + " no existe.", 404);
        }
        CaminoDTO caminoDTO = new CaminoDTO(caminoCasoLogic.addCasoDeUso(caminosId, casosId));
        LOGGER.log(Level.INFO, "CaminoCasoDeUsoResource addAuthor: output: {0}", authorDTO);
        return authorDTO;
    }*/
}

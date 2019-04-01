/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.resources;

import co.edu.uniandes.csw.requirement.dtos.DrsDTO;
import co.edu.uniandes.csw.requirement.dtos.DrsDTODetail;
import co.edu.uniandes.csw.requirement.ejb.DRSLogic;
import co.edu.uniandes.csw.requirement.ejb.RequisitoDrsLogic;
import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Sofia Alvarez
 */
@Path("drs/requisitos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RequisitoDrsResource {
   @Inject
    private RequisitoDrsLogic requisitoDrsLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private DRSLogic drsLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un drs dentro de un requisito con la informacion que recibe el la
     * URL.
     *
     * @param reqsId Identificador de el requisito que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param drsId Identificador del drs que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON - El drs guardado en el requisito.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @POST
    @Path("{drsId: \\d+}")
    public DrsDTO addDrs(@PathParam("requisitosId") Long reqsId, @PathParam("drsId") Long drsId) {
        if (drsLogic.getDRS(drsId) == null) {
            throw new WebApplicationException("El recurso /drs/" + drsId + " no existe.", 404);
        }
        DrsDTO drsDTO = new DrsDTO(requisitoDrsLogic.addDrs(drsId, reqsId));
        return drsDTO;
    }

    /**
     * Busca el drs dentro de el requisito con id asociado.
     *
     * @param reqsId Identificador de el requisito que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON - El drs buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el premio no tiene autor.
     */
    @GET
    public DrsDTODetail getDrs(@PathParam("requisitosId") Long reqsId) {
        DRSEntity drsEntity = requisitoDrsLogic.getDrs(reqsId);
        if (drsEntity == null) {
            throw new WebApplicationException("El recurso /requisitos/" + reqsId + "/drs no existe.", 404);
        }
        DrsDTODetail drsDetailDTO = new DrsDTODetail(drsEntity);
        return drsDetailDTO;
    }

}

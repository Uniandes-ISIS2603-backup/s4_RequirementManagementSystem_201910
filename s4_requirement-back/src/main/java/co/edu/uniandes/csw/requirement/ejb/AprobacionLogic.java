/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase de la logica para la aprobacion
 *
 * @author Sofia Alvarez
 */
@Stateless
public class AprobacionLogic {

    /**
     * Inyección de la persistencia de aprobacion
     */
    @Inject
    private AprobacionPersistence aprobacionPersistence;

    /**
     * Inyección de la persistencia de objetivo
     */
    @Inject
    private ObjetivoPersistence objetivoPersistence;

    /**
     * Inyección de la persistencia de requisito.
     */
    @Inject
    private RequisitoPersistence requisitoPersistence;

    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(AprobacionLogic.class.getName());

    /**
     * Método para la creación de una aprobación
     *
     * @param aprobacion la aprobación a persistir
     * @return la aprobación lista
     * @throws BusinessLogicException
     */
    public AprobacionEntity createAprobacionObjetivo(Long proyectoId, Long objetivoId, AprobacionEntity aprobacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de una aprobacion");
        if (!(aprobacion.getEstado().equals("APROBADO") || aprobacion.getEstado().equals("NO APROBADO") || aprobacion.getEstado().equals("EN REVISION"))) {
            throw new BusinessLogicException("El estado de la aprobación no es valido");
        }
        ObjetivoEntity obj = objetivoPersistence.find(proyectoId, objetivoId);
        if (obj != null) {
            aprobacion.setObjetivo(obj);
        } else {
            throw new BusinessLogicException("El objetivo con id = " + objetivoId + "no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de una aprobacion");
        return aprobacionPersistence.create(aprobacion);
    }

    public AprobacionEntity createAprobacionRequisito(Long objetivoId, Long requisitoId, AprobacionEntity aprobacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de una aprobacion");
        if (!(aprobacion.getEstado().equals("APROBADO") || aprobacion.getEstado().equals("NO APROBADO") || aprobacion.getEstado().equals("EN REVISION"))) {
            throw new BusinessLogicException("El estado de la aprobación no es valido");
        }
        RequisitoEntity req = requisitoPersistence.find(objetivoId, requisitoId);
        if (req != null) {
            aprobacion.setRequisito(req);
        } else {
            throw new BusinessLogicException("El requisito con id = " + requisitoId + "no existe");
        }
        LOGGER.log(Level.INFO, "Termina proceso de creación de una aprobacion");
        return aprobacionPersistence.create(aprobacion);
    }

    public List<AprobacionEntity> getAprobacionesObjetivo(Long proyectoId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las aprobaciones");
        ObjetivoEntity obj = objetivoPersistence.find(proyectoId, objetivoId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las aprobaciones");
        return obj.getAprobaciones();
    }

    public List<AprobacionEntity> getAprobacionesRequisito(Long objetivoId, Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las aprobaciones");
        RequisitoEntity obj = requisitoPersistence.find(objetivoId, requisitoId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos las aprobaciones");
        return obj.getAprobaciones();
    }

    /**
     *
     * Obtener una editorial por medio de su id.
     *
     * @param casoDeUsoId : id de la editorial para ser buscada.
     * @param caminoId
     * @return la editorial solicitada por medio de su id.
     */
    public AprobacionEntity getAprobacionObjetivo(Long objetivoId, Long aprobacionId) {
        AprobacionEntity aprobacionEntity = aprobacionPersistence.findWithObjetivo(objetivoId, aprobacionId);
        if (aprobacionEntity == null) {
            LOGGER.log(Level.SEVERE, "La aprobacion con el id = {0} no existe", aprobacionId);
        }
        return aprobacionEntity;
    }

    public AprobacionEntity getAprobacionRequisito(Long requisitoId, Long aprobacionId) {
        AprobacionEntity aprobacionEntity = aprobacionPersistence.findWithRequisito(requisitoId, aprobacionId);
        if (aprobacionEntity == null) {
            LOGGER.log(Level.SEVERE, "La aprobacion con el id = {0} no existe", aprobacionId);
        }
        return aprobacionEntity;
    }

    /**
     *
     * Actualizar un camino.
     *
     * @param casoDeUsoId: id de la editorial para buscarla en la base de datos.
     * @param aprobacion: editorial con los cambios para ser actualizada, por
     * ejemplo el nombre.
     * @param requisitoId
     * @return la editorial con los cambios actualizados en la base de datos.
     */
    public AprobacionEntity updateAprobacionObjetivo(Long proyectoId, Long objetivoId, AprobacionEntity aprobacion) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el camino con id = {0}", aprobacion.getId());
        ObjetivoEntity obj = objetivoPersistence.find(proyectoId, objetivoId);
        aprobacion.setObjetivo(obj);
        aprobacionPersistence.update(aprobacion);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la aprobacion con id = {0}", aprobacion.getId());
        return aprobacion;
    }

    public AprobacionEntity updateAprobacionRequisito(Long objetivoId, Long requisitoId, AprobacionEntity aprobacion) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el camino con id = {0}", aprobacion.getId());
        RequisitoEntity req = requisitoPersistence.find(objetivoId, requisitoId);
        aprobacion.setRequisito(req);
        aprobacionPersistence.update(aprobacion);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la aprobacion con id = {0}", aprobacion.getId());
        return aprobacion;
    }

    /**
     * Borrar un editorial
     *
     * @param objetivoId
     * @param aprobacionId: id de la editorial a borrar
     * @throws BusinessLogicException Si la editorial a eliminar tiene libros.
     */
    public void deleteAprobacionObjetivo(Long objetivoId, Long aprobacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la aprobacion con id = {0}", aprobacionId);
        AprobacionEntity camino = getAprobacionObjetivo(objetivoId, aprobacionId);
        if (camino == null) {
            throw new BusinessLogicException("La aprobacion con id = " + aprobacionId + " no esta asociado a el objetivo con id = " + objetivoId);
        }
        aprobacionPersistence.delete(camino.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la aprobacion con id = {0}", aprobacionId);
    }
    
    public void deleteAprobacionRequisito(Long requisitoId, Long aprobacionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la aprobacion con id = {0}", aprobacionId);
        AprobacionEntity camino = getAprobacionRequisito(requisitoId, aprobacionId);
        if (camino == null) {
            throw new BusinessLogicException("La aprobacion con id = " + aprobacionId + " no esta asociado a el requisito con id = " + requisitoId);
        }
        aprobacionPersistence.delete(camino.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la aprobacion con id = {0}", aprobacionId);
    }
}

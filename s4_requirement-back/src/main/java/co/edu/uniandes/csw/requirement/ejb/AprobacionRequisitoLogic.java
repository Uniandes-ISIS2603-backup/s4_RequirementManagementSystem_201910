/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
REQUISITOS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Aprobacion y Requisito
 *
 * @requisito ISIS2603
 */
@Stateless
public class AprobacionRequisitoLogic {

    private static final Logger LOGGER = Logger.getLogger(AprobacionRequisitoLogic.class.getName());

    @Inject
    private RequisitoPersistence requisitoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private AprobacionPersistence aprobacionPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un requisito a un aprobacion
     *
     * @param aprobacionsId El id aprobacion a guardar
     * @param requisitosId El id del requisito al cual se le va a guardar el aprobacion.
     * @return El requisito que fue agregado al aprobacion.
     */
    public RequisitoEntity addRequisito(Long requisitosId, Long aprobacionsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el requisito con id = {0} al aprobacion con id = " + aprobacionsId, requisitosId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitosId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionsId);
        aprobacionEntity.setRequisito(requisitoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el requisito con id = {0} al aprobacion con id = " + aprobacionsId, requisitosId);
        return requisitoPersistence.find(requisitosId);
    }

    /**
     *
     * Obtener un aprobacion por medio de su id y el de su requisito.
     *
     * @param aprobacionsId id del aprobacion a ser buscado.
     * @return el requisito solicitada por medio de su id.
     */
    public RequisitoEntity getRequisito(Long aprobacionsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el requisito del aprobacion con id = {0}", aprobacionsId);
        RequisitoEntity requisitoEntity = aprobacionPersistence.find(aprobacionsId).getRequisito();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el requisito del aprobacion con id = {0}", aprobacionsId);
        return requisitoEntity;
    }

    /**
     * Remplazar requisito de un aprobacion
     *
     * @param aprobacionsId el id del aprobacion que se quiere actualizar.
     * @param requisitosId El id del nuebo requisito asociado al aprobacion.
     * @return el nuevo requisito asociado.
     */
    public RequisitoEntity replaceRequisito(Long aprobacionsId, Long requisitosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el requisito del aprobacion aprobacion con id = {0}", aprobacionsId);
        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitosId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionsId);
        aprobacionEntity.setRequisito(requisitoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el requisito con id = {0} al aprobacion con id = " + aprobacionsId, requisitosId);
        return requisitoPersistence.find(requisitosId);
    }

    /**
     * Borrar el requisito de un aprobacion
     *
     * @param aprobacionsId El aprobacion que se desea borrar del requisito.
     * @throws BusinessLogicException si el aprobacion no tiene requisito
     */
    public void removeRequisito(Long aprobacionsId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el requisito del aprobacion con id = {0}", aprobacionsId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionsId);
        if (aprobacionEntity.getRequisito() == null) {
            throw new BusinessLogicException("El aprobacion no tiene requisito");
        }
        RequisitoEntity requisitoEntity = requisitoPersistence.find(aprobacionEntity.getRequisito().getId());
        aprobacionEntity.setRequisito(null);
        requisitoEntity.getAprobaciones().remove(aprobacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el requisito con id = {0} del aprobacion con id = " + aprobacionsId, requisitoEntity.getId());
    }
}

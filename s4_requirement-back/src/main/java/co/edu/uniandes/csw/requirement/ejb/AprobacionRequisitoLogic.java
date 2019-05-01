/*
MIT License

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
 * @requisito Jorgeandresesguerraalarcon
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
     * @param requisitosId El id del requisito al cual se le va a guardar el
     * aprobacion.
     * @return El requisito que fue agregado al aprobacion.
     */
    public RequisitoEntity addRequisito(Long requisitosId, Long aprobacionsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el requisito con id = {0} al aprobacion con id = " + aprobacionsId, requisitosId);

        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitosId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionsId);
        aprobacionEntity.setRequisito(requisitoEntity);
        requisitoEntity.getAprobaciones().add(aprobacionEntity);

        LOGGER.log(Level.INFO, "Termina proceso de asociar el requisito con id = {0} al aprobacion con id = " + aprobacionsId, requisitosId);
        return requisitoPersistence.find(requisitosId);
    }

    /**
     *
     * Servicio 1: Obtener un requisito por medio de su id y el de su aprobacion.
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
     * Servicio 2: Desasocia el requisito y la aprobación.
     *
     * @param objID el id del objetivo a remover
     * @param aprID el id de la aprobación de la cual se remueve la asociación
     */
    public void removeRequisito(Long objID, Long aprID) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un Requisito de la aprobación con id = {0}", aprID);
        RequisitoEntity reqEntity = requisitoPersistence.find(objID);
        AprobacionEntity aprEntity = aprobacionPersistence.find(aprID);
        reqEntity.getAprobaciones().remove(aprEntity);
        aprEntity.setObjetivo(null);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un Requisito de la aprobación con id = {0}", aprID);
    }

}

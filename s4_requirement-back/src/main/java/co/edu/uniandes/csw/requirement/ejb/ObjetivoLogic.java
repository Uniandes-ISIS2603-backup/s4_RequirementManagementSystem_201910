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
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la cascara.
 *
 * @author David Manosalva, Jorge Esguerra
 */
@Stateless
public class ObjetivoLogic {
     /**
     * Logger de la clase
     */
    private static final Logger LOGGER = Logger.getLogger(ObjetivoLogic.class.getName());
    /**
     * Persistencia de la clase
     */
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    @Inject
    private ProyectoPersistence proyectoPersistence;

    /**
     * Metodo que valida la logica de un objetivo
     * @param objetivoEntity Objeto entity para verificar
     * @param proyectosId id del Proyecto el cual sera padre del nuevo Objetivo.
     * @return ObjetivoEntity generado por persistencia si es correcta la logica
     * @throws BusinessLogicException Si no cumple con las reglas de estabilidad, impotancia y descripcion
     */
    public ObjetivoEntity createObjetivo(Long proyectosId, ObjetivoEntity objetivoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del objetivo");
        if (objetivoEntity.getEstabilidad() < 1 || objetivoEntity.getEstabilidad() > 3) {
            throw new BusinessLogicException("La estabilidad debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getImportancia() < 1 || objetivoEntity.getImportancia() > 3) {
            throw new BusinessLogicException("La importancia debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getDescripcion().equals("") || objetivoEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripcion no pueden ser nulo o vacio");
        }
        ProyectoEntity proyecto = proyectoPersistence.find(proyectosId);
        objetivoEntity.setProyecto(proyecto);
        LOGGER.log(Level.INFO, "Termina proceso de creación del objetivo");
        return objetivoPersistence.create(objetivoEntity);
    }

    /**
     * Metodo que llama a la persistencia para obtener los requisitos
     * @param proyectosId el id del proyecto padre de estos objetivos.
     * @return Lista de Objetivos
     */
    public List<ObjetivoEntity> getObjetivos(Long proyectosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los objetivos asociados al proyecto con id = {0}", proyectosId);
        ProyectoEntity proy = proyectoPersistence.find(proyectosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los objetivos asociados al proyecto con id {0}", proyectosId);
        return proy.getObjetivos();
    }

    /**
     * Metodo que llama a la peristencia para obtener un objetivo especifico
     * @param proyectosId
     * @param objetivoId Id del objetivo a obtener
     * @return ObjetivoEntity con el id dado
     */
    public ObjetivoEntity getObjetivo(Long proyectosId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo con id = {0} del proyecto con id = " + proyectosId, objetivoId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(proyectosId, objetivoId);
        if (objetivoEntity == null) {
            LOGGER.log(Level.SEVERE, "El Objetivo con el id = {0} no existe", objetivoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el objetivo con id = {0} dell proyecto con id = " + proyectosId, objetivoId);
        return objetivoEntity;
    }

    /**
     * Metodo que llama a la persistencia para actualizar un objetivo
     * @param proyectosId id del proyecto el cual será padre del objetivo actualizado.
     * @param objetivoEntity Entidad nueva del objetivo
     * @return Objetivo actualizado. 
     * @throws BusinessLogicException Si no cumple con las reglas de estabilidad, impotancia y descripcion
     */
    public ObjetivoEntity updateObjetivo(Long proyectosId, ObjetivoEntity objetivoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el objetivo con id = {0} del proyecto con id = " + proyectosId, objetivoEntity.getId());
        if (objetivoEntity.getEstabilidad() < 1 || objetivoEntity.getEstabilidad() > 3) {
            throw new BusinessLogicException("La estabilidad debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getImportancia() < 1 || objetivoEntity.getImportancia() > 3) {
            throw new BusinessLogicException("La importancia debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getDescripcion().equals("") || objetivoEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripcion no pueden ser nulo o vacio");
        }
        ProyectoEntity p = proyectoPersistence.find(proyectosId);
        objetivoEntity.setProyecto(p);
        objetivoPersistence.update(objetivoEntity);
        System.out.println(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el objetivo con id = {0} del proyecto con id = " + proyectosId, objetivoEntity.getId());
        return objetivoEntity;
    }

    /**
     * Metodo que llama a la persistencia para eliminar el objetivo
     * @param proyectosId el id del proyecto que es padre del objetivo a eliminar.
     * @throws BusinessLogicException Si el objetivo no está asociado al proyecto.
     */
    public void deleteObjetivo(Long proyectosId, Long objetivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el objetivo con id = {0} del proyecto con id =" + proyectosId, objetivoId);
        ObjetivoEntity old = getObjetivo(proyectosId, objetivoId);
        if (old == null) {
            throw new BusinessLogicException("El objetivo con id = " + objetivoId + " no esta asociado a el proyecto con id = " + proyectosId);
        }
        objetivoPersistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el objetivo con id = {0} del proyecto con id = " + proyectosId, objetivoId);
    }
}

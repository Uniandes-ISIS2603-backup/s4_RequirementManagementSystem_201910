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
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la cascara.
 *
 * @author David Manosalva
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

    /**
     * Metodo que valida la logica de un objetivo
     * @param objetivoEntity Objeto entity para verificar
     * @return ObjetivoEntity generado por persistencia si es correcta la logica
     * @throws BusinessLogicException Si no cumple con las reglas de estabilidad, impotancia y descripcion
     */
    public ObjetivoEntity createObjetivo(ObjetivoEntity objetivoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del autor");
        if (objetivoEntity.getEstabilidad() < 1 || objetivoEntity.getEstabilidad() > 3) {
            throw new BusinessLogicException("La estabilidad debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getImportancia() < 1 || objetivoEntity.getImportancia() > 3) {
            throw new BusinessLogicException("La importancia debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getDescripcion().equals("") || objetivoEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripcion no pueden ser nulo o vacio");
        }

        ObjetivoEntity newObjetivoEntity = objetivoPersistence.create(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del autor");
        return newObjetivoEntity;
    }

    /**
     * Metodo que llama a la persistencia para obtener los requisitos
     * @return Lista de Objetivos
     */
    public List<ObjetivoEntity> getObjetivos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los objetivos");
        List<ObjetivoEntity> lista = objetivoPersistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los objetivos");
        return lista;
    }

    /**
     * Metodo que llama a la peristencia para obtener un objetivo especifico
     * @param objetivoId Id del objetivo a obtener
     * @return ObjetivoEntity con el id dado
     */
    public ObjetivoEntity getObjetivo(Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo con id = {0}", objetivoId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivoId);
        if (objetivoEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", objetivoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el autor con id = {0}", objetivoId);
        return objetivoEntity;
    }

    /**
     * Metodo que llama a la persistencia para actualizar un objetivo
     * @param objetivoId Id del objetivo a Actualizar
     * @param objetivoEntity Entidad nueva del objetivo
     * @return Objetivo cambiado
     * @throws BusinessLogicException Si no cumple con las reglas de estabilidad, impotancia y descripcion
     */
    public ObjetivoEntity updateObjetivo(Long objetivoId, ObjetivoEntity objetivoEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el autor con id = {0}", objetivoId);
        if (objetivoEntity.getEstabilidad() < 1 || objetivoEntity.getEstabilidad() > 3) {
            throw new BusinessLogicException("La estabilidad debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getImportancia() < 1 || objetivoEntity.getImportancia() > 3) {
            throw new BusinessLogicException("La importancia debe de ser un valor entre 1 y 3");
        }
        if (objetivoEntity.getDescripcion().equals("") || objetivoEntity.getDescripcion() == null) {
            throw new BusinessLogicException("La descripcion no pueden ser nulo o vacio");
        }
        ObjetivoEntity newObjetivoEntity = objetivoPersistence.update(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", objetivoId);
        return newObjetivoEntity;
    }

    /**
     * Metodo que llama a la persistencia para eliminar el objetivo
     * @param objetivoId Id del objetivo a eliminar
     * @throws BusinessLogicException Si el objetivo no existe
     */
    public void deleteObjetivo(Long objetivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", objetivoId);
        objetivoPersistence.delete(objetivoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", objetivoId);
    }
}

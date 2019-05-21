/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Esguerra, David Manosalva
 */
@Stateless
public class RequisitoLogic {

    private static final Logger LOGGER = Logger.getLogger(RequisitoLogic.class.getName());
    @Inject
    private RequisitoPersistence reqPersistence;

    @Inject
    private ObjetivoPersistence objPersistence;

    /**
     * Método para crear un requisito, validando sus reglas de negocio. Reglas
     * de negocio: 1. La estabilidad de un requisito debe estar en el rango
     * [1,3], discreto. 2. La importancia de un requisito debe estar en el rango
     * [1,3], discreto. 3. El tipo solo puede ser "FUNCIONAL" o "NOFUNCIONAL",
     * no es nullable. 4. La descripción no puede ser null, o vacía.
     *
     * Reglas de negocio a verificar en otras dos clases de lógica con las
     * asociaciones respectivas: 5. Solamente los requisitos con tipo FUNCIONAL
     * pueden tener casos de uso 6. El autor no puede ser nulo (autor es un
     * Stakeholder)
     *
     * @param x la entidad a crear
     * @param objetivoId el id del padre
     * @return la entidad creada y persistida
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    public RequisitoEntity createRequisito(Long proyectosId, Long objetivosId, RequisitoEntity x) throws BusinessLogicException {
        // Aquí ponemos todas las validaciones que hay que hacer al momento de crear un nuevo requisito, según reglas de negocio. 
        LOGGER.log(Level.INFO, "Inicia proceso de creación del requisito");
        if (x.getEstabilidad() < 1 || x.getEstabilidad() > 3) {
            throw new BusinessLogicException("La estabilidad debe de ser un valor entre 1 y 3");
        }
        if (x.getImportancia() < 1 || x.getImportancia() > 3) {
            throw new BusinessLogicException("La importancia debe de ser un valor entre 1 y 3");
        }
        boolean bool1 = x.getTipo().equalsIgnoreCase("FUNCIONAL");
        boolean bool2 = x.getTipo().equalsIgnoreCase("NOFUNCIONAL");
        if (!(bool1 || bool2)) {
            throw new BusinessLogicException("El tipo solo puede ser funcional o no funcional");
        }
        if (x.getDescripcion() == null || x.getDescripcion().equals("")) {
            throw new BusinessLogicException("La descripcion no puede ser vacía o nula");
        }
        ObjetivoEntity obj = objPersistence.find(proyectosId, objetivosId);
        if (obj != null) {
            x.setObjetivo(obj);
        }
        else
            throw new BusinessLogicException("El objetivo con id =" + objetivosId + " no existe para el proyecto con id = " + proyectosId);
        LOGGER.log(Level.INFO, "Termina proceso de creación del requisito");
        x = reqPersistence.create(x);
        return x;
    }

    /**
     * Consulta de todos los requisitos guardados en el sistema.
     *
     * @return Lista con todos los requisitos en el sistema.
     */
    public List<RequisitoEntity> getRequisitos(Long proyectosId, Long objetivosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los requisitos");
        ObjetivoEntity objE = objPersistence.find(proyectosId, objetivosId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los requisitos");
        return objE.getRequisitos();
    }

    /**
     * Consulta de un requisito entity a partir de su identificador unico
     *
     * @param requisitosId, el identificador único del requisito a consultar
     * @return el requisito con dicho identificador.
     */
    public RequisitoEntity getRequisito( Long objetivosId, Long requisitosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el requisito con id = {0} del objetivo con id = " + objetivosId, requisitosId);
        RequisitoEntity x = reqPersistence.find(objetivosId, requisitosId);
        if (x == null) {
            LOGGER.log(Level.SEVERE, "El requisito con el id = {0} no existe", requisitosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el requisito con id = {0}", requisitosId);
        return x;
    }

    public RequisitoEntity updateRequisito(Long proyectosId, Long objetivosId, Long requisitoId, RequisitoEntity x) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el requisito con id = {0}", requisitoId);
        if (x.getEstabilidad() < 1 || x.getEstabilidad() > 3) {
            throw new BusinessLogicException("La estabilidad debe de ser un valor entre 1 y 3");
        }
        if (x.getImportancia() < 1 || x.getImportancia() > 3) {
            throw new BusinessLogicException("La importancia debe de ser un valor entre 1 y 3");
        }
        String[] possibleValues = {"FUNCIONAL", "NOFUNCIONAL"};
        boolean typeIsInSet = false;
        for (String s : possibleValues) {
            if (x.getTipo().equalsIgnoreCase(s)) {
                typeIsInSet = true;
            }
        }
        if (!typeIsInSet) {
            throw new BusinessLogicException("El tipo solo puede ser funcional o no funcional");
        }
        if (x.getDescripcion() == null || x.getDescripcion().equals("")) {
            throw new BusinessLogicException("La descripcion no puede ser vacía o nula");
        }

        ObjetivoEntity obj = objPersistence.find(proyectosId, objetivosId);
        x.setObjetivo(obj);
        reqPersistence.update(x);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el requisito con id = {0}", requisitoId);
        return x;
    }

    public void deleteRequisito(Long proyectosId, Long objetivosId, Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", id);
        List<CasoDeUsoEntity> casosDeUso = getRequisito(objetivosId, id).getCasosDeUso();
        if (casosDeUso != null && !casosDeUso.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el requisito con id = " + id + " porque tiene casos de uso asociados");
        }
        RequisitoEntity old = getRequisito(objetivosId, id);
        if (old == null)
        {
               throw new BusinessLogicException("El requisito con id " + id + " no está asociado con el objetivo con id " + objetivosId);
        }
        reqPersistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", id);
    }

}

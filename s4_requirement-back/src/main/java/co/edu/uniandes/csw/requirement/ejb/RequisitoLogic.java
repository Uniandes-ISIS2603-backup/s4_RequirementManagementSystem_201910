/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorgeandresesguerraalarcon
 */
@Stateless
public class RequisitoLogic {

    private static final Logger LOGGER = Logger.getLogger(RequisitoLogic.class.getName());
    @Inject
    private RequisitoPersistence persistence;

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
     * @return la entidad creada y persistida
     * @throws co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException
     */
    public RequisitoEntity createRequisito(RequisitoEntity x) throws BusinessLogicException {
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
        if (!(bool1||bool2)) 
        {
            throw new BusinessLogicException("El tipo solo puede ser funcional o no funcional");
        }
        if (x.getDescripcion() == null || x.getDescripcion().equals("")) {
            throw new BusinessLogicException("La descripcion no puede ser vacía o nula");
        }

        LOGGER.log(Level.INFO, "Termina proceso de creación del requisito");
        x = persistence.create(x);
        return x;
    }

    /**
     * Consulta de todos los requisitos guardados en el sistema.
     *
     * @return Lista con todos los requisitos en el sistema.
     */
    public List<RequisitoEntity> getRequisitos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los requisitos");
        List<RequisitoEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los requisitos");
        return lista;
    }

    /**
     * Consulta de un requisito entity a partir de su identificador unico
     *
     * @param id, el identificador único del requisito a consultar
     * @return el requisito con dicho identificador.
     */
    public RequisitoEntity getRequisito(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el requisito con id = {0}", id);
        RequisitoEntity x = persistence.find(id);
        if (x == null) {
            LOGGER.log(Level.SEVERE, "El requisito con el id = {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el requisito con id = {0}", id);
        return x;
    }

    public RequisitoEntity updateRequisito(Long id, RequisitoEntity x) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el requisito con id = {0}", id);
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

        RequisitoEntity y = persistence.update(x);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el requisito con id = {0}", id);
        return y;
    }

    public void deleteRequisito(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el autor con id = {0}", id);
        /*List<BookEntity> books = getObjetivo(objetivoId).getBooks();
        if (books != null && !books.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el autor con id = " + objetivoId + " porque tiene books asociados");
        }
        List<PrizeEntity> prizes = getObjetivo(objetivoId).getPrizes();
        if (prizes != null && !prizes.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el autor con id = " + objetivoId + " porque tiene premios asociados");
        }*/
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el autor con id = {0}", id);
    }

}

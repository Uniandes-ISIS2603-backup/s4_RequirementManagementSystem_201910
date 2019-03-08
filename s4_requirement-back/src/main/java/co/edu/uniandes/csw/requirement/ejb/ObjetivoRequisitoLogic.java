/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this requisitolate file, choose Tools | Requisitolates
 * and open the requisitolate in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author David Manosalva
 */
@Stateless
public class ObjetivoRequisitoLogic 
{
   
    private static final Logger LOGGER = Logger.getLogger(ObjetivoRequisitoLogic.class.getName());

    @Inject
    private RequisitoPersistence requisitoPersistence;

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    /**
     * Asocia un Requisito existente a un Objetivo
     *
     * @param objetivoId Identificador de la instancia de Objetivo
     * @param requisitoId Identificador de la instancia de Requisito
     * @return Instancia de RequisitoEntity que fue asociada a Objetivo
     */
    public RequisitoEntity addRequisito(Long objetivoId, Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un requisito al objetivo con id = {0}", objetivoId);
        ObjetivoEntity authorEntity = objetivoPersistence.find(objetivoId);
        RequisitoEntity bookEntity = requisitoPersistence.find(requisitoId);
        bookEntity.getObjetivos().add(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un requisito al objetivo con id = {0}", objetivoId);
        return requisitoPersistence.find(requisitoId);
    }

    /**
     * Obtiene una colección de instancias de RequisitoEntity asociadas a una
     * instancia de Objetivo
     *
     * @param objetivoId Identificador de la instancia de Objetivo
     * @return Colección de instancias de RequisitoEntity asociadas a la instancia de
     * Objetivo
     */
    public List<RequisitoEntity> getRequisitos(Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los requisitos del objetivo con id = {0}", objetivoId);
        return objetivoPersistence.find(objetivoId).getRequisitos();
    }

    /**
     * Obtiene una instancia de RequisitoEntity asociada a una instancia de Objetivo
     *
     * @param objetivoId Identificador de la instancia de Objetivo
     * @param requisitoId Identificador de la instancia de Requisito
     * @return La entidad de Requisito del objetivo
     * @throws BusinessLogicException Si el requisito no está asociado al objetivo
     */
    public RequisitoEntity getRequisito(Long objetivoId, Long requisitoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el requisito con id = {0} del objetivo con id = " + objetivoId, requisitoId);
        List<RequisitoEntity> books = objetivoPersistence.find(objetivoId).getRequisitos();
        RequisitoEntity bookEntity = requisitoPersistence.find(requisitoId);
        int index = books.indexOf(bookEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el requisito con id = {0} del objetivo con id = " + objetivoId, requisitoId);
        if (index >= 0) {
            return books.get(index);
        }
        throw new BusinessLogicException("El requisito no está asociado al objetivo");
    }

    /**
     * Remplaza las instancias de Requisito asociadas a una instancia de Objetivo
     *
     * @param authorId Identificador de la instancia de Objetivo
     * @param books Colección de instancias de RequisitoEntity a asociar a instancia
     * de Objetivo
     * @return Nueva colección de RequisitoEntity asociada a la instancia de Objetivo
     */
    public List<RequisitoEntity> replaceRequisitos(Long authorId, List<RequisitoEntity> books) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los requisitos asocidos al author con id = {0}", authorId);
        ObjetivoEntity authorEntity = objetivoPersistence.find(authorId);
        List<RequisitoEntity> bookList = requisitoPersistence.findAll();
        for (RequisitoEntity requisito : bookList) {
            if (books.contains(requisito)) {
                if (!requisito.getObjetivos().contains(authorEntity)) {
                    requisito.getObjetivos().add(authorEntity);
                }
            } else {
                requisito.getObjetivos().remove(authorEntity);
            }
        }
        authorEntity.setRequisitos(books);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los requisitos asocidos al author con id = {0}", authorId);
        return authorEntity.getRequisitos();
    }

    /**
     * Desasocia un Requisito existente de un Objetivo existente
     *
     * @param objetivoId Identificador de la instancia de Objetivo
     * @param requisitoId Identificador de la instancia de Requisito
     */
    public void removeRequisito(Long objetivoId, Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un requisito del author con id = {0}", objetivoId);
        ObjetivoEntity authorEntity = objetivoPersistence.find(objetivoId);
        RequisitoEntity bookEntity = requisitoPersistence.find(requisitoId);
        bookEntity.getObjetivos().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un requisito del author con id = {0}", objetivoId);
    }
}

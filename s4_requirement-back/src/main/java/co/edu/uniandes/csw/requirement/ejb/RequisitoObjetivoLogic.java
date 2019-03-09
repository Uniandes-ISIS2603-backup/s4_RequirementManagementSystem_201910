/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author jorgeandresesguerraalarcon
 */
@Stateless
public class RequisitoObjetivoLogic 
{
   
    private static final Logger LOGGER = Logger.getLogger(RequisitoObjetivoLogic.class.getName());

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    @Inject
    private RequisitoPersistence requisitoPersistence;

    /**
     * Asocia un Objetivo existente a un Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param objetivoId Identificador de la instancia de Objetivo
     * @return Instancia de ObjetivoEntity que fue asociada a Requisito
     */
    public ObjetivoEntity addObjetivo(Long requisitoId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociarle un objetivo al requisito con id = {0}", requisitoId);
        RequisitoEntity authorEntity = requisitoPersistence.find(requisitoId);
        ObjetivoEntity bookEntity = objetivoPersistence.find(objetivoId);
        bookEntity.getRequisitos().add(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociarle un objetivo al requisito con id = {0}", requisitoId);
        return objetivoPersistence.find(objetivoId);
    }

    /**
     * Obtiene una colección de instancias de ObjetivoEntity asociadas a una
     * instancia de Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @return Colección de instancias de ObjetivoEntity asociadas a la instancia de
     * Requisito
     */
    public List<ObjetivoEntity> getObjetivos(Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los objetivos del requisito con id = {0}", requisitoId);
        return requisitoPersistence.find(requisitoId).getObjetivos();
    }

    /**
     * Obtiene una instancia de ObjetivoEntity asociada a una instancia de Requisito
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param objetivoId Identificador de la instancia de Objetivo
     * @return La entidad de Objetivo del requisito
     * @throws BusinessLogicException Si el objetivo no está asociado al requisito
     */
    public ObjetivoEntity getObjetivo(Long requisitoId, Long objetivoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo con id = {0} del requisito con id = " + requisitoId, objetivoId);
        List<ObjetivoEntity> books = requisitoPersistence.find(requisitoId).getObjetivos();
        ObjetivoEntity bookEntity = objetivoPersistence.find(objetivoId);
        int index = books.indexOf(bookEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el objetivo con id = {0} del requisito con id = " + requisitoId, objetivoId);
        if (index >= 0) {
            return books.get(index);
        }
        throw new BusinessLogicException("El objetivo no está asociado al requisito");
    }

    /**
     * Remplaza las instancias de Objetivo asociadas a una instancia de Requisito
     *
     * @param authorId Identificador de la instancia de Requisito
     * @param books Colección de instancias de ObjetivoEntity a asociar a instancia
     * de Requisito
     * @return Nueva colección de ObjetivoEntity asociada a la instancia de Requisito
     */
    public List<ObjetivoEntity> replaceObjetivos(Long authorId, List<ObjetivoEntity> books) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los objetivos asocidos al author con id = {0}", authorId);
        RequisitoEntity authorEntity = requisitoPersistence.find(authorId);
        List<ObjetivoEntity> bookList = objetivoPersistence.findAll();
        for (ObjetivoEntity objetivo : bookList) {
            if (books.contains(objetivo)) {
                if (!objetivo.getRequisitos().contains(authorEntity)) {
                    objetivo.getRequisitos().add(authorEntity);
                }
            } else {
                objetivo.getRequisitos().remove(authorEntity);
            }
        }
        authorEntity.setObjetivos(books);
        LOGGER.log(Level.INFO, "Termina proceso de reemplazar los objetivos asocidos al author con id = {0}", authorId);
        return authorEntity.getObjetivos();
    }

    /**
     * Desasocia un Objetivo existente de un Requisito existente
     *
     * @param requisitoId Identificador de la instancia de Requisito
     * @param objetivoId Identificador de la instancia de Objetivo
     */
    public void removeObjetivo(Long requisitoId, Long objetivoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un objetivo del author con id = {0}", requisitoId);
        RequisitoEntity authorEntity = requisitoPersistence.find(requisitoId);
        ObjetivoEntity bookEntity = objetivoPersistence.find(objetivoId);
        bookEntity.getRequisitos().remove(authorEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar un objetivo del author con id = {0}", requisitoId);
    }
}

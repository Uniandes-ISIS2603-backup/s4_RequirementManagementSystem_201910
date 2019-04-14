/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase de logica para un caso de uso
 * @author Sofia Sarmiento
 */

@Stateless
public class CasoDeUsoLogic {
    
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(CasoDeUsoLogic.class.getName());
    
    /**
     * Inyección de persistencia de un caso de uso
     */
    @Inject
    private CasoDeUsoPersistence persistence;
    
    /**
    * Metodo que crea la logica de un caso de uso
    * @param casoDeUso caso de uso a persistir
    * @return casoDeUso persistido
    * @throws BusinessLogicException  si ya existe un caso de uso con el mismo nombre.
    */
    public CasoDeUsoEntity createCasoDeUso(CasoDeUsoEntity casoDeUso) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación del caso de uso");
        if(persistence.findByName(casoDeUso.getNombre())!=null){
            throw new BusinessLogicException("Ya existe un caso de uso con el nombre: "+casoDeUso.getNombre());
        }
        persistence.create(casoDeUso);
        LOGGER.log(Level.INFO, "Termina proceso de creación del caso de uso");
        return casoDeUso;
    }
    
    /**
     * Encuentra un caso de uso dado su id
     * @param casoDeUsoId id del caso de uso a encontrar
     * @return caso de uso encontrado
     */
    public CasoDeUsoEntity getCasoDeUso(Long casoDeUsoId){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el caso de uso con id = {0}", casoDeUsoId);
        CasoDeUsoEntity casoDeUso = persistence.find(casoDeUsoId);
        if (casoDeUso == null) {
            LOGGER.log(Level.SEVERE, "El caso de uso con el id = {0} no existe", casoDeUsoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el caso de uso con id = {0}", casoDeUsoId);
        return casoDeUso;
    }
    
    public CasoDeUsoEntity getCasoDeUsoPorNombre(String name){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el caso de uso por nombre", name);
        CasoDeUsoEntity casoDeUso = persistence.findByName(name);
        if (casoDeUso == null) {
            LOGGER.log(Level.SEVERE, "El caso de uso con el nombre no existe", name);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el caso de uso con el nombre", name);
        return casoDeUso;
    }
    
    /**
     * Lista con todos los casos de uso.
     * @return lista con todos los casos de uso
     */
    public List<CasoDeUsoEntity> getCasosDeUso() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los casos de uso");
        List<CasoDeUsoEntity> casosDeUso = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los casos de uso");
        return casosDeUso;
    }
    
    /**
     * Actualiza un caso de uso.
     * @param casoDeUsoId el caso de uso a actualizar
     * @return caso de uso actualizado
     */
     public CasoDeUsoEntity updateCasoDeUso(Long casoDeUsoId, CasoDeUsoEntity casoDeUso) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el caso de uso con id = {0}", casoDeUsoId);
        CasoDeUsoEntity newEntity = persistence.update(casoDeUso);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el caso de uso con id = {0}", casoDeUso.getId());
        return newEntity;
    }
     
    /**
     * Elimina un caso de uso, dado su id
     * @param casoDeUsoId id del caso de uso a eliminar
     */
     public void deleteCasoDeUso(Long casoDeUsoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", casoDeUsoId);
       
        persistence.delete(casoDeUsoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", casoDeUsoId);
    }
}

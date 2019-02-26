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
 *
 * @author Sofia Sarmiento
 */

@Stateless
public class CasoDeUsoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(CasoDeUsoLogic.class.getName());
    
    @Inject
    private CasoDeUsoPersistence persistence;
    
    public CasoDeUsoEntity createCasoDeUso(CasoDeUsoEntity casoDeUso) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación del caso de uso");
        if(persistence.findByName(casoDeUso.getNombre())!=null){
            throw new BusinessLogicException("Ya existe un caso de uso con el nombre: "+casoDeUso.getNombre());
        }
        persistence.create(casoDeUso);
        LOGGER.log(Level.INFO, "Termina proceso de creación del caso de uso");
        return casoDeUso;
    }
    
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
    
    public List<CasoDeUsoEntity> getCasosDeUso() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los casos de uso");
        List<CasoDeUsoEntity> casosDeUso = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los casos de uso");
        return casosDeUso;
    }
    
     public CasoDeUsoEntity updateCasoDeUso(Long casoDeUsoId, CasoDeUsoEntity casoDeUso) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el caso de uso con id = {0}", casoDeUsoId);
        CasoDeUsoEntity newEntity = persistence.update(casoDeUso);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el caso de uso con id = {0}", casoDeUso.getId());
        return newEntity;
    }
     
     public void deleteCasoDeUso(Long casoDeUsoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", casoDeUsoId);
       
        persistence.delete(casoDeUsoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", casoDeUsoId);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.entities.CasoDeUsoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CasoDeUsoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
    private CasoDeUsoPersistence persistenceCaso;
    
    @Inject
    private RequisitoPersistence persistenceReq;
    
    /**
    * Metodo que crea la logica de un caso de uso
    * @param casoDeUso caso de uso a persistir
    * @param requisitoId
    * @return casoDeUso persistido
    * @throws BusinessLogicException  si ya existe un caso de uso con el mismo nombre.
    */
    public CasoDeUsoEntity createCasoDeUso(Long objetivoId, Long requisitoId, CasoDeUsoEntity casoDeUso) throws BusinessLogicException{
        LOGGER.log(Level.INFO, "Inicia proceso de creación del caso de uso");
        RequisitoEntity requisito = persistenceReq.find(objetivoId, requisitoId);
        if(requisito != null)
        {
        if(!requisito.getTipo().equals("FUNCIONAL"))
            throw new BusinessLogicException("Solo se pueden crear casos de uso si el requisito es funcional");
        casoDeUso.setRequisito(requisito);
        }
        else
            throw new BusinessLogicException("El requisito con id = " + requisitoId + " no existe");
        return persistenceCaso.create(casoDeUso);
    }
    
    /**
     * Encuentra un caso de uso dado su id
     * @param casoDeUsoId id del caso de uso a encontrar
     * @param requisitoId
     * @return caso de uso encontrado
     */
    public CasoDeUsoEntity getCasoDeUso(Long requisitoId, Long casoDeUsoId){
        CasoDeUsoEntity casoEntity = persistenceCaso.find(requisitoId, casoDeUsoId);
        if (casoEntity == null) {
            LOGGER.log(Level.SEVERE, "El Objetivo con el id = {0} no existe", casoDeUsoId);
        }
        return casoEntity;
    }
    
    /*public CasoDeUsoEntity getCasoDeUsoPorNombre(String name){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el caso de uso por nombre", name);
        CasoDeUsoEntity casoDeUso = persistence.findByName(name);
        if (casoDeUso == null) {
            LOGGER.log(Level.SEVERE, "El caso de uso con el nombre no existe", name);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el caso de uso con el nombre", name);
        return casoDeUso;
    }*/
    
    /**
     * Lista con todos los casos de uso.
     * @return lista con todos los casos de uso
     */
    public List<CasoDeUsoEntity> getCasosDeUso(Long objetivosId, Long requisitoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los casos de uso");
        RequisitoEntity req = persistenceReq.find(objetivosId, requisitoId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los casos de uso");
        return req.getCasosDeUso();
    }
    
    /**
     * Actualiza un caso de uso.
     * @param casoDeUso el caso de uso a actualizar
     * @param requisitoId
     * @return caso de uso actualizado
     */
     public CasoDeUsoEntity updateCasoDeUso(Long objetivoId, Long requisitoId, CasoDeUsoEntity casoDeUso) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el caso de uso del requisito con id = {0}", requisitoId);
        RequisitoEntity req = persistenceReq.find(objetivoId, requisitoId);
        casoDeUso.setRequisito(req);
        persistenceCaso.update(casoDeUso);   
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el caso de uso con id = {0}", casoDeUso.getId());
        return casoDeUso;
    }
     
    /**
     * Elimina un caso de uso, dado su id
     * @param casoDeUsoId id del caso de uso a eliminar
     * @param requisitoId 
     */
     public void deleteCasoDeUso(Long requisitoId, Long casoDeUsoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el camino con id = {0}", casoDeUsoId);
        List<CaminoEntity> caminos = getCasoDeUso(requisitoId, casoDeUsoId).getCaminos();
        if (caminos != null && !caminos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el caso de uso con id = " + casoDeUsoId + " porque tiene caminos asociados");
        }
        CasoDeUsoEntity caso = getCasoDeUso(requisitoId, casoDeUsoId);
        if (caso == null) {
            throw new BusinessLogicException("El caso de uso con id = " + casoDeUsoId + " no esta asociado a el requisito con id = " + requisitoId);
        }
        persistenceCaso.delete(caso.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar la editorial con id = {0}", casoDeUsoId);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class CambioLogic {
    
    @Inject
    private CambioPersistence cambioPersistence;
    
    @Inject 
    private StakeHolderPersistence shPersistence;
    
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    @Inject 
    private RequisitoPersistence requisitoPersistence;
    
    private static final Logger LOGGER = Logger.getLogger(CambioLogic.class.getName());
    
    public CambioEntity createCambio(CambioEntity cambio) throws BusinessLogicException{
        if(cambio.getFechaYHora() == null){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if(cambio.getTipo() == null){
            throw new BusinessLogicException("El tipo del cambio no puede ser nulo.");
        }
        if(cambio.getObjetivo() != null && cambio.getRequisito() != null){
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
        if(!cambio.getTipo().equals("OBJETIVO")&&!cambio.getTipo().equals("REQUISITO")&&!cambio.getTipo().equals("TEST")){
            throw new BusinessLogicException("El tipo de un cambio debe ser Objetivo o Requisito");
        }
        if(cambio.getTipo().equals("OBJETIVO") && cambio.getObjetivo() == null){
            throw new BusinessLogicException("El cambio debería estar asociado con un objetivo.");
        }
        if(cambio.getTipo().equals("REQUISITO") && cambio.getRequisito()== null){
            throw new BusinessLogicException("El cambio debería estar asociado con un requisito.");
        }
        cambio = cambioPersistence.create(cambio);
        return cambio;
    }
    
    public CambioEntity deleteCambio(Long cambioId){
        CambioEntity cambio = cambioPersistence.delete(cambioId);
        return cambio;
    }
    
    public CambioEntity findCambioById(Long id){
        CambioEntity cambio = cambioPersistence.find(id);
        return cambio;
    }
    
    public List<CambioEntity> findAllCambios(){
        List<CambioEntity> cambios = cambioPersistence.findAll();
        return cambios;
    }
    
    public CambioEntity updateCambio(CambioEntity cambio) throws BusinessLogicException{
        if(cambio.getFechaYHora() == null){
            throw new BusinessLogicException("La fecha y hora del cambio no pueden ser nulos.");
        }
        if(cambio.getTipo() == null){
            throw new BusinessLogicException("El tipo del cambio no puede ser nulo.");
        }
        if(cambio.getObjetivo() != null && cambio.getRequisito() != null){
            throw new BusinessLogicException("El cambio no puede estar asociado a un Objetivo y a un Requisito.");
        }
        if(!cambio.getTipo().equals("OBJETIVO")&&!cambio.getTipo().equals("REQUISITO")&&!cambio.getTipo().equals("TEST")){
            throw new BusinessLogicException("El tipo de un cambio debe ser Objetivo o Requisito");
        }
        if(cambio.getTipo().equals("OBJETIVO") && cambio.getObjetivo() == null){
            throw new BusinessLogicException("El cambio debería estar asociado con un objetivo.");
        }
        if(cambio.getTipo().equals("REQUISITO") && cambio.getRequisito()== null){
            throw new BusinessLogicException("El cambio debería estar asociado con un requisito.");
        }
        cambio = cambioPersistence.update(cambio);
        return cambio;
    }
    
    public CambioEntity changeStakeHolder(Long cambioId, Long shId){
        StakeHolderEntity nuevo = shPersistence.find(shId);
        CambioEntity cambio = cambioPersistence.find(cambioId);
        cambio.setAutor(nuevo);
        return cambio;
    }
    
    public CambioEntity changeObjetivo(Long cambioId, Long objetivoId){
        ObjetivoEntity nuevo = objetivoPersistence.find(objetivoId);
        CambioEntity cambio = cambioPersistence.find(cambioId);
        cambio.setObjetivo(nuevo);
        return cambio;
    }
        
    public CambioEntity changeRequisito(Long cambioId, Long requisitoId){
        RequisitoEntity nuevo = requisitoPersistence.find(requisitoId);
        CambioEntity cambio = cambioPersistence.find(cambioId);
        cambio.setRequisito(nuevo);
        return cambio;
    }
}

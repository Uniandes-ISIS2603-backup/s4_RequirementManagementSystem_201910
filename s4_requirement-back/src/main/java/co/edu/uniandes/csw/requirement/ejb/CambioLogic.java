/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
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
    private CambioPersistence persistence;
    
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
        cambio = persistence.create(cambio);
        return cambio;
    }
    
    public CambioEntity deleteCambio(Long cambioId){
        CambioEntity cambio = persistence.delete(cambioId);
        return cambio;
    }
    
    public CambioEntity findCambioById(Long id){
        CambioEntity cambio = persistence.find(id);
        return cambio;
    }
    
    public List<CambioEntity> findAllCambios(){
        List<CambioEntity> cambios = persistence.findAll();
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
        cambio = persistence.update(cambio);
        return cambio;
    }
}

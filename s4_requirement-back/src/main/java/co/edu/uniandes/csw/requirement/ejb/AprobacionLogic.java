/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class AprobacionLogic {
    
    @Inject
    private AprobacionPersistence persistence;
    
    private static final Logger LOGGER = Logger.getLogger(AprobacionLogic.class.getName());
    
    public AprobacionEntity createAprobacion(AprobacionEntity aprobacion) throws BusinessLogicException{
        if(aprobacion.getTipo() == null){
            throw new BusinessLogicException("El tipo de la aprobación no puede ser nulo.");
        }
        if(aprobacion.getObjetivo() == null && aprobacion.getRequisito() == null){
            throw new BusinessLogicException("La aprobación debe estar asociada a un Objetivo o a un Requisito.");
        }
        if(aprobacion.getObjetivo() != null && aprobacion.getRequisito() != null){
            throw new BusinessLogicException("La aprobación no puede estar asociada a un Objetivo y a un Requisito.");
        }
        if(!aprobacion.getTipo().equals("OBJETIVO")||!aprobacion.getTipo().equals("REQUISITO")){
            throw new BusinessLogicException("El tipo de una aprobación debe ser Objetivo o Requisito");
        }
        if(aprobacion.getTipo().equals("OBJETIVO") && aprobacion.getObjetivo() == null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un objetivo.");
        }
        if(aprobacion.getTipo().equals("REQUISITO") && aprobacion.getObjetivo() == null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un requisito.");
        }
        aprobacion = persistence.create(aprobacion);
        return aprobacion;
    }
    
    public AprobacionEntity deleteAprobacion(Long aprobacionId){
        AprobacionEntity aprobacion = persistence.delete(aprobacionId);
        return aprobacion;
    }
    
    public AprobacionEntity findAprobacionById(Long id){
        AprobacionEntity aprobacion = persistence.find(id);
        return aprobacion;
    }
    
    public List<AprobacionEntity> findAllAprobaciones(){
        List<AprobacionEntity> aprobaciones = persistence.findAll();
        return aprobaciones;
    }
    
    public AprobacionEntity updateAprobacion(AprobacionEntity aprobacion) throws BusinessLogicException{
        if(aprobacion.getTipo() == null){
            throw new BusinessLogicException("El tipo de la aprobación no puede ser nulo.");
        }
        if(aprobacion.getObjetivo() == null && aprobacion.getRequisito() == null){
            throw new BusinessLogicException("La aprobación debe estar asociada a un Objetivo o a un Requisito.");
        }
        if(aprobacion.getObjetivo() != null && aprobacion.getRequisito() != null){
            throw new BusinessLogicException("La aprobación no puede estar asociada a un Objetivo y a un Requisito.");
        }
        if(!aprobacion.getTipo().equals("OBJETIVO")||!aprobacion.getTipo().equals("REQUISITO")){
            throw new BusinessLogicException("El tipo de una aprobación debe ser Objetivo o Requisito");
        }
        if(aprobacion.getTipo().equals("OBJETIVO") && aprobacion.getObjetivo() == null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un objetivo.");
        }
        if(aprobacion.getTipo().equals("REQUISITO") && aprobacion.getObjetivo() == null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un requisito.");
        }
        aprobacion = persistence.update(aprobacion);
        return aprobacion;
    }
}

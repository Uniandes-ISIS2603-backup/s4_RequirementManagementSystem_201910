/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.ObjetivoEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.ObjetivoPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
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
    private AprobacionPersistence aprobacionPersistence;
    
    @Inject 
    private StakeHolderPersistence shPersistence;
    
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    @Inject 
    private RequisitoPersistence requisitoPersistence;
    
    private static final Logger LOGGER = Logger.getLogger(AprobacionLogic.class.getName());
    
    public AprobacionEntity createAprobacion(AprobacionEntity aprobacion) throws BusinessLogicException{
        if(aprobacion.getTipo() == null){
            throw new BusinessLogicException("El tipo de la aprobación no puede ser nulo.");
        }
        if(aprobacion.getObjetivo() != null && aprobacion.getRequisito() != null){
            throw new BusinessLogicException("La aprobación no puede estar asociada a un Objetivo y a un Requisito.");
        }
        if(!aprobacion.getTipo().equals("OBJETIVO")&&!aprobacion.getTipo().equals("REQUISITO")&&!aprobacion.getTipo().equals("TEST")){
            throw new BusinessLogicException("El tipo de una aprobación debe ser Objetivo o Requisito");
        }
        if(aprobacion.getTipo().equals("OBJETIVO") && aprobacion.getObjetivo() == null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un objetivo.");
        }
        if(aprobacion.getTipo().equals("REQUISITO") && aprobacion.getRequisito()== null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un requisito.");
        }
        aprobacion = aprobacionPersistence.create(aprobacion);
        return aprobacion;
    }
    
    public AprobacionEntity deleteAprobacion(Long aprobacionId){
        AprobacionEntity aprobacion = aprobacionPersistence.delete(aprobacionId);
        return aprobacion;
    }
    
    public AprobacionEntity findAprobacionById(Long id){
        AprobacionEntity aprobacion = aprobacionPersistence.find(id);
        return aprobacion;
    }
    
    public List<AprobacionEntity> findAllAprobaciones(){
        List<AprobacionEntity> aprobaciones = aprobacionPersistence.findAll();
        return aprobaciones;
    }
    
    public AprobacionEntity updateAprobacion(AprobacionEntity aprobacion) throws BusinessLogicException{
        if(aprobacion.getTipo() == null){
            throw new BusinessLogicException("El tipo de la aprobación no puede ser nulo.");
        }
        if(aprobacion.getObjetivo() != null && aprobacion.getRequisito() != null){
            throw new BusinessLogicException("La aprobación no puede estar asociada a un Objetivo y a un Requisito.");
        }
        if(!aprobacion.getTipo().equals("OBJETIVO")&&!aprobacion.getTipo().equals("REQUISITO")){
            throw new BusinessLogicException("El tipo de una aprobación debe ser Objetivo o Requisito");
        }
        if(aprobacion.getTipo().equals("OBJETIVO") && aprobacion.getObjetivo() == null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un objetivo.");
        }
        if(aprobacion.getTipo().equals("REQUISITO") && aprobacion.getRequisito()== null){
            throw new BusinessLogicException("La aprobación debería estar asociada con un requisito.");
        }
        aprobacion = aprobacionPersistence.update(aprobacion);
        return aprobacion;
    }
    
    public AprobacionEntity changeStakeHolder(Long aprobacionId, Long shId){
        StakeHolderEntity nuevo = shPersistence.find(shId);
        AprobacionEntity aprobacion = aprobacionPersistence.find(aprobacionId);
        aprobacion.setStakeHolder(nuevo);
        return aprobacion;
    }
    
    public AprobacionEntity changeObjetivo(Long aprobacionId, Long objetivoId){
        ObjetivoEntity nuevo = objetivoPersistence.find(objetivoId);
        AprobacionEntity aprobacion = aprobacionPersistence.find(aprobacionId);
        aprobacion.setObjetivo(nuevo);
        return aprobacion;
    }
        
    public AprobacionEntity changeRequisito(Long aprobacionId, Long requisitoId){
        RequisitoEntity nuevo = requisitoPersistence.find(requisitoId);
        AprobacionEntity aprobacion = aprobacionPersistence.find(aprobacionId);
        aprobacion.setRequisito(nuevo);
        return aprobacion;
    }
}

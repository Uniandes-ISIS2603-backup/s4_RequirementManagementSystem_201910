/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import java.util.List;
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
    
    public AprobacionEntity createCambio(AprobacionEntity aprobacion){
        aprobacion = persistence.create(aprobacion);
        return aprobacion;
    }
    
    public AprobacionEntity deleteCambio(Long aprobacionId){
        AprobacionEntity aprobacion = persistence.delete(aprobacionId);
        return aprobacion;
    }
    
    public AprobacionEntity findCambioById(Long id){
        AprobacionEntity aprobacion = persistence.find(id);
        return aprobacion;
    }
    
    public List<AprobacionEntity> findAllCambios(){
        List<AprobacionEntity> aprobaciones = persistence.findAll();
        return aprobaciones;
    }
    
    public AprobacionEntity updateCambio(AprobacionEntity aprobacion){
        aprobacion = persistence.update(aprobacion);
        return aprobacion;
    }
}

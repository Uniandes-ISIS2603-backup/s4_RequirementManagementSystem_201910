/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import java.util.List;
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
    
    public CambioEntity createCambio(CambioEntity cambio){
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
    
    public CambioEntity updateCambio(CambioEntity cambio){
        cambio = persistence.update(cambio);
        return cambio;
    }
}

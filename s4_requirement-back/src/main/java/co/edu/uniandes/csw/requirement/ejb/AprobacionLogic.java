/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
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
 * Clase de la logica para la aprobacion
 * @author Sofia Alvarez
 */
@Stateless
public class AprobacionLogic {
//    
//   /**
//    * Inyección de la persistencia de aprobacion
//    */
//    @Inject
//    private AprobacionPersistence aprobacionPersistence;
//    
//    /**
//     * Inyección de la persistencia de stakeholder
//     */
//    @Inject 
//    private StakeHolderPersistence shPersistence;
//    
//    /**
//     * Inyección de la persistencia de objetivo
//     */
//    @Inject
//    private ObjetivoPersistence objetivoPersistence;
//    
//    /**
//     * Inyección de la persistencia de requisito.
//     */
//    @Inject 
//    private RequisitoPersistence requisitoPersistence;
//    
//    /**
//     * Consola de JS
//     */
//    private static final Logger LOGGER = Logger.getLogger(AprobacionLogic.class.getName());
//    
//    /**
//     * Método para la creación de una aprobación
//     * @param aprobacion la aprobación a persistir
//     * @return la aprobación lista
//     * @throws BusinessLogicException si el tipo de la aprobación es nulo o si es diferente de "OBJETIVO" || "REQUISITO"
//     */
//    public AprobacionEntity createAprobacion(AprobacionEntity aprobacion) throws BusinessLogicException{
//        if(aprobacion.getTipo() == null){
//            throw new BusinessLogicException("El tipo de la aprobación no puede ser nulo.");
//        }
//        /*
//        if(aprobacion.getObjetivo() != null && aprobacion.getRequisito() != null){
//            throw new BusinessLogicException("La aprobación no puede estar asociada a un Objetivo y a un Requisito.");
//        }*/
//        if(!aprobacion.getTipo().equals("OBJETIVO")&&!aprobacion.getTipo().equals("REQUISITO")){
//            throw new BusinessLogicException("El tipo de una aprobación debe ser Objetivo o Requisito");
//        }
//        /*
//        if(aprobacion.getTipo().equals("OBJETIVO") && aprobacion.getObjetivo() == null){
//            throw new BusinessLogicException("La aprobación debería estar asociada con un objetivo.");
//        }
//        if(aprobacion.getTipo().equals("REQUISITO") && aprobacion.getRequisito()== null){
//            throw new BusinessLogicException("La aprobación debería estar asociada con un requisito.");
//        }
//        */
//        aprobacion = aprobacionPersistence.create(aprobacion);
//        return aprobacion;
//    }
//    
//    /**
//     * Método para eliminar una aprobación
//     * @param aprobacionId id de la aprobacion a eliminar
//     * @return la aprobacion eliminada
//     */
//    public AprobacionEntity deleteAprobacion(Long aprobacionId){
//        AprobacionEntity aprobacion = aprobacionPersistence.delete(aprobacionId);
//        return aprobacion;
//    }
//    
//    /**
//     * Método para encontrar una aprobacion por id.
//     * @param id id de la aprobacion a buscar
//     * @return la aprobacion encontrada
//     */
//    public AprobacionEntity findAprobacionById(Long id){
//        AprobacionEntity aprobacion = aprobacionPersistence.find(id);
//        return aprobacion;
//    }
//    
//    /**
//     * Método que retorna una lista con todas las aprobaciones encontradas
//     * @return lista con todas las aprobaciones encontradas
//     */
//    public List<AprobacionEntity> findAllAprobaciones(){
//        List<AprobacionEntity> aprobaciones = aprobacionPersistence.findAll();
//        return aprobaciones;
//    }
//    
//    /**
//     * Método que actualiza una aprobacion
//     * @param aprobacion es la aprobacion a actualizar
//     * @return la aprobacion actualizada
//     * @throws BusinessLogicException si el tipo de la aprobación es nulo o si es diferente de "OBJETIVO" || "REQUISITO"
//     * o si la aprobacion esta asociada a un obetivo y a un requisito.
//     */
//    public AprobacionEntity updateAprobacion(AprobacionEntity aprobacion) throws BusinessLogicException{
//        if(aprobacion.getTipo() == null){
//            throw new BusinessLogicException("El tipo de la aprobación no puede ser nulo.");
//        }
//        if(aprobacion.getObjetivo() != null && aprobacion.getRequisito() != null){
//            throw new BusinessLogicException("La aprobación no puede estar asociada a un Objetivo y a un Requisito.");
//        }
//        if(!aprobacion.getTipo().equals("OBJETIVO")&&!aprobacion.getTipo().equals("REQUISITO")){
//            throw new BusinessLogicException("El tipo de una aprobación debe ser Objetivo o Requisito");
//        }
//        /*
//        if(aprobacion.getTipo().equals("OBJETIVO") && aprobacion.getObjetivo() == null){
//            throw new BusinessLogicException("La aprobación debería estar asociada con un objetivo.");
//        }
//        if(aprobacion.getTipo().equals("REQUISITO") && aprobacion.getRequisito()== null){
//            throw new BusinessLogicException("La aprobación debería estar asociada con un requisito.");
//        }
//        */ 
//        aprobacion = aprobacionPersistence.update(aprobacion);
//        return aprobacion;
//    }
//    
//   /**
//    * Cambia al stakeholder de una aprobacion
//    * @param aprobacionId id de la aprobacion
//    * @param shId stakeholder a cambiar
//    * @return la aprobacion con el nuevo stakeholder
//    */
//   
//    public AprobacionEntity changeStakeHolder(Long aprobacionId, Long shId){
//        StakeHolderEntity nuevo = shPersistence.find(shId);
//        AprobacionEntity aprobacion = aprobacionPersistence.find(aprobacionId);
//        aprobacion.setStakeHolder(nuevo);
//        return aprobacion;
//    }
//    
//   /**
//    * Cambia el objetivo de una aprobación
//    * @param aprobacionId id de la aprobación
//    * @param objetivoId objetivo a cambiar
//    * @return la aprobación con el nuevo objetivo.
//    */
//    //TODO Revisar el cambio de Objetivo
////    public AprobacionEntity changeObjetivo(Long aprobacionId, Long objetivoId){
////        ObjetivoEntity nuevo = objetivoPersistence.find(objetivoId);
////        AprobacionEntity aprobacion = aprobacionPersistence.find(aprobacionId);
////        aprobacion.setObjetivo(nuevo);
////        return aprobacion;
////    }
//      
//    /**
//    * Cambia el requisito de una aprobación
//    * @param aprobacionId id de la aprobación
//    * @param requisitoId requisito a cambiar
//    * @return la aprobación con el nuevo requisito.
//    */
//    public AprobacionEntity changeRequisito(Long aprobacionId, Long requisitoId){
//        RequisitoEntity nuevo = requisitoPersistence.find(requisitoId);
//        AprobacionEntity aprobacion = aprobacionPersistence.find(aprobacionId);
//        aprobacion.setRequisito(nuevo);
//        return aprobacion;
//    }
}

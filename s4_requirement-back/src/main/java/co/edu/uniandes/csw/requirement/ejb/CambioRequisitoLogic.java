/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.ejb;

import co.edu.uniandes.csw.requirement.entities.CambioEntity;
import co.edu.uniandes.csw.requirement.entities.RequisitoEntity;
import co.edu.uniandes.csw.requirement.persistence.CambioPersistence;
import co.edu.uniandes.csw.requirement.persistence.RequisitoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase para la relacion entre requisito y cambio 
 * @author sofiaalvarez
 */
@Stateless
public class CambioRequisitoLogic {
    

    @Inject
    private RequisitoPersistence requisitoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private CambioPersistence cambioPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un requisito a un cambio
     *
     * @param camsId El id cambio a guardar
     * @param requisitosId El id del requisito al cual se le va a guardar el
     * cambio.
     * @return El requisito que fue agregado al cambio.
     */
    public RequisitoEntity addRequisito(Long requisitosId, Long camsId) {

        RequisitoEntity requisitoEntity = requisitoPersistence.find(requisitosId);
        CambioEntity cambioEntity = cambioPersistence.find(camsId);
        cambioEntity.setRequisito(requisitoEntity);
        requisitoEntity.getCambios().add(cambioEntity);

        return requisitoPersistence.find(requisitosId);
    }

    /**
     *
     * Servicio 1: Obtener un requisito por medio de su id y el de su cambio.
     *
     * @param camId id del cambio a ser buscado.
     * @return el requisito solicitada por medio de su id.
     */
    public RequisitoEntity getRequisito(Long camId) {
        RequisitoEntity requisitoEntity = cambioPersistence.find(camId).getRequisito();
        return requisitoEntity;
    }

    /**
     * Servicio 2: Desasocia el requisito y el cambio.
     *
     * @param objID el id del objetivo a remover
     * @param cID el id del cambio del cual se remueve la asociación
     */
    public void removeRequisito(Long objID, Long cID) {
        RequisitoEntity reqEntity = requisitoPersistence.find(objID);
        CambioEntity aprEntity = cambioPersistence.find(cID);
        reqEntity.getCambios().remove(aprEntity);
        aprEntity.setObjetivo(null);
    }

    
}

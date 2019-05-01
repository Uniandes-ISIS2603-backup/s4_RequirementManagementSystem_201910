/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.requirement.ejb;
        
import co.edu.uniandes.csw.requirement.entities.AprobacionEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.AprobacionPersistence;
import co.edu.uniandes.csw.requirement.persistence.StakeHolderPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Aprobacion e StakeHolder.
 *
 * @author jorgeandresesguerralarcon & Sofia Alvarez
 */
@Stateless
public class AprobacionStakeHolderLogic {
    /**
     * Consola de JS
     */
    private static final Logger LOGGER = Logger.getLogger(AprobacionStakeHolderLogic.class.getName());

    /**
     * Inyeccion de dependencias de la aprobacion
     */
    @Inject
    private AprobacionPersistence aprobacionPersistence;

     /**
     * Inyeccion de dependencias del stakeholder
     */
    @Inject
    private StakeHolderPersistence stakeholderPersistence;

    /**
     * Añade la relacion con stakeholder de una aprobacion.
     *
     * @param aprobacionsId id del aprobacion que se quiere actualizar.
     * @param stakeholdersId El id de la stakeholder que se será del aprobacion.
     * @return El StakeHolder agregado a la relación
     */
    public StakeHolderEntity addStakeHolder(Long aprobacionsId, Long stakeholdersId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar stakeholder con id = {0}" +  "a aprobacion con id" + aprobacionsId, stakeholdersId);
        StakeHolderEntity stakeholderEntity = stakeholderPersistence.find(stakeholdersId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionsId);
        aprobacionEntity.setStakeHolder(stakeholderEntity);
        
        
        LOGGER.log(Level.INFO, "Termina proceso de asociar aprobacion con id = {0}", aprobacionsId);
        return stakeholderEntity;
    }

    /**
     * Borrar un aprobacion de una stakeholder. Este metodo se utiliza para borrar la
     * relacion de un aprobacion.
     *
     * @param aprobacionsId El aprobacion que se desea borrar de la stakeholder.
     */
    public void removeStakeHolder(Long aprobacionsId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la StakeHolder del aprobacion con id = {0}", aprobacionsId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionsId);
        StakeHolderEntity stakeholderEntity = stakeholderPersistence.find(aprobacionEntity.getStakeHolder().getId());
        aprobacionEntity.setStakeHolder(null);
        
        LOGGER.log(Level.INFO, "Termina proceso de borrar la StakeHolder del aprobacion con id = {0}", aprobacionEntity.getId());
    }
    
    /**
     * Obtener un stakeholder asociado a la aprobación
     * @param  id de la aprobacion
     * @return el stakeholder
     */
    
    public StakeHolderEntity getStakeholder(Long aprobacionId)     
    {
        LOGGER.log(Level.INFO, "Inicia proceso de obtener el StakeHolder de la aprobacion con id = {0}", aprobacionId);
        AprobacionEntity aprobacionEntity = aprobacionPersistence.find(aprobacionId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la StakeHolder del aprobacion con id = {0}", aprobacionEntity.getId());
        return aprobacionEntity.getStakeHolder();
    }
    
}

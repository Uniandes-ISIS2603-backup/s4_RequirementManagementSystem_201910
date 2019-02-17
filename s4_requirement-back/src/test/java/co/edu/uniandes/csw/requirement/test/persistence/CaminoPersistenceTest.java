/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.CaminoEntity;
import co.edu.uniandes.csw.requirement.persistence.CaminoPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sofia Alvarez
 */

@RunWith(Arquillian.class)
public class CaminoPersistenceTest {
    
    @Inject
    private CaminoPersistence cp;
    
    @PersistenceContext
    private EntityManager em;
    
    @Deployment
    public static JavaArchive deployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CaminoEntity.class.getPackage())
                .addPackage(CaminoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createCaminoTest(){
        PodamFactory factory = new PodamFactoryImpl();
        CaminoEntity newCaminoEntity = factory.manufacturePojo(CaminoEntity.class);
        CaminoEntity ce = cp.create(newCaminoEntity);
        
        Assert.assertNotNull(ce);
        
        CaminoEntity entity = em.find(CaminoEntity.class, ce.getId());
        Assert.assertEquals(newCaminoEntity.getDescripcionPaso(), entity.getDescripcionPaso());
}
}




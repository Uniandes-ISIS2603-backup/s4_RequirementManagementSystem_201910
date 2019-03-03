/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.persistence;

import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Sofia Alvarez
 */
@RunWith(Arquillian.class)

public class DRSPersistenceTest {

    @Inject
    private DRSPersistence drsep;

    @PersistenceContext
    private EntityManager em;
    
     @Inject
    UserTransaction utx;
     
     private List<DRSEntity> data = new ArrayList<DRSEntity>();

    @Deployment
    public static JavaArchive deployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DRSEntity.class.getPackage())
                .addPackage(DRSPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from DRSEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            DRSEntity entity = factory.manufacturePojo(DRSEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }


    @Test
    public void createDRSTest() {

        PodamFactory factory = new PodamFactoryImpl();
        DRSEntity newDrse = factory.manufacturePojo(DRSEntity.class);
        DRSEntity drse = drsep.create(newDrse);

        Assert.assertNotNull(drse);
        DRSEntity entity = em.find(DRSEntity.class, drse.getId());
        Assert.assertEquals(newDrse.getVersion(), entity.getVersion());

    }
    
    /**
     * Prueba para consultar la lista de DRSs.
     */
    @Test
    public void getDRSsTest() {
        List<DRSEntity> list = drsep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (DRSEntity ent : list) {
            boolean found = false;
            for (DRSEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un DRS.
     */
    @Test
    public void getDRSTest() {
        DRSEntity entity = data.get(0);
        DRSEntity newEntity = drsep.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para eliminar un DRS.
     */
    @Test
    public void deleteEditorialTest() {
        DRSEntity entity = data.get(0);
        drsep.delete(entity.getId());
        DRSEntity deleted = em.find(DRSEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un DRS.
     */
    @Test
    public void updateEditorialTest() {
        DRSEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        DRSEntity newEntity = factory.manufacturePojo(DRSEntity.class);

        newEntity.setId(entity.getId());

        drsep.update(newEntity);

        DRSEntity resp = em.find(DRSEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    /**
     * Prueba para consultar una Editorial por nombre.
     */
    @Test
    public void findDRSByVersionTest() {
        DRSEntity entity = data.get(0);
        DRSEntity newEntity = drsep.findByVersion(entity.getVersion());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getVersion(), newEntity.getVersion());

        newEntity = drsep.findByVersion(null);
        Assert.assertNull(newEntity);
    }


}

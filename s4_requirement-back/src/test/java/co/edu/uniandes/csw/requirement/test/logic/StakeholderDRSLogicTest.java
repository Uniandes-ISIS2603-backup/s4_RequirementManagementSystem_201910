/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.StakeholderDRSLogic;
import co.edu.uniandes.csw.requirement.entities.DRSEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.persistence.DRSPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
public class StakeholderDRSLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private StakeholderDRSLogic stakeholderDrsLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<DRSEntity> data = new ArrayList<DRSEntity>();

    private List<StakeHolderEntity> shData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DRSEntity.class.getPackage())
                .addPackage(StakeholderDRSLogic.class.getPackage())
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
        em.createQuery("delete from StakeHolderEntity").executeUpdate();
        em.createQuery("delete from DRSEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            StakeHolderEntity requis = factory.manufacturePojo(StakeHolderEntity.class);
            em.persist(requis);
            shData.add(requis);
        }
        for (int i = 0; i < 3; i++) {
            DRSEntity entity = factory.manufacturePojo(DRSEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                shData.get(i).setDrs(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Requisito existente a un Drs.
     */
    @Test
    public void addDrsTest() {
        DRSEntity entity = data.get(0);
        StakeHolderEntity shEntity = shData.get(1);
        DRSEntity response = stakeholderDrsLogic.addDrs(entity.getId(), shEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Drs.
     */
    @Test
    public void getDrsTest() {
        StakeHolderEntity entity = shData.get(0);
        DRSEntity resultEntity = stakeholderDrsLogic.getDrs(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getDrs().getId(), resultEntity.getId());
    }
    
    
    
}

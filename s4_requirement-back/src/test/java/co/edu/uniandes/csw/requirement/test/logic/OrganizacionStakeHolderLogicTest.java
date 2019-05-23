/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.OrganizacionLogic;
import co.edu.uniandes.csw.requirement.ejb.OrganizacionStakeHolderLogic;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.entities.StakeHolderEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.OrganizacionPersistence;
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
 * @author Mateo Pedroza
 */
@RunWith(Arquillian.class)
public class OrganizacionStakeHolderLogicTest {
//    
//    private PodamFactory factory = new PodamFactoryImpl();
//
//    @Inject
//    private OrganizacionLogic OrganizacionLogic;
//    @Inject
//    private OrganizacionStakeHolderLogic OrganizacionStakeHolderLogic;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Inject
//    private UserTransaction utx;
//
//    private List<OrganizacionEntity> data = new ArrayList<OrganizacionEntity>();
//
//    private List<StakeHolderEntity> stakeholdersData = new ArrayList();
//
//    /**
//     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
//     * El jar contiene las clases, el descriptor de la base de datos y el
//     * archivo beans.xml para resolver la inyección de dependencias.
//     */
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addPackage(OrganizacionEntity.class.getPackage())
//                .addPackage(OrganizacionLogic.class.getPackage())
//                .addPackage(OrganizacionPersistence.class.getPackage())
//                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
//                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
//    }
//
//    /**
//     * Configuración inicial de la prueba.
//     */
//    @Before
//    public void configTest() {
//        try {
//            utx.begin();
//            clearData();
//            insertData();
//            utx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                utx.rollback();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Limpia las tablas que están implicadas en la prueba.
//     */
//    private void clearData() {
//        em.createQuery("delete from StakeHolderEntity").executeUpdate();
//        em.createQuery("delete from OrganizacionEntity").executeUpdate();
//    }
//
//    /**
//     * Inserta los datos iniciales para el correcto funcionamiento de las
//     * pruebas.
//     */
//    private void insertData() {
//        for (int i = 0; i < 3; i++) {
//            StakeHolderEntity stakeholder = factory.manufacturePojo(StakeHolderEntity.class);
//            em.persist(stakeholder);
//            stakeholdersData.add(stakeholder);
//        }
//        for (int i = 0; i < 3; i++) {
//            OrganizacionEntity entity = factory.manufacturePojo(OrganizacionEntity.class);
//            em.persist(entity);
//            data.add(entity);
//            if (i == 0) {
//                stakeholdersData.get(i).setOrganizacion(entity);
//            }
//        }
//    }
//
//    /**
//     * Prueba para asociar un stakeholder existente a un organizacion.
//     */
//    @Test
//    public void addstakeholderTest() {
//        OrganizacionEntity entity = data.get(0);
//        StakeHolderEntity StakeHolderEntity = stakeholdersData.get(1);
//        StakeHolderEntity response = OrganizacionStakeHolderLogic.addStakeHolder(StakeHolderEntity.getId(), entity.getId());
//
//        Assert.assertNotNull(response);
//        Assert.assertEquals(StakeHolderEntity.getId(), response.getId());
//    }
//
//    /**
//     * Prueba para obtener una colección de instancias de stakeholder asociadas a una
//     * instancia organizacion.
//     */
//    @Test
//    public void getstakeholderTest() {
//        List<StakeHolderEntity> list = OrganizacionStakeHolderLogic.getStakeHolders(data.get(0).getId());
//        Assert.assertEquals(1, list.size());
//    }
//
//    /**
//     * Prueba para obtener una instancia de stakeholder asociada a una instancia
//     * organizacion.
//     *
//     * @throws co.edu.uniandes.csw.stakeholdertore.exceptions.BusinessLogicException
//     */
//    @Test
//    public void getStakeHolderTest() throws BusinessLogicException {
//        OrganizacionEntity entity = data.get(0);
//        StakeHolderEntity StakeHolderEntity = stakeholdersData.get(0);
//        StakeHolderEntity response = OrganizacionStakeHolderLogic.getStakeHolder(entity.getId(), StakeHolderEntity.getId());
//
//        Assert.assertEquals(StakeHolderEntity.getId(), response.getId());
//        Assert.assertEquals(StakeHolderEntity.getNombre(), response.getNombre());
//        Assert.assertEquals(StakeHolderEntity.getTipo(), response.getTipo());
//        Assert.assertEquals(StakeHolderEntity.getOrganizacion(), response.getOrganizacion());
//    }
//
//    /**
//     * Prueba para obtener una instancia de stakeholder asociada a una instancia
//     * organizacion que no le pertenece.
//     *
//     * @throws co.edu.uniandes.csw.stakeholdertore.exceptions.BusinessLogicException
//     */
//    @Test(expected = BusinessLogicException.class)
//    public void getStakeHolderNoAsociadoTest() throws BusinessLogicException {
//        OrganizacionEntity entity = data.get(0);
//        StakeHolderEntity StakeHolderEntity = stakeholdersData.get(1);
//        OrganizacionStakeHolderLogic.getStakeHolder(entity.getId(), StakeHolderEntity.getId());
//    }
//
//    /**
//     * Prueba para remplazar las instancias de stakeholder asociadas a una instancia
//     * de organizacion.
//     */
//    @Test
//    public void replacestakeholderTest() {
//        OrganizacionEntity entity = data.get(0);
//        List<StakeHolderEntity> list = stakeholdersData.subList(1, 3);
//        OrganizacionStakeHolderLogic.replaceStakeHolders(entity.getId(), list);
//
//        entity = OrganizacionLogic.getOrganizacion(entity.getId());
//        Assert.assertFalse(entity.getStakeHolders().contains(stakeholdersData.get(0)));
//        Assert.assertTrue(entity.getStakeHolders().contains(stakeholdersData.get(1)));
//        Assert.assertTrue(entity.getStakeHolders().contains(stakeholdersData.get(2)));
//    }
//    
}

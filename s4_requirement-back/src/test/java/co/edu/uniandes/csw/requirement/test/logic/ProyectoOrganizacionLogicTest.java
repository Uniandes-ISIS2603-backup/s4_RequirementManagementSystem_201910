/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.ProyectoOrganizacionLogic;
import co.edu.uniandes.csw.requirement.ejb.OrganizacionLogic;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.OrganizacionEntity;
import co.edu.uniandes.csw.requirement.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.requirement.persistence.ProyectoPersistence;
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
 * @proyecto Mateo Pedroza
 */
@RunWith(Arquillian.class)
public class ProyectoOrganizacionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProyectoOrganizacionLogic proyectoOrganizacionLogic;

    @Inject
    private OrganizacionLogic organizacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ProyectoEntity proyecto = new ProyectoEntity();
    private List<OrganizacionEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProyectoEntity.class.getPackage())
                .addPackage(OrganizacionEntity.class.getPackage())
                .addPackage(ProyectoOrganizacionLogic.class.getPackage())
                .addPackage(ProyectoPersistence.class.getPackage())
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
        em.createQuery("delete from ProyectoEntity").executeUpdate();
        em.createQuery("delete from OrganizacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        proyecto = factory.manufacturePojo(ProyectoEntity.class);
        proyecto.setId(1L);
        proyecto.setOrganizaciones(new ArrayList<>());
        em.persist(proyecto);

        for (int i = 0; i < 3; i++) {
            OrganizacionEntity entity = factory.manufacturePojo(OrganizacionEntity.class);
            entity.setProyectos(new ArrayList<>());
            entity.getProyectos().add(proyecto);
            em.persist(entity);
            data.add(entity);
            proyecto.getOrganizaciones().add(entity);
        }
    }

    /**
     * Prueba para asociar un proyecto a un organizacion.
     *
     *
     * @throws co.edu.uniandes.csw.organizacionestore.exceptions.BusinessLogicException
     */
    @Test
    public void addOrganizacionTest() throws BusinessLogicException {
        OrganizacionEntity newOrganizacion = factory.manufacturePojo(OrganizacionEntity.class);
        organizacionLogic.createOrganizacion(newOrganizacion);
        OrganizacionEntity organizacionEntity = proyectoOrganizacionLogic.addOrganizacion(proyecto.getId(), newOrganizacion.getId());
        Assert.assertNotNull(organizacionEntity);

        Assert.assertEquals(organizacionEntity.getId(), newOrganizacion.getId());
        Assert.assertEquals(organizacionEntity.getNombre(), newOrganizacion.getNombre());
        Assert.assertEquals(organizacionEntity.getSector(), newOrganizacion.getSector());

        OrganizacionEntity lastOrganizacion = proyectoOrganizacionLogic.getOrganizacion(proyecto.getId(), newOrganizacion.getId());

        Assert.assertEquals(lastOrganizacion.getId(), newOrganizacion.getId());
        Assert.assertEquals(lastOrganizacion.getNombre(), newOrganizacion.getNombre());
        Assert.assertEquals(lastOrganizacion.getSector(), newOrganizacion.getSector());
    }

    /**
     * Prueba para consultar la lista de Organizaciones de un proyecto.
     */
    @Test
    public void getOrganizacionesTest() {
        List<OrganizacionEntity> organizacionEntities = proyectoOrganizacionLogic.getOrganizaciones(proyecto.getId());

        Assert.assertEquals(data.size(), organizacionEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(organizacionEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un organizacion de un proyecto.
     *
     * @throws co.edu.uniandes.csw.organizacionestore.exceptions.BusinessLogicException
     */
    @Test
    public void getOrganizacionTest() throws BusinessLogicException {
        OrganizacionEntity organizacionEntity = data.get(0);
        OrganizacionEntity organizacion = proyectoOrganizacionLogic.getOrganizacion(proyecto.getId(), organizacionEntity.getId());
        Assert.assertNotNull(organizacion);

        Assert.assertEquals(organizacionEntity.getId(), organizacion.getId());
        Assert.assertEquals(organizacionEntity.getNombre(), organizacion.getNombre());
        Assert.assertEquals(organizacionEntity.getSector(), organizacion.getSector());
    }

    /**
     * Prueba para actualizar los organizaciones de un proyecto.
     *
     * @throws co.edu.uniandes.csw.organizacionestore.exceptions.BusinessLogicException
     */
    @Test
    public void replaceOrganizacionesTest() throws BusinessLogicException {
        List<OrganizacionEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OrganizacionEntity entity = factory.manufacturePojo(OrganizacionEntity.class);
            entity.setProyectos(new ArrayList<>());
            entity.getProyectos().add(proyecto);
            organizacionLogic.createOrganizacion(entity);
            nuevaLista.add(entity);
        }
        proyectoOrganizacionLogic.replaceOrganizaciones(proyecto.getId(), nuevaLista);
        List<OrganizacionEntity> organizacionEntities = proyectoOrganizacionLogic.getOrganizaciones(proyecto.getId());
        for (OrganizacionEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(organizacionEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un organizacion con un proyecto.
     *
     */
    @Test
    public void removeOrganizacionTest() {
        for (OrganizacionEntity organizacion : data) {
            proyectoOrganizacionLogic.removeOrganizacion(proyecto.getId(), organizacion.getId());
        }
        Assert.assertTrue(proyectoOrganizacionLogic.getOrganizaciones(proyecto.getId()).isEmpty());
    }
}

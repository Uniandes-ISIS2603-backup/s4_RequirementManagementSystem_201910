/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.requirement.test.logic;

import co.edu.uniandes.csw.requirement.ejb.ProyectoUsuarioLogic;
import co.edu.uniandes.csw.requirement.ejb.UsuarioLogic;
import co.edu.uniandes.csw.requirement.entities.ProyectoEntity;
import co.edu.uniandes.csw.requirement.entities.UsuarioEntity;
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
public class ProyectoUsuarioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProyectoUsuarioLogic proyectoUsuarioLogic;

    @Inject
    private UsuarioLogic usuarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private ProyectoEntity proyecto = new ProyectoEntity();
    private List<UsuarioEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProyectoEntity.class.getPackage())
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(ProyectoUsuarioLogic.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        proyecto = factory.manufacturePojo(ProyectoEntity.class);
        proyecto.setId(1L);
        proyecto.setUsuarios(new ArrayList<>());
        em.persist(proyecto);

        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            entity.setProyectos(new ArrayList<>());
            entity.getProyectos().add(proyecto);
            em.persist(entity);
            data.add(entity);
            proyecto.getUsuarios().add(entity);
        }
    }

    /**
     * Prueba para asociar un proyecto a un usuario.
     *
     *
     * @throws co.edu.uniandes.csw.usuariostore.exceptions.BusinessLogicException
     */
    @Test
    public void addUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newUsuario = factory.manufacturePojo(UsuarioEntity.class);
        usuarioLogic.createUsuario(newUsuario);
        UsuarioEntity usuarioEntity = proyectoUsuarioLogic.addUsuario(proyecto.getId(), newUsuario.getId());
        Assert.assertNotNull(usuarioEntity);

        Assert.assertEquals(usuarioEntity.getId(), newUsuario.getId());
        Assert.assertEquals(usuarioEntity.getTipo(), newUsuario.getTipo());
        Assert.assertEquals(usuarioEntity.getUsuario(), newUsuario.getUsuario());
        Assert.assertEquals(usuarioEntity.getContrasena(), newUsuario.getContrasena());

        UsuarioEntity lastUsuario = proyectoUsuarioLogic.getUsuario(proyecto.getId(), newUsuario.getId());

        Assert.assertEquals(lastUsuario.getId(), newUsuario.getId());
        Assert.assertEquals(lastUsuario.getTipo(), newUsuario.getTipo());
        Assert.assertEquals(lastUsuario.getContrasena(), newUsuario.getContrasena());
        Assert.assertEquals(lastUsuario.getUsuario(), newUsuario.getUsuario());
    }

    /**
     * Prueba para consultar la lista de Usuarios de un proyecto.
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> usuarioEntities = proyectoUsuarioLogic.getUsuarios(proyecto.getId());

        Assert.assertEquals(data.size(), usuarioEntities.size());

        for (int i = 0; i < data.size(); i++) {
            Assert.assertTrue(usuarioEntities.contains(data.get(0)));
        }
    }

    /**
     * Prueba para cpnsultar un usuario de un proyecto.
     *
     * @throws co.edu.uniandes.csw.usuariostore.exceptions.BusinessLogicException
     */
    @Test
    public void getUsuarioTest() throws BusinessLogicException {
        UsuarioEntity usuarioEntity = data.get(0);
        UsuarioEntity usuario = proyectoUsuarioLogic.getUsuario(proyecto.getId(), usuarioEntity.getId());
        Assert.assertNotNull(usuario);

        Assert.assertEquals(usuarioEntity.getId(), usuario.getId());
        Assert.assertEquals(usuarioEntity.getUsuario(), usuario.getUsuario());
        Assert.assertEquals(usuarioEntity.getContrasena(), usuario.getContrasena());
        Assert.assertEquals(usuarioEntity.getTipo(), usuario.getTipo());
    }

    /**
     * Prueba para actualizar los usuarios de un proyecto.
     *
     * @throws co.edu.uniandes.csw.usuariostore.exceptions.BusinessLogicException
     */
    @Test
    public void replaceUsuariosTest() throws BusinessLogicException {
        List<UsuarioEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            entity.setProyectos(new ArrayList<>());
            entity.getProyectos().add(proyecto);
            usuarioLogic.createUsuario(entity);
            nuevaLista.add(entity);
        }
        proyectoUsuarioLogic.replaceUsuarios(proyecto.getId(), nuevaLista);
        List<UsuarioEntity> usuarioEntities = proyectoUsuarioLogic.getUsuarios(proyecto.getId());
        for (UsuarioEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(usuarioEntities.contains(aNuevaLista));
        }
    }

    /**
     * Prueba desasociar un usuario con un proyecto.
     *
     */
    @Test
    public void removeUsuarioTest() {
        for (UsuarioEntity usuario : data) {
            proyectoUsuarioLogic.removeUsuario(proyecto.getId(), usuario.getId());
        }
        Assert.assertTrue(proyectoUsuarioLogic.getUsuarios(proyecto.getId()).isEmpty());
    }
}

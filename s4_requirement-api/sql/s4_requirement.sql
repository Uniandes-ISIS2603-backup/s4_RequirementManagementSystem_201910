delete from StakeHolderEntity;
delete from OrganizacionEntity;
delete from UsuarioEntity;
delete from ProyectoEntity;

delete from AprobacionEntity;
delete from CambioEntity;

delete from CasoDeUsoEntity;
delete from CondicionEntity;

delete from ObjetivoEntity;
delete from ProyectoEntity;
delete from RequisitoEntity;

delete from CaminoEntity;

insert into OrganizacionEntity (id, sector, nombre) values (1, 'Tecnologico', 'Microsoft');
insert into OrganizacionEntity (id, sector, nombre) values (2, 'Financiero', 'Bancolombia');
insert into OrganizacionEntity (id, sector, nombre) values (3, 'Ambiental', 'Fundacion Natural');
insert into OrganizacionEntity (id, sector, nombre) values (4, 'Tecnologico', 'Apple');

insert into StakeHolderEntity (id, tipo, nombre, organizacion_id) values (1, 'Financiero', 'Mateo', 2);
insert into StakeHolderEntity (id, tipo, nombre, organizacion_id) values (2, 'Tecnologico', 'Juan', 4);
insert into StakeHolderEntity (id, tipo, nombre, organizacion_id) values (3, 'Administrador', 'Jorge', 1);
insert into StakeHolderEntity (id, tipo, nombre, organizacion_id) values (4, 'Tecnologico', 'Sofia', 4);

insert into UsuarioEntity (id, usuario, contrasena, tipo) values (1, 'Mateo', '1234', 'Administrador');
insert into UsuarioEntity (id, usuario, contrasena, tipo) values (2, 'Jose', '1234', 'Cliente');

insert into ProyectoEntity (id, nombre, descripcion) values (1, 'Proyecto1', 'Descripcion');
insert into ProyectoEntity (id, nombre, descripcion) values (2, 'Proyecto2', 'Descripcion');


insert into OrganizacionEntity_ProyectoEntity (organizaciones_id,proyectos_id ) values (1,1);
insert into OrganizacionEntity_ProyectoEntity (organizaciones_id,proyectos_id ) values (2,1);
insert into OrganizacionEntity_ProyectoEntity (organizaciones_id,proyectos_id ) values (1,2);
insert into UsuarioEntity_ProyectoEntity (usuarios_id,proyectos_id ) values (1,1);
insert into UsuarioEntity_ProyectoEntity (usuarios_id,proyectos_id ) values (2,1);
insert into UsuarioEntity_ProyectoEntity (usuarios_id,proyectos_id ) values (1,2);

insert into AprobacionEntity (id, tipo, autor, organizacion, fechaYHora, estado, id_aprobado, nombre_aprobado, comentario)
 values (1, 'OBJETIVO','Sofia Alvarez','Startech', 'Fri Apr 05 2019 16:06:58 GMT -0500 (-05)', 'Aprobado', '1', 'Objetivo 1', 'Se ha evaluado el cambio 1 y se considera pertinente su implementación');

insert into AprobacionEntity (id, tipo, autor, organizacion, fechaYHora, estado, id_aprobado, nombre_aprobado, comentario)
 values (2, 'OBJETIVO','Juan Alvarez','Startech', 'Tue Apr 03 2019 16:06:58 GMT -0300 (-03)', 'No aprobado', '2', 'Objetivo 2', 'Se ha evaluado el cambio 2 y se considera que no es pertinente su implementación');

insert into AprobacionEntity (id, tipo, autor, organizacion, fechaYHora, estado, id_aprobado, nombre_aprobado, comentario)
 values (3, 'REQUISITO','Sofia Alvarez','Startech', 'Fri Apr 05 2019 16:06:58 GMT -0500 (-05)', 'En proceso', '1', 'Requisito 3', 'Se está evaluando el requisito 3');

insert into CambioEntity (id, tipo, descripcion, autor, organizacion, fechaYHora, id_aprobado, nombre_aprobado) 
values (1, 'MODIFICACION', 'El objetivo 1 debe modificarse pues no es coherente con el proyecto', 'Sofia Alvarez', 'Startech', 'Fri Apr 05 2019 16:06:58 GMT -0500 (-05)', '1', 'Objetivo 1');

insert into CambioEntity (id, tipo, descripcion, autor, organizacion, fechaYHora, id_aprobado, nombre_aprobado) 
values (2, 'APROBACION', 'El objetivo 3 debe modificarse pues no tiene requisitos asociados', 'Sofia Alvarez', 'Startech', 'Mon Apr 03 2017 16:06:58 GMT -0500 (-05)', '3', 'Objetivo 3');

insert into CambioEntity (id, tipo, descripcion, autor, organizacion, fechaYHora, id_aprobado, nombre_aprobado) 
values (3, 'ELIMINACION', 'Eliminar el requisito 5 pues no concuerda con los lineamientos del proyecto', 'Sofia Alvarez', 'Startech', 'Sun Jan 02 2017 16:06:58 GMT -0500 (-05)', '5', 'Requisito 5'); 

insert into CasoDeUsoEntity (id, nombre) values (10, 'casoPrueba');
insert into CasoDeUsoEntity (id, nombre) values (20, 'casoPrueba2');

insert into CaminoEntity(id, tipoPaso, pasos) values (1, 'BASICO', 'Paso 1');
insert into CaminoEntity(id, tipoPaso, pasos) values (2, 'BASICO', 'Paso 5');

insert into CondicionEntity (id, descripcion, seCumplio) values (30, 'condicionPrueba1', 1);
insert into CondicionEntity (id, descripcion, seCumplio) values (40, 'condicionPrueba2', 0);

insert into CambioEntity (idPaso, tipo, descripcion) values (11, 'TEST', 'Cambio 11');

insert into ObjetivoEntity (id, descripcion, importancia, estabilidad, comentarios) values (11, 'Este es un ejemplo de descripcion', 3,2,'Ejemplo de comantario 1');
insert into ObjetivoEntity (id, descripcion, importancia, estabilidad, comentarios) values (300, 'Ejemplo descripcion 2', 1,1,'Ejemplo de comantario 2');

SELECT * FROM RequisitoEntity;

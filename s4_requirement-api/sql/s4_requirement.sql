delete from StakeHolderEntity;
delete from OrganizacionEntity;

delete from AprobacionEntity;
delete from CambioEntity;

delete from CasoDeUsoEntity;
delete from CondicionEntity;

delete from ObjetivoEntity;
delete from RequisitoEntity;

delete from DRSEntity;
delete from CaminoEntity;

insert into StakeHolderEntity (id, tipo, nombre) values (100, 'Financiero', 'Mateo');
insert into StakeHolderEntity (id, tipo, nombre) values (200, 'Tecnologico', 'Juan');

insert into OrganizacionEntity (id, sector, nombre) values (110, 'Tecnologico', 'Tec');
insert into OrganizacionEntity (id, sector, nombre) values (120, 'Financiero', 'Fin');

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

insert into DRSEntity (id, version, reporte) values (55, 1, 'Este es un reporte');
insert into DRSEntity (id, version, reporte) values (28, 2, 'Este e la version 2 del reporte');

insert into CaminoEntity(id, tipoPaso, pasos) values (1, 'BASICO', 'Paso 1');

insert into CondicionEntity (id, descripcion, seCumplio) values (30, 'condicionPrueba1', 1);
insert into CondicionEntity (id, descripcion, seCumplio) values (40, 'condicionPrueba2', 0);

insert into CambioEntity (idPaso, tipo, descripcion) values (11, 'TEST', 'Cambio 11')

insert into ObjetivoEntity (id, descripcion, importancia, estabilidad, comentarios) values (11, 'Este es un ejemplo de descripcion', 3,2,'Ejemplo de comantario 1')
insert into ObjetivoEntity (id, descripcion, importancia, estabilidad, comentarios) values (300, 'Ejemplo descripcion 2', 1,1,'Ejemplo de comantario 2')


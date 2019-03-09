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

insert into AprobacionEntity (id, tipo, aprobado, comentario) values (2, 'TEST',1,'Aprobacion2');

insert into CambioEntity (id, tipo, descripcion) values (1, 'TEST', 'Cambio 1');

insert into CasoDeUsoEntity (id, nombre) values (10, 'casoPrueba');
insert into CasoDeUsoEntity (id, nombre) values (20, 'casoPrueba2');

insert into DRSEntity (id, version, reporte) values (55, 1, 'Este es un reporte');
insert into DRSEntity (id, version, reporte) values (28, 2, 'Este e la version 2 del reporte');

insert into CaminoEntity(pasos) values ('Paso 1');

insert into CondicionEntity (id, descripcion, seCumplio) values (30, 'condicionPrueba1', 1);
insert into CondicionEntity (id, descripcion, seCumplio) values (40, 'condicionPrueba2', 0);

insert into CambioEntity (id, tipo, descripcion) values (1, 'TEST', 'Cambio 1')

insert into ObjetivoEntity (id, descripcion, importancia, estabilidad, comentarios) values (11, 'Este es un ejemplo de descripcion', 3,2,'Ejemplo de comantario 1')
insert into ObjetivoEntity (id, descripcion, importancia, estabilidad, comentarios) values (300, 'Ejemplo descripcion 2', 1,1,'Ejemplo de comantario 2')


delete from StakeHolderEntity;
delete from OrganizacionEntity;
delete from AprobacionEntity;

insert into StakeHolderEntity (id, tipo, nombre) values (100, 'Financiero', 'Mateo');
insert into StakeHolderEntity (id, tipo, nombre) values (200, 'Tecnologico', 'Juan');

insert into OrganizacionEntity (id, sector, nombre) values (110, 'Tecnologico', 'Tec');
insert into OrganizacionEntity (id, sector, nombre) values (120, 'Financiero', 'Fin');

insert into AprobacionEntity (id, tipo, aprobado, comentario) values (2, 'TEST',1,'Aprobacion2')

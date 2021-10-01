--for migration
--create backup
--pg_dump -U usuario -W -h host basename > basename.sql
--restaurar
--psql -U username -W -h host basename < basename.sql

INSERT INTO gestion(id, fecha_fin,fecha_ini,gestion,periodo) VALUES (1, '2020/12/01','2020/12/05','2020','segundo');
INSERT INTO rol(id_rol, tipo) VALUES (1,'ROLE_ADMIN');
INSERT INTO rol(id_rol, tipo) VALUES (2,'ROLE_COMMISSION');
INSERT INTO rol(id_rol, tipo) VALUES (3,'ROLE_JURY');
INSERT INTO rol(id_rol, tipo) VALUES (4,'ROLE_LEADER');
INSERT INTO rol(id_rol, tipo) VALUES (5,'ROLE_LEADER_GROUP');
INSERT INTO usuario(cod_registro, nombre, password,habilitado,correo) VALUES ('open123', 'David Villca','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'davp123.dh@gmail.com');
INSERT INTO usuario(cod_registro, nombre, password,habilitado,correo) VALUES ('open456', 'Kevin Vargas','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'vargaskevv@gmail.com');
INSERT INTO usuario(cod_registro, nombre, password,habilitado,correo) VALUES ('open789', 'Mijael Mijael','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'minjomc5@gmail.com');
INSERT INTO usuario(cod_registro, nombre, password,habilitado,correo) VALUES ('open321', 'Jorge Bustamante','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'JorgeBustamante868@gmail.com');
INSERT INTO usuario(cod_registro, nombre, password,habilitado,correo) VALUES ('open1', 'Marcelo Palma','$2a$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'marcelopalma@fcpn.edu.bo');
INSERT INTO usuario(cod_registro, nombre, password,habilitado,correo) VALUES ('open2', 'Marisol Tellez','$10$C3Uln5uqnzx/GswADURJGOIdBqYrly9731fnwKDaUdBkt/M3qvtLq',true, 'mtellez@umsa.bo');
INSERT INTO tipo_equipo(id, nombre) VALUES (1,'Grupo De Estudio');
INSERT INTO tipo_equipo(id, nombre) VALUES (2,'Materia');
INSERT INTO tipo_equipo(id, nombre) VALUES (3,'Sociedad Cientifica');
INSERT INTO tipo_equipo(id, nombre) VALUES (4,'III');
INSERT INTO tipo_equipo(id, nombre) VALUES (5,'Invitados');
INSERT INTO usuario_rol(usuario_cod_registro, rol_id_rol) VALUES('open123',1);
INSERT INTO usuario_rol(usuario_cod_registro, rol_id_rol) VALUES('open1',1);
INSERT INTO usuario_rol(usuario_cod_registro, rol_id_rol) VALUES('open2',1);
INSERT INTO usuario_rol(usuario_cod_registro, rol_id_rol) VALUES('open456',1);
INSERT INTO usuario_rol(usuario_cod_registro, rol_id_rol) VALUES('open321',1);
INSERT INTO usuario_rol(usuario_cod_registro, rol_id_rol) VALUES('open789',1);
INSERT INTO tipo_proyecto(id, nombre) VALUES (1,'Educacion y TICs'); 
INSERT INTO tipo_proyecto(id, nombre) VALUES (2,'Diseño y desarrollo de software');
INSERT INTO tipo_proyecto(id, nombre) VALUES (3,'Informática Teórica');
INSERT INTO tipo_proyecto(id, nombre) VALUES (4,'Inteligencia Artificial');
INSERT INTO tipo_proyecto(id, nombre) VALUES (5,'Simulación y Modelaje de Sistemas'); 
INSERT INTO tipo_proyecto(id, nombre) VALUES (6,'Robótica y Arduino'); 
INSERT INTO tipo_proyecto(id, nombre) VALUES (7,'VideoJuegos'); 
INSERT INTO tipo_proyecto(id, nombre) VALUES (8,'Desarrollo de aplicaciones móviles');
INSERT INTO tipo_proyecto(id, nombre) VALUES (9,'Otras relacionadas al área de la Informática'); 
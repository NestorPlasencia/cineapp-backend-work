Query inicial

```sql
create table oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);

INSERT INTO cliente(id_cliente, apellidos, nombres, dni, fecha_nac) values (1, 'MEDINA', 'CODE', '72658987', '1991-01-21');
INSERT INTO cliente(id_cliente, apellidos, nombres, dni, fecha_nac) values (2, 'DELGADO', 'JAIME', '72658987', '1991-01-21');

INSERT INTO usuario(id_usuario, nombre, clave, estado) values (1, 'mitocode', '$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.', '1');
INSERT INTO usuario(id_usuario, nombre, clave, estado) values (2, 'jaime', '$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.', '1');

INSERT INTO Rol (id_rol, nombre, descripcion) VALUES (1, 'ADMIN', 'Administrador');
INSERT INTO Rol (id_rol, nombre, descripcion) VALUES (2, 'USER', 'Usuario');
INSERT INTO Rol (id_rol, nombre, descripcion) VALUES (3, 'DBA', 'Admin de bd');

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 1);
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (1, 3);
INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (2, 2);

INSERT INTO menu(id_menu, nombre, icono, url) VALUES (1, 'Consultas', 'search', '/consulta');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (2, 'Venta de Entradas', 'bookmarks', '/venta');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (3, 'Clientes', 'insert_emoticon', '/cliente');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (4, 'Peliculas', 'local_movies', '/pelicula');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (5, 'Comidas', 'fastfood', '/comida');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (6, 'Géneros', 'view_agenda', '/genero');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (7, 'Reportes', 'assessment', '/reporte');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (8, 'Configuraciones', 'build', '/configuracion');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (9, 'Roles', 'supervisor_account', '/rol');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (10,'Menús', 'menu_open', '/menu');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (11,'Roles a Usuario','how_to_reg','/usuario_rol');
INSERT INTO menu(id_menu, nombre, icono, url) VALUES (12,'Roles a Menu','contacts','/menu_rol');

INSERT INTO menu_rol (id_menu, id_rol) VALUES (1, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (2, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (3, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (4, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (5, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (6, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (7, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (8, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (9, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (10, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (11, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (12, 1);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (4, 2);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (5, 2);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (6, 2);
INSERT INTO menu_rol (id_menu, id_rol) VALUES (7, 2);


INSERT INTO genero (nombre) VALUES ('AVENTURA');
INSERT INTO genero (nombre) VALUES ('DRAMA');
INSERT INTO genero (nombre) VALUES ('MUSICAL');
INSERT INTO genero (nombre) VALUES ('FICCIÓN');
INSERT INTO genero (nombre) VALUES ('OESTE');
INSERT INTO genero (nombre) VALUES ('SUSPENSO');
INSERT INTO genero (nombre) VALUES ('INFANTIL');
INSERT INTO genero (nombre) VALUES ('ANIMACIÓN');
INSERT INTO genero (nombre) VALUES ('BÉLICO');
INSERT INTO genero (nombre) VALUES ('HISTÓRICO');
INSERT INTO genero (nombre) VALUES ('ADULTO');
INSERT INTO genero (nombre) VALUES ('DEPORTIVO');
INSERT INTO genero (nombre) VALUES ('ANTIGUAS');
INSERT INTO genero (nombre) VALUES ('BLANCO/NEGRO');
INSERT INTO genero (nombre) VALUES ('MISTERIO');
INSERT INTO genero (nombre) VALUES ('FANTASIA');
INSERT INTO genero (nombre) VALUES ('RELIGION');
INSERT INTO genero (nombre) VALUES ('EPICO');
INSERT INTO genero (nombre) VALUES ('POLICIACO');
INSERT INTO genero (nombre) VALUES ('COMEDIA ROMANTICA');
INSERT INTO genero (nombre) VALUES ('TERROR INFANTIL');
INSERT INTO genero (nombre) VALUES ('SUBREALISTA');
INSERT INTO genero (nombre) VALUES ('CASERO');
INSERT INTO genero (nombre) VALUES ('AFICIONADO');

CREATE OR REPLACE FUNCTION fn_listarResumen () 
 RETURNS TABLE (
 cantidad INT,
 fecha TEXT
) 
AS $$
DECLARE 
    var_r record;
BEGIN
FOR var_r IN(
	select (count(*)::int) as cantidad, to_char(v.fecha, 'dd/MM/yyyy') as fecha from venta v 
	group by to_char(v.fecha, 'dd/MM/yyyy') order by to_char(v.fecha, 'dd/MM/yyyy') asc 
	)  
 LOOP
        cantidad := var_r.cantidad; 
 		fecha := var_r.fecha;
        RETURN NEXT;
 END LOOP;
END; $$ 
LANGUAGE 'plpgsql';

SELECT * FROM fn_listarResumen();


select m.* from menu_rol mr inner join usuario_rol ur 	on ur.id_rol = mr.id_rol
							inner join menu m 			on m.id_menu = mr.id_menu
							inner join usuario u 		on u.id_usuario = ur.id_usuario
where u.nombre = 'jaime';
```

Credenciales de Superusuario

```yaml
Usuario: mitocode
pass: 123
```
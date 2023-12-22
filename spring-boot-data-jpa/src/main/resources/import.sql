/* Populate tabla clientes */
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Gerson', 'Ep', 'ger@gmail.com', '2023-12-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Paola', 'Ruiz', 'paoi@gmail.com', '2020-10-16', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Maria', 'Esste', 'maria@gmail.com', '2022-12-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Lucia', 'Ruano', 'lucia@gmail.com', '2021-10-14', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Ana', 'Argus', 'ana@gmail.com', '2023-12-22', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Amada', 'Perez', 'amd@gmail.com', '2019-10-16', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Wilson', 'Estrada', 'wilson@gmail.com', '2017-12-25', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Yoselin', 'Ruano', 'yose@gmail.com', '2015-11-18', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jasibe', 'Ep', 'jaz@gmail.com', '2019-11-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Ann', 'Ruiz', 'ann@gmail.com', '2021-10-18', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('laura', 'Esste', 'laura@gmail.com', '2020-11-19', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Luis', 'Ruano', 'luis@gmail.com', '2021-10-16', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Marin', 'Ep', 'marin@gmail.com', '2023-12-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Carlos', 'Ruiz', 'carlosi@gmail.com', '2020-10-16', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('andres', 'Esste', 'andres@gmail.com', '2022-12-17', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Salvador', 'Ruano', 'salvad@gmail.com', '2021-10-16', '');

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple ipod shuffle', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta aro 26', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica comoda 5 Cajones', 299990, NOW());

/* Populate table facturas - Creamos alguna facturas*/
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2,1,4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1,1,7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3,2,6);



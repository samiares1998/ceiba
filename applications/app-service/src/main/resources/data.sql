CREATE TABLE usuario(
	id int primary key auto_increment,    
    correo varchar(100) not null unique,
    clave varchar(100) not null,
    identificacion varchar(10) not null unique,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    rol char not null
);

CREATE TABLE libro(
	id int primary key auto_increment,
    isbn varchar(10) not null unique,
    nombre varchar(100) not null,
    cantidad_total int not null, -- Stock de inventario
    cantidad_disponible int not null
);

CREATE TABLE prestamo(
	id int primary key auto_increment,
    id_usuario int not null,
		foreign key(id_usuario) references usuario(id),
	id_libro int not null,
		foreign key(id_libro) references libro(id),
	fecha_prestamo date not null,
    fecha_entrega date,
    fecha_entregado date,
    observaciones text not null
);
INSERT INTO libro (id,isbn,nombre,cantidad_total,cantidad_disponible) VALUES (null,'ABC','CIEN',2,1);
INSERT INTO usuario(id,correo,clave,identificacion,nombre,apellido,rol ) VALUES (null,'admin@gmail.com','123456','12145636','sami','arevalo',1);
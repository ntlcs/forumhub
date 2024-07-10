drop table usuarios;

create table cursos (

	id bigint not null auto_increment,
	nome varchar(100) not null,
	categoria varchar(100) not null,

	primary key(id)

);

create table usuarios(

    id bigint not null auto_increment,
    nome varchar(100) not null,
	email varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)

);

create table topicos(

	id bigint not null auto_increment,
	titulo varchar(100) not null,
	mensagem varchar(100) not null,
	nome datetime not null,
	status varchar(100) not null,
	usuario_id bigint not null,
	curso_id bigint not null,

	primary key(id)

);

alter table topicos
add FOREIGN key (usuario_id) REFERENCES usuarios(id),
add FOREIGN key (curso_id) REFERENCES cursos(id);

create table respostas(

	id bigint not null auto_increment,
	mensagem varchar(100) not null,
	topico_id bigint not null,
	data_criacao datetime not null,
	usuario_id bigint not null,
	solucao varchar(100),

	primary key (id)

);

alter table respostas
add FOREIGN key (topico_id) REFERENCES topicos(id),
add FOREIGN key (usuario_id) REFERENCES usuarios(id);
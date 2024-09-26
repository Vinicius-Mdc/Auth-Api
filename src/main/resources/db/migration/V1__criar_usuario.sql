CREATE sequence seq_usuario
    start 1
    increment 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE tb_usuario (
	id INTEGER UNIQUE NOT NULL,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	login VARCHAR(255) NOT NULL,
	senha VARCHAR(255) NOT NULL,
	CONSTRAINT pkey_usuario PRIMARY KEY (id)
);
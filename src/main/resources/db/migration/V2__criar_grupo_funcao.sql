CREATE sequence seq_grupo
    start 1
    increment 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE tb_grupo (
	id INTEGER UNIQUE NOT NULL,
	nome VARCHAR(255) NOT NULL,
	CONSTRAINT pkey_grupo PRIMARY KEY (id)
);

CREATE sequence seq_funcao
    start 1
    increment 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE tb_funcao (
	id INTEGER UNIQUE NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	url VARCHAR(255) NOT NULL,
	metodo VARCHAR(20) NULL,
	CONSTRAINT pkey_funcao PRIMARY KEY (id)
);

CREATE TABLE tb_grupo_funcao (
	id_grupo INTEGER NOT NULL,
	id_funcao INTEGER NOT NULL,
	CONSTRAINT fkey_funcao FOREIGN KEY (id_funcao) REFERENCES tb_funcao(id),
	CONSTRAINT fkey_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id)
);
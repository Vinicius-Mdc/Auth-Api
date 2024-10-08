CREATE TABLE tb_refresh_token (
	id UUID UNIQUE NOT NULL,
	id_usuario INTEGER NOT null,
	expiracao DATE NOT null,
	CONSTRAINT pkey_refresh_token PRIMARY KEY (id),
	CONSTRAINT fkey_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id)
);
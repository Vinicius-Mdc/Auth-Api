CREATE TABLE tb_usuario_grupo (
	id_usuario INTEGER NOT NULL,
	id_grupo INTEGER NOT NULL,
	CONSTRAINT fkey_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id),
	CONSTRAINT fkey_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id)
);


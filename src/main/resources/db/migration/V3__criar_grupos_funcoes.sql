INSERT INTO tb_grupo (id,nome) VALUES (nextval('seq_grupo'),'ADMIN');
INSERT INTO tb_grupo (id,nome) VALUES (nextval('seq_grupo'),'MANAGER');
INSERT INTO tb_grupo (id,nome) VALUES (nextval('seq_grupo'),'BASIC');

INSERT INTO tb_funcao (id,descricao, metodo, url) VALUES (nextval('seq_funcao'),'Consultar usuário', 'GET','/usuario/get');
INSERT INTO tb_funcao (id,descricao, metodo, url) VALUES (nextval('seq_funcao'),'Criar usuário', 'POST','/usuario/criar');
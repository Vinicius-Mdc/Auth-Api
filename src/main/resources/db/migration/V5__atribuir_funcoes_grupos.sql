INSERT INTO tb_grupo_funcao (id_grupo,id_funcao) VALUES ((SELECT id FROM tb_grupo WHERE nome = 'MANAGER'), (SELECT id FROM tb_funcao WHERE descricao = 'Consultar usuário'));
INSERT INTO tb_grupo_funcao (id_grupo,id_funcao) VALUES ((SELECT id FROM tb_grupo WHERE nome = 'ADMIN'), (SELECT id FROM tb_funcao WHERE descricao = 'Criar usuário'));
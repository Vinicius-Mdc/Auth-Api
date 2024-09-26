package com.viniciusmdc.AuthApi.repository;

import com.viniciusmdc.AuthApi.domain.Funcao;
import com.viniciusmdc.AuthApi.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByLogin(String login);



}

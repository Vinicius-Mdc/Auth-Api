package com.viniciusmdc.AuthApi.repository;

import com.viniciusmdc.AuthApi.domain.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    Grupo findByNome(String nome);

}

package com.viniciusmdc.AuthApi.repository;

import com.viniciusmdc.AuthApi.domain.Funcao;
import com.viniciusmdc.AuthApi.domain.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {

    @Query("SELECT f FROM Funcao f JOIN f.grupos g WHERE g.nome IN :nomesGrupos")
    List<Funcao> findByNomesGrupoIn(@Param("nomesGrupos") List<String> nomesGrupos);

}

package com.viniciusmdc.AuthApi.service;

import com.viniciusmdc.AuthApi.domain.Funcao;
import com.viniciusmdc.AuthApi.domain.Grupo;
import com.viniciusmdc.AuthApi.repository.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncaoService {

    @Autowired
    FuncaoRepository funcaoRepository;

    public List<Funcao> findByNomesGrupoIn(List<Grupo> grupos) {
        if (grupos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ao menos um grupo deve ser informado para consulta das funções.");
        }

        List<String> nomesGrupos = grupos.stream().map(Grupo::getNome).collect(Collectors.toList());

        return funcaoRepository.findByNomesGrupoIn(nomesGrupos);
    }

}

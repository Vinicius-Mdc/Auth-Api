package com.viniciusmdc.AuthApi.service;

import com.viniciusmdc.AuthApi.domain.Grupo;
import com.viniciusmdc.AuthApi.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    public Grupo findByNome(String nome){
        Grupo grupo = grupoRepository.findByNome(nome);

        if(grupo == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Grupo '%s' não encontrado.", nome));
        }

        return grupo;
    }

}

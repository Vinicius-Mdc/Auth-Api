package com.viniciusmdc.AuthApi.dto;

import com.viniciusmdc.AuthApi.domain.Grupo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

    private String id;

    private String nome;

    private String email;

    private String login;

    private List<String> nomesGrupos;

}

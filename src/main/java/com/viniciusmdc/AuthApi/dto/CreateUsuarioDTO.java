package com.viniciusmdc.AuthApi.dto;

import com.viniciusmdc.AuthApi.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUsuarioDTO {

    private String nome;

    private String email;

    private String login;

    @Setter
    private String senha;

    private List<String> nomesGrupos;

    public Usuario convertDtoToEntity(){
        return new ModelMapper().map(this, Usuario.class);
    }

}

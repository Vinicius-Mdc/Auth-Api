package com.viniciusmdc.AuthApi.service;

import com.viniciusmdc.AuthApi.domain.Grupo;
import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.dto.CreateUsuarioDTO;
import com.viniciusmdc.AuthApi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    GrupoService grupoService;

    @Autowired @Lazy
    PasswordEncoder passwordEncoder;

    public Usuario findUsuarioByLogin(String login){
        Usuario user = usuarioRepository.findByLogin(login);

        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        return user;
    }

    @Transactional
    public void createUsuario(CreateUsuarioDTO userDTO) {

        Usuario usuario = usuarioRepository.findByLogin(userDTO.getLogin());
        if(usuario != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login de usuário já em uso.");
        }
        usuario = userDTO.convertDtoToEntity();

        String encryptedPassword = passwordEncoder.encode(userDTO.getSenha());
        usuario.setSenha(encryptedPassword);

        List<Grupo> grupos = new ArrayList<>();
        for(String nomeGrupo : userDTO.getNomesGrupos()){
            grupos.add(grupoService.findByNome(nomeGrupo));
        }
        usuario.setGrupos(grupos);

        usuarioRepository.save(usuario);
    }
}

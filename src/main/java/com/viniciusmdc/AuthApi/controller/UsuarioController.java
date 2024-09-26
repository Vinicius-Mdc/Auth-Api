package com.viniciusmdc.AuthApi.controller;

import com.viniciusmdc.AuthApi.domain.Grupo;
import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.dto.CreateUsuarioDTO;
import com.viniciusmdc.AuthApi.dto.UsuarioDTO;
import com.viniciusmdc.AuthApi.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuário")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/get/{login}")
    public ResponseEntity<UsuarioDTO> findByLogin(@PathVariable(name = "login") String login){
        Usuario usuario = usuarioService.findUsuarioByLogin(login);

        UsuarioDTO usuarioDTO = usuario.convertEntityToDto();
        usuarioDTO.setNomesGrupos(usuario.getGrupos().stream().map(Grupo::getNome).collect(Collectors.toList()));

        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping("/criar")
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUsuarioDTO userDTO){

        usuarioService.createUsuario(userDTO);

        return ResponseEntity.ok("Usuário criado com sucesso");

    }

}

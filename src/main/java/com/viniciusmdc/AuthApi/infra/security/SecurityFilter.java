package com.viniciusmdc.AuthApi.infra.security;

import com.viniciusmdc.AuthApi.domain.Funcao;
import com.viniciusmdc.AuthApi.domain.Grupo;
import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.repository.FuncaoRepository;
import com.viniciusmdc.AuthApi.service.AuthenticationTokenService;
import com.viniciusmdc.AuthApi.service.FuncaoService;
import com.viniciusmdc.AuthApi.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationTokenService tokenService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FuncaoService funcaoService;

    @Value("${spring.mvc.servlet.path}")
    private String urlBase;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if (token != null) {
            String login = tokenService.validarAccessToken(token);
            Usuario usuario = usuarioService.findUsuarioByLogin(login);

            if(!isAutorizado(request, usuario)){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().flush();

                return;
            };

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

    private boolean isAutorizado(HttpServletRequest request, Usuario usuario) {
        String requestUrl = request.getRequestURI().replace(urlBase, "");

        List<Funcao> funcoes = funcaoService.findByNomesGrupoIn(usuario.getGrupos());

        return funcoes.stream().anyMatch(funcao ->
                antPathMatcher.match(funcao.getUrl(), requestUrl) &&
                        (funcao.getMetodo() == null || funcao.getMetodo().equals("") || funcao.getMetodo().toUpperCase().equals(request.getMethod()))
        );

    }

    private String getToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");

    }
}

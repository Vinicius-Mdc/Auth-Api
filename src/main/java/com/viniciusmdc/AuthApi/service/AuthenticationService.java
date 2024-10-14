package com.viniciusmdc.AuthApi.service;

import com.viniciusmdc.AuthApi.domain.Funcao;
import com.viniciusmdc.AuthApi.domain.Usuario;
import com.viniciusmdc.AuthApi.enums.TipoTokenJwtEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FuncaoService funcaoService;

    @Autowired
    TokenService tokenService;

    @Value("${spring.mvc.servlet.path}")
    private String urlBase;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public AuthorizationDecision validarAutenticacaoAutorizacao(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        try {
            HttpServletRequest request = requestAuthorizationContext.getRequest();

            if (!validarTokenJwt(authentication, requestAuthorizationContext, TipoTokenJwtEnum.ACCESS).isGranted() || !isAutenticado(authentication)) {
                return new AuthorizationDecision(false);
            }

            String login = authentication.get().getName();

            Usuario usuario = usuarioService.findUsuarioByLogin(login);
            return new AuthorizationDecision(isAutorizado(request, usuario));
        } catch (Exception e) {
            log.error(e);
            return new AuthorizationDecision(false);
        }

    }

    public AuthorizationDecision validarTokenJwt(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext, TipoTokenJwtEnum tipoTokenJwt) {
        try {
            HttpServletRequest request = requestAuthorizationContext.getRequest();
            boolean existeToken = isTokenPresente(request);
            if (existeToken) {
                String login = tokenService.validarToken(getToken(request), tipoTokenJwt);
                Usuario usuario = usuarioService.findUsuarioByLogin(login);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            return new AuthorizationDecision(existeToken);
        } catch (ResponseStatusException e) {
            throw new AuthenticationServiceException(e.getReason(), e);
        }
    }

    private boolean isTokenPresente(HttpServletRequest request) {
        String token = getToken(request);
        return token != null;
    }

    private static boolean isAutenticado(Supplier<Authentication> authentication) {
        return authentication != null && authentication.get() != null && authentication.get().isAuthenticated() && !(authentication.get() instanceof AnonymousAuthenticationToken);
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

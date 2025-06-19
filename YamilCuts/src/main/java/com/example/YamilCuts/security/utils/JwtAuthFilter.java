package com.example.YamilCuts.security.utils;

import com.example.YamilCuts.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.validarJWT(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        // ✅ Extraer el usuario (whatsapp) del token
        String whatsapp = jwtService.extraerUsuario(token);

        // ✅ Crear Authentication sin roles por ahora
        Authentication auth = new UsernamePasswordAuthenticationToken(whatsapp, null, List.of());

        // ✅ Setear en el contexto de Spring Security
        SecurityContextHolder.getContext().setAuthentication(auth);

        // ✅ Continuar el flujo normal
        chain.doFilter(request, response);
    }
}



package com.example.YamilCuts.controller;

import com.example.YamilCuts.security.service.JwtService;
import com.example.YamilCuts.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final JwtService jwtService;
    //private final NotificadorWhatsApp notificador;

    @PostMapping("/request-token")
    public ResponseEntity<Void> solicitarToken(@RequestParam String whatsapp) {
        String token = tokenService.generarTokenTemporal(whatsapp);
        System.out.println(token);
        //notificador.enviarToken(whatsapp, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String whatsapp, @RequestParam String token) {
        if (!tokenService.validarTokenTemporal(whatsapp, token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token inválido"));
        }

        String jwt = jwtService.generarJWT(whatsapp);
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}


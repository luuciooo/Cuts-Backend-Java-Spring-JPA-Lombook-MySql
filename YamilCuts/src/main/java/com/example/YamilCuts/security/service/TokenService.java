package com.example.YamilCuts.security.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final Map<String, String> tokensTemporales = new ConcurrentHashMap<>();

    public String generarTokenTemporal(String whatsapp) {
        String token = UUID.randomUUID().toString().substring(0, 6);
        tokensTemporales.put(whatsapp, token);
        return token;
    }

    public boolean validarTokenTemporal(String whatsapp, String token) {
        return tokensTemporales.getOrDefault(whatsapp, "").equals(token);
    }
}


package com.loginpotro.login.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${app.credentials.username}")
    private String usuarioValido;
    @Value("${app.credentials.password}")
    private String contraValida;

    // Record interno para el body del request
    public record LoginRequest(String username, String password) {
        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        boolean valido = usuarioValido.equals(request.getUsername()) && contraValida.equals(request.getPassword());
        if(!valido){
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }
        return ResponseEntity.ok("Bienvenid@ "+request.username);
    }
}

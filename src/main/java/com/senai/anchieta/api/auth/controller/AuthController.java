package com.senai.anchieta.api.auth.controller;

import com.senai.anchieta.infra.security.TokenService;
import com.senai.anchieta.domain.repository.UserRepository;
import com.senai.anchieta.api.auth.dto.AuthDTO;
import com.senai.anchieta.api.auth.dto.LoginResponseDTO;
import com.senai.anchieta.api.auth.dto.RegisterDTO;
import com.senai.anchieta.api.auth.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO){
        try {
            var emailAndPassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
            var auth = this.authenticationManager.authenticate(emailAndPassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        if (this.userRepository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.login(), encryptedPassword, registerDTO.role());

        this.userRepository.save(user);
        return ResponseEntity.ok().body("usuário registrado com sucesso, você já pode fazer login");
    }

    @DeleteMapping(path = {"/delete"+"/{id}"})
    public ResponseEntity delete(@PathVariable String id){
        return userRepository.findById(id)
                .map(record -> {userRepository.deleteById(id);
                return ResponseEntity.ok().body(record);
                }).orElse(ResponseEntity.notFound().build());
    }
}

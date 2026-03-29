package com.springmusicapp.controller;

import com.springmusicapp.dto.*;
import com.springmusicapp.model.Admin;
import com.springmusicapp.model.User;
import com.springmusicapp.repository.UserLoginRepository;
import com.springmusicapp.service.BandManagerService;
import com.springmusicapp.service.JwtService;
import com.springmusicapp.service.MusicianService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserLoginRepository userRepository;
    private final JwtService jwtService;
    private final MusicianService musicianService;
    private final BandManagerService bandManagerService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow();

        boolean needsChange = false;

        if (user instanceof Admin admin) {
            needsChange = admin.isRequiresPasswordChange();
        }

        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDTO(jwtToken, needsChange));
    }

    @PostMapping("/register/musician")
    public ResponseEntity<AuthResponseDTO> registerMusician(@Valid @RequestBody CreateMusicianDTO request) {
        musicianService.create(request);

        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(jwtToken));
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO request, Authentication auth) {
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        if (user instanceof Admin admin) {
            admin.setRequiresPasswordChange(false);
            admin.setLastPasswordChangeDate(LocalDate.now());
        }

        userRepository.save(user);
        return ResponseEntity.ok("Password changed");
    }
}

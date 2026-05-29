package com.medellinpainting.medellingpaintingapi.controllers;

import com.medellinpainting.medellingpaintingapi.dtos.JwtRequestDTO;
import com.medellinpainting.medellingpaintingapi.dtos.JwtResponseDTO;
import com.medellinpainting.medellingpaintingapi.entities.RefreshToken;
import com.medellinpainting.medellingpaintingapi.securities.JwtTokenUtil;
import com.medellinpainting.medellingpaintingapi.services.RefreshTokenService;
import com.medellinpainting.medellingpaintingapi.servicesimplements.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDTO req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "User account is disabled"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        String accessToken = jwtTokenUtil.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.create(req.getUsername());
        return ResponseEntity.ok(new JwtResponseDTO(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String token = body.get("refreshToken");
        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing refresh token"));
        }
        Optional<RefreshToken> found = refreshTokenService.validate(token);
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Refresh token expired or invalid"));
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(found.get().getUsername());
        String newAccessToken = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("jwttoken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        String token = body.get("refreshToken");
        if (token != null) {
            refreshTokenService.validate(token)
                    .ifPresent(rt -> refreshTokenService.deleteByUsername(rt.getUsername()));
        }
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}

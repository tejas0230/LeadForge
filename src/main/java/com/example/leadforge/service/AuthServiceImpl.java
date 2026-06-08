package com.example.leadforge.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.leadforge.dto.LoginRequestDTO;
import com.example.leadforge.dto.LoginResponseDTO;
import com.example.leadforge.dto.RefreshTokenRequestDTO;
import com.example.leadforge.dto.UserRequestDTO;
import com.example.leadforge.entity.RefreshTokenEntity;
import com.example.leadforge.entity.UserEntity;
import com.example.leadforge.exceptions.EmailAlreadyExistsException;
import com.example.leadforge.exceptions.ResourceNotFoundExcepection;
import com.example.leadforge.exceptions.UnauthorizedException;
import com.example.leadforge.repository.RefreshTokenRepository;
import com.example.leadforge.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public UserEntity register(UserRequestDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UserEntity user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new ResourceNotFoundExcepection("Invalid email or password"));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiresAt(LocalDateTime.now().plusSeconds(jwtService.getRefreshTokenExpiration()/1000));
        refreshTokenEntity.setRevoked(false);
        refreshTokenEntity.setCreatedAt(LocalDateTime.now());
        refreshTokenEntity.setUser(user);
        refreshTokenRepository.save(refreshTokenEntity);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(accessToken);
        loginResponseDTO.setRefreshToken(refreshToken);
        loginResponseDTO.setTokenType("Bearer");

        return loginResponseDTO;
    }

    @Override
    @Transactional
    public LoginResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(refreshTokenRequestDTO.getRefreshToken()).orElseThrow(() -> new ResourceNotFoundExcepection("Invalid refresh token"));
        if(refreshTokenEntity.getRevoked()) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        if(refreshTokenEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenEntity.setRevoked(true);
            refreshTokenRepository.save(refreshTokenEntity);
            throw new UnauthorizedException("Refresh token expired");
        }

        String accessToken = jwtService.generateAccessToken(refreshTokenEntity.getUser());
        String refreshToken = jwtService.generateRefreshToken(refreshTokenEntity.getUser());

        refreshTokenEntity.setToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        RefreshTokenEntity newRefreshTokenEntity = new RefreshTokenEntity();
        newRefreshTokenEntity.setToken(refreshToken);
        newRefreshTokenEntity.setExpiresAt(LocalDateTime.now().plusSeconds(jwtService.getRefreshTokenExpiration()/1000));
        newRefreshTokenEntity.setRevoked(false);
        newRefreshTokenEntity.setCreatedAt(LocalDateTime.now());
        newRefreshTokenEntity.setUser(refreshTokenEntity.getUser());
        refreshTokenRepository.save(newRefreshTokenEntity);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setAccessToken(accessToken);
        loginResponseDTO.setRefreshToken(refreshToken);
        loginResponseDTO.setTokenType("Bearer");
        
        return loginResponseDTO;
    }
}

package com.example.leadforge.service;

import com.example.leadforge.dto.LoginRequestDTO;
import com.example.leadforge.dto.LoginResponseDTO;
import com.example.leadforge.dto.RefreshTokenRequestDTO;
import com.example.leadforge.dto.UserRequestDTO;
import com.example.leadforge.entity.UserEntity;

public interface AuthService {
 
    UserEntity register(UserRequestDTO userDTO);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    LoginResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);
}

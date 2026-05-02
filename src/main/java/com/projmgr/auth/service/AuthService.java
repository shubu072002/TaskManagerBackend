package com.projmgr.auth.service;

import com.projmgr.auth.dto.AuthResponse;
import com.projmgr.auth.dto.LoginRequest;
import com.projmgr.auth.dto.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);
    AuthResponse login(LoginRequest request);
}

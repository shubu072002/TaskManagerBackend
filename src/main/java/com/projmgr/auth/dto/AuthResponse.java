package com.projmgr.auth.dto;

import com.projmgr.user.model.Role;

public record AuthResponse(String token, Long userId, String name, String email, Role role) {}

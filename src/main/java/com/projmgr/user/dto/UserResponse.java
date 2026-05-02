package com.projmgr.user.dto;

import com.projmgr.user.model.Role;

public record UserResponse(Long id, String name, String email, Role role) {}

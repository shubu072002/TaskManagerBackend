package com.projmgr.user.dto;

import com.projmgr.user.model.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(@NotNull Role role) {}

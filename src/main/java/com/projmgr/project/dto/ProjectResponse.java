package com.projmgr.project.dto;

import com.projmgr.user.dto.UserResponse;

import java.time.Instant;
import java.util.List;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        UserResponse owner,
        List<UserResponse> members,
        Instant createdAt) {}

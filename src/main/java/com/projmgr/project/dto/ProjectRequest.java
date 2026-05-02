package com.projmgr.project.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record ProjectRequest(@NotBlank String name
        , String description
        , Set<Long> memberIds) {}

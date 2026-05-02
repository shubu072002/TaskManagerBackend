package com.projmgr.task.dto;

import com.projmgr.task.model.TaskStatus;
import com.projmgr.user.dto.UserResponse;

import java.time.Instant;
import java.time.LocalDate;

public record TaskResponse(
        Long id, String title, String description,
        TaskStatus status, LocalDate dueDate,
        Long projectId, String projectName,
        UserResponse assignee, Instant createdAt) {}

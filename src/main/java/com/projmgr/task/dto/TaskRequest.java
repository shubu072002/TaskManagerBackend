package com.projmgr.task.dto;

import com.projmgr.task.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank
        String title,

        String description,

        @NotNull
        TaskStatus status,

        LocalDate dueDate,

        @NotNull
        Long projectId,

        Long assigneeId) {}

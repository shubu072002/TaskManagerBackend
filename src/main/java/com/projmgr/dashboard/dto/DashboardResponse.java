package com.projmgr.dashboard.dto;

import com.projmgr.task.dto.TaskResponse;

import java.util.List;

public record DashboardResponse(
        long totalTasks, long todo, long inProgress, long done,
        List<TaskResponse> overdue) {}

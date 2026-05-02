package com.projmgr.task.service;

import com.projmgr.task.dto.TaskRequest;
import com.projmgr.task.dto.TaskResponse;

import java.util.List;

public interface TaskService {
    List<TaskResponse> listByProject(Long projectId);
    List<TaskResponse> listMine(String email);
    TaskResponse get(Long id);
    TaskResponse create(TaskRequest request);
    TaskResponse update(Long id, TaskRequest request);
    void delete(Long id);
}

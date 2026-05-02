package com.projmgr.dashboard.service;

import com.projmgr.dashboard.dto.DashboardResponse;
import com.projmgr.task.dto.TaskResponse;
import com.projmgr.task.model.TaskEntity;
import com.projmgr.task.model.TaskStatus;
import com.projmgr.task.repository.TaskRepository;
import com.projmgr.user.dto.UserResponse;
import com.projmgr.user.model.UserEntity;
import com.projmgr.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public DashboardServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public DashboardResponse forUser(String email) {
        UserEntity user = userService.getEntityByEmail(email);
        long todo = taskRepository.countByAssigneeAndStatus(user, TaskStatus.TODO);
        long inProgress = taskRepository.countByAssigneeAndStatus(user, TaskStatus.IN_PROGRESS);
        long done = taskRepository.countByAssigneeAndStatus(user, TaskStatus.DONE);
        List<TaskResponse> overdue = taskRepository
                .findAllByAssigneeAndStatusNotAndDueDateBefore(user, TaskStatus.DONE, LocalDate.now())
                .stream().map(this::toResponse).toList();
        return new DashboardResponse(todo + inProgress + done, todo, inProgress, done, overdue);
    }

    private TaskResponse toResponse(TaskEntity t) {
        UserResponse assignee = t.getAssignee() == null ? null : new UserResponse(
                t.getAssignee().getId(), t.getAssignee().getName(),
                t.getAssignee().getEmail(), t.getAssignee().getRole());
        return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(),
                t.getStatus(), t.getDueDate(),
                t.getProject().getId(), t.getProject().getName(),
                assignee, t.getCreatedAt());
    }
}

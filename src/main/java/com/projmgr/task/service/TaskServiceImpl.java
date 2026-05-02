package com.projmgr.task.service;

import com.projmgr.common.NotFoundException;
import com.projmgr.project.model.ProjectEntity;
import com.projmgr.project.service.ProjectService;
import com.projmgr.task.dto.TaskRequest;
import com.projmgr.task.dto.TaskResponse;
import com.projmgr.task.model.TaskEntity;
import com.projmgr.task.repository.TaskRepository;
import com.projmgr.user.dto.UserResponse;
import com.projmgr.user.model.UserEntity;
import com.projmgr.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectService projectService, UserService userService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public List<TaskResponse> listByProject(Long projectId) {
        return taskRepository.findAllByProjectId(projectId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<TaskResponse> listMine(String email) {
        UserEntity user = userService.getEntityByEmail(email);
        return taskRepository.findAllByAssignee(user).stream().map(this::toResponse).toList();
    }

    @Override
    public TaskResponse get(Long id) {
        return toResponse(getEntity(id));
    }

    @Override
    public TaskResponse create(TaskRequest request) {
        ProjectEntity project = projectService.getEntity(request.projectId());
        TaskEntity task = new TaskEntity();
        applyRequest(task, request, project);
        return toResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse update(Long id, TaskRequest request) {
        TaskEntity task = getEntity(id);
        ProjectEntity project = projectService.getEntity(request.projectId());
        applyRequest(task, request, project);
        return toResponse(taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskEntity getEntity(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
    }

    private void applyRequest(TaskEntity task, TaskRequest req, ProjectEntity project) {
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(req.status());
        task.setDueDate(req.dueDate());
        task.setProject(project);
        task.setAssignee(req.assigneeId() == null ? null : userService.getEntityById(req.assigneeId()));
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

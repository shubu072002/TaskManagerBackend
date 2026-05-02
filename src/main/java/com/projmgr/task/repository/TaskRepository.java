package com.projmgr.task.repository;

import com.projmgr.task.model.TaskEntity;
import com.projmgr.task.model.TaskStatus;
import com.projmgr.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByProjectId(Long projectId);
    List<TaskEntity> findAllByAssignee(UserEntity assignee);
    long countByAssigneeAndStatus(UserEntity assignee, TaskStatus status);
    List<TaskEntity> findAllByAssigneeAndStatusNotAndDueDateBefore(UserEntity assignee, TaskStatus status, LocalDate date);
}

package com.projmgr.project.repository;

import com.projmgr.project.model.ProjectEntity;
import com.projmgr.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findAllByMembersContainingOrOwner(UserEntity member, UserEntity owner);
}

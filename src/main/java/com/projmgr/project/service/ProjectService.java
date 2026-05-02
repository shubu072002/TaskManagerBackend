package com.projmgr.project.service;

import com.projmgr.project.dto.ProjectRequest;
import com.projmgr.project.dto.ProjectResponse;
import com.projmgr.project.model.ProjectEntity;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> listForUser(String email);
    ProjectResponse get(Long id);
    ProjectResponse create(ProjectRequest request, String creatorEmail);
    ProjectResponse update(Long id, ProjectRequest request);
    void delete(Long id);
    ProjectEntity getEntity(Long id);
}

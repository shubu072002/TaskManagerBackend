package com.projmgr.project.service;

import com.projmgr.common.NotFoundException;
import com.projmgr.project.dto.ProjectRequest;
import com.projmgr.project.dto.ProjectResponse;
import com.projmgr.project.model.ProjectEntity;
import com.projmgr.project.repository.ProjectRepository;
import com.projmgr.user.dto.UserResponse;
import com.projmgr.user.model.UserEntity;
import com.projmgr.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public List<ProjectResponse> listForUser(String email) {
        UserEntity user = userService.getEntityByEmail(email);
        return projectRepository.findAllByMembersContainingOrOwner(user, user)
                .stream().distinct().map(this::toResponse).toList();
    }

    @Override
    public ProjectResponse get(Long id) {
        return toResponse(getEntity(id));
    }

    @Override
    public ProjectResponse create(ProjectRequest request, String creatorEmail) {
        UserEntity owner = userService.getEntityByEmail(creatorEmail);
        ProjectEntity project = new ProjectEntity();
        project.setName(request.name());
        project.setDescription(request.description());
        project.setOwner(owner);
        project.setMembers(resolveMembers(request.memberIds()));
        project.getMembers().add(owner);
        return toResponse(projectRepository.save(project));
    }

    @Override
    public ProjectResponse update(Long id, ProjectRequest request) {
        ProjectEntity project = getEntity(id);
        project.setName(request.name());
        project.setDescription(request.description());
        if (request.memberIds() != null) {
            Set<UserEntity> members = resolveMembers(request.memberIds());
            members.add(project.getOwner());
            project.setMembers(members);
        }
        return toResponse(projectRepository.save(project));
    }

    @Override
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new NotFoundException("Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectEntity getEntity(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found: " + id));
    }

    private Set<UserEntity> resolveMembers(Set<Long> ids) {
        Set<UserEntity> members = new HashSet<>();
        if (ids == null) return members;
        for (Long uid : ids) members.add(userService.getEntityById(uid));
        return members;
    }

    private ProjectResponse toResponse(ProjectEntity p) {
        UserResponse ownerDto = new UserResponse(
                p.getOwner().getId(), p.getOwner().getName(),
                p.getOwner().getEmail(), p.getOwner().getRole());
        List<UserResponse> memberDtos = p.getMembers().stream()
                .map(m -> new UserResponse(m.getId(), m.getName(), m.getEmail(), m.getRole()))
                .toList();
        return new ProjectResponse(p.getId(), p.getName(), p.getDescription(), ownerDto, memberDtos, p.getCreatedAt());
    }
}

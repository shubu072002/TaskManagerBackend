package com.projmgr.user.service;

import com.projmgr.common.NotFoundException;
import com.projmgr.user.dto.UserResponse;
import com.projmgr.user.model.Role;
import com.projmgr.user.model.UserEntity;
import com.projmgr.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> listAll() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public UserResponse updateRole(Long userId, Role role) {
        UserEntity user = getEntityById(userId);
        user.setRole(role);
        return toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getCurrent(String email) {
        return toResponse(getEntityByEmail(email));
    }

    @Override
    public UserEntity getEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));
    }

    @Override
    public UserEntity getEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    private UserResponse toResponse(UserEntity u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}

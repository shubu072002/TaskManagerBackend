package com.projmgr.user.service;

import com.projmgr.user.dto.UserResponse;
import com.projmgr.user.model.Role;
import com.projmgr.user.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserResponse> listAll();
    UserResponse updateRole(Long userId, Role role);
    UserResponse getCurrent(String email);
    UserEntity getEntityByEmail(String email);
    UserEntity getEntityById(Long id);
}

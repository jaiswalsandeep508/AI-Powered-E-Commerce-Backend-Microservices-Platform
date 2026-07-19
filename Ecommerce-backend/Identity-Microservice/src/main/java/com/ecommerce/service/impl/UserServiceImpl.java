package com.ecommerce.service.impl;

import com.ecommerce.dto.request.RegisterRequest;
import com.ecommerce.dto.request.UpdateUserRequest;
import com.ecommerce.dto.response.UserResponse;
import com.ecommerce.exception.ResourceAlreadyExistsException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.service.factory.UserFactory;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper;
    private final UserFactory userFactory;

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User", "email", request.getEmail()
            );
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResourceAlreadyExistsException("User", "phoneNumber", request.getPhoneNumber()
            );
        }
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role", "roleId", request.getRoleId()));
        User user = userFactory.createUser(request);
        user.setRole(role);
        User savedUser = userRepository.save(user);
        log.info("Register user successfully with userId : {}",
                savedUser);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User getUserById = userFactory.getUserById(userId);
        log.info("Get User successfully with userId : {}",userId);
        return userMapper.toResponse(getUserById);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User getUserByEmail = userFactory.getUserByEmail(email);
        log.info("Get user successfully with email : {}",email);
        return userMapper.toResponse(getUserByEmail);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userFactory.getUserById(userId);
        userMapper.updateEntity(request, user);
        User savedUser = userRepository.save(user);
        log.info("Updated user successfully with userId : {}",userId);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userFactory.getUserById(userId);
        userRepository.delete(user);
        log.info("Delete user successfully wit userId : {}",userId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserResponse> responses = userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
        log.info("Get all users successfully");
        return responses;
    }
}
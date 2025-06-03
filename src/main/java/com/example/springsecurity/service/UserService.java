package com.example.springsecurity.service;

import java.util.List;
import java.util.Optional;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.model.dto.UserDto;

public interface UserService {
    UserDto saveUser(UserDto user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<User> getUser(String username);
    List<User> getUsers();
    Optional<Role> getRoleByName(String role);
    UserDto getUserById(Long id);
    UserDto updateUser(UserDto user);
}
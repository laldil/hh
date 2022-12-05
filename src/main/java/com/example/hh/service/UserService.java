package com.example.hh.service;

import com.example.hh.models.Role;
import com.example.hh.models.Users;

import java.util.List;

public interface UserService {
    Users saveUser(Users user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Users getUser(String username);
    List<Users> getUsers();
}

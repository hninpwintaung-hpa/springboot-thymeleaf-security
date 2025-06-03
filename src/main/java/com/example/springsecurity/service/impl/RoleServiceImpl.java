package com.example.springsecurity.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepo;
	@Override
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}

}

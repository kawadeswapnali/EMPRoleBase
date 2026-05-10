package com.employeeSystem.RoleBasedSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeSystem.RoleBasedSystem.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}

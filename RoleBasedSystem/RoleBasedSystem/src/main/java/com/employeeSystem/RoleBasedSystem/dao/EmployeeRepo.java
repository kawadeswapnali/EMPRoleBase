package com.employeeSystem.RoleBasedSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeSystem.RoleBasedSystem.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}

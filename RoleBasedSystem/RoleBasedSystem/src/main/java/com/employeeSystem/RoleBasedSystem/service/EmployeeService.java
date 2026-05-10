package com.employeeSystem.RoleBasedSystem.service;


import com.employeeSystem.RoleBasedSystem.entity.Employee;

public interface EmployeeService {

	Employee getEmployee(int id);

	Employee updateEmployee(Employee employee);

	Employee delEmployee(Employee employee);

	Employee saveEmployee(Employee employee);

}

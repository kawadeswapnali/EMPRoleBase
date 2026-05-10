package com.employeeSystem.RoleBasedSystem.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeeSystem.RoleBasedSystem.dao.EmployeeRepo;
import com.employeeSystem.RoleBasedSystem.entity.Employee;
import com.employeeSystem.RoleBasedSystem.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepo employeeRepo;
	@Override
	public Employee getEmployee(int id) {
		Optional<Employee> e=employeeRepo.findById(id);
		if(e.isPresent())
			return e.get();
		else
			return null;
	}
	

	@Override
	public Employee saveEmployee(Employee employee) {
		Employee e= employeeRepo.save(employee);
		return e;
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		 Optional<Employee> emp = employeeRepo.findById(employee.getId());
		    
		    if (emp.isPresent()) {
		        Employee employeeFrmDb = emp.get();
		        employeeFrmDb.setName(employee.getName());
		        employeeFrmDb.setEmail(employee.getEmail());
		        employeeFrmDb.setDepartment(employee.getDepartment());
		        employeeFrmDb.setSalary(employee.getSalary());
		        
		        return employeeRepo.save(employeeFrmDb);
		    } 
		        return null;  
	}



	@Override
	public Employee delEmployee(Employee employee) {
Optional<Employee> emp = employeeRepo.findById(employee.getId());
	    
	    if (emp.isPresent()) {
	    	Employee employeeFrmDb = emp.get();
	    	employeeRepo.delete(employeeFrmDb);
	    	return employeeFrmDb;
	    } 
	        return null;
	}


}
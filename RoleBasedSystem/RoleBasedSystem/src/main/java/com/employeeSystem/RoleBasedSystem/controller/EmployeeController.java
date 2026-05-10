
package com.employeeSystem.RoleBasedSystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.employeeSystem.RoleBasedSystem.dao.EmployeeRepo;
import com.employeeSystem.RoleBasedSystem.dao.UserRepo;
import com.employeeSystem.RoleBasedSystem.entity.Employee;
import com.employeeSystem.RoleBasedSystem.entity.User;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepo repo;
    private final UserRepo repoUser;

    public EmployeeController(
            EmployeeRepo repo,
            UserRepo repoUser) {

        this.repo = repo;
        this.repoUser = repoUser;
    }

    // ================= VIEW ALL =================
    // ADMIN + SUPERVISOR

    @GetMapping
    @PreAuthorize(
            "hasAuthority('ADMIN') || " +
            "hasAuthority('SUPERVISOR')"
    )
    public ResponseEntity<?> getAll() {

        List<Employee> employees = repo.findAll();

        return ResponseEntity.ok(employees);
    }

    // ================= ADD EMPLOYEE =================
    // EMPLOYEE ONLY

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public ResponseEntity<?> addEmployee(
            @RequestBody Employee emp) {

        emp.setId(0);

        Employee saved = repo.save(emp);

        // CREATE LOGIN USER

        User user = new User(
                emp.getEmail(),
                "12345",
                "EMPLOYEE"
        );

        repoUser.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // ================= UPDATE EMPLOYEE =================
    // SUPERVISOR ONLY

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPERVISOR')")
    public ResponseEntity<?> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee updatedEmp) {

        Employee emp =
                repo.findById(id).orElse(null);

        if (emp == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message",
                                    "Employee not found"
                            )
                    );
        }

        emp.setName(updatedEmp.getName());
        emp.setEmail(updatedEmp.getEmail());
        emp.setDepartment(updatedEmp.getDepartment());
        emp.setSalary(updatedEmp.getSalary());

        Employee saved = repo.save(emp);

        return ResponseEntity.ok(saved);
    }

    // ================= DELETE EMPLOYEE =================
    // ADMIN ONLY

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteEmployee(
            @PathVariable int id) {

        if (!repo.existsById(id)) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Map.of(
                                    "message",
                                    "Employee not found"
                            )
                    );
        }

        repo.deleteById(id);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Employee deleted successfully"
                )
        );
    }
}


package com.springsecurityloginregister.loginregister.service;

import com.springsecurityloginregister.loginregister.models.Employee;
import com.springsecurityloginregister.loginregister.models.Role;
import com.springsecurityloginregister.loginregister.repositories.EmployeeRepository;
import com.springsecurityloginregister.loginregister.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("employeeService")
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public void saveEmployee(Employee employee) {
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employee.setActive(1);
        Role employeeRole = roleRepository.findByRole("Admin");
        employee.setRoles(new HashSet<Role>(Arrays.asList(employeeRole)));
        employeeRepository.save(employee);
    }
}

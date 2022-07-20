package com.example.employee_git.controller;

import com.example.employee_git.model.Employee;
import com.example.employee_git.service.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }



    @GetMapping("/user")
    public List<Employee> getEmployees(){
        return employeeService.getUsers();
    }

    @PostMapping("/user")
    public String saveEmployee(@RequestBody Employee employee) throws Exception {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/user/{id}")
    public Employee getEmployeeById(@RequestBody @PathVariable long id) throws Exception {
        return employeeService.findUser(id);
    }


    @PostMapping("/update/{id}")
    public Employee updateEmployeeById(@RequestBody Employee employee,@PathVariable long id) throws Exception {
        return employeeService.updateEmployee(employee,id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id) throws Exception {
        return employeeService.deleteEmployee(id);
    }
}

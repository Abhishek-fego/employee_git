package com.example.employee_git.service;

import com.example.employee_git.model.Employee;
import com.example.employee_git.repo.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl {
    private final EmployeeRepo employeeRepo;
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }



    public List<Employee> getUsers() {
        return employeeRepo.findAll();
    }


    public Employee findUser(long id) throws Exception {
        return employeeRepo.findById(id).orElseThrow(()->new Exception("NO employee with given id"));
    }


    public Employee updateEmployee(Employee employee,long id) throws Exception {
        if(id!=employee.getId()){
            throw new Exception("The given id does not match the id which needs to be updated");
        }
        employeeRepo.save(employee);
        return employeeRepo.findById(employee.getId()).orElseThrow(()->new Exception("NO employee with given id"));

    }


    public String deleteEmployee(long id) throws Exception {
        try{
            Employee employee = employeeRepo.findById(id).orElseThrow(()-> new Exception("NO employee with given id"));
            employeeRepo.delete(employee);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return "Deletion Successful!!!";
    }

    public String saveEmployee(Employee employee) throws Exception {
        try{
            employeeRepo.save(employee);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return "User added successfully";
    }
}

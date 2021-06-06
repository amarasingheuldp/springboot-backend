package com.danu.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danu.springboot.exception.ResourceNotFoundException;
import com.danu.springboot.model.Employee;
import com.danu.springboot.repository.EmployeeRepository;

//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
	
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * get all employees
	 * @return Employee list
	 */
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		logger.info("EmployeeController------inside getAllEmployees method");
		return employeeRepository.findAll();
	}
	
	// create employee rest api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		logger.info("EmployeeController------inside createEmployee method");
		return employeeRepository.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		
		logger.info("EmployeeController------inside getEmployeeById method");
		Employee employee = employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id: " + id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, 
													@RequestBody Employee employeeDetails){
		
		logger.info("EmployeeController------inside updateEmployee method");
		Employee employee = employeeRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee not exist with id: " + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

}

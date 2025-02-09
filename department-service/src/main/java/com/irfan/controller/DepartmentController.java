package com.irfan.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.irfan.client.EmployeeClient;
import com.irfan.model.Department;
import com.irfan.repository.DepartmentRepository;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	private static final Logger logger=LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	DepartmentRepository repo;
	
	@Autowired
	EmployeeClient empClient;
	
	@PostMapping
	public Department addDepartment(@RequestBody Department dep) {
		logger.info("Adding department: "+ dep);
		return repo.addDepartment(dep);
	}
	
	@GetMapping
	public List<Department> getAll(){
		logger.info("Find all");
		return repo.findAll();
	}
	
	@GetMapping({"/id"})
	public Department findById(@PathVariable Long id) {
		logger.info("Find By Id :"+ id);
		return repo.findById(id);
	}
	
    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees() {
        logger.info("Department find");
        List<Department> departments
                = repo.findAll();
        departments.forEach(department ->
                department.setEmployees(
                        empClient.findByDepartment(department.getId())));
        return  departments;
    }

}

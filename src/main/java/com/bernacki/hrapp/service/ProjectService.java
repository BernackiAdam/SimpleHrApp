package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;

import java.util.List;

public interface ProjectService {
    public List<Project> findAll();
    public Project findById(int id);
    public Project findByName(String name);
    public List<Employee> findEmployeesAssignedToProject(int id);
}

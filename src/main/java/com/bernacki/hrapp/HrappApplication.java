package com.bernacki.hrapp;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.model.ProjectPhase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class HrappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrappApplication.class, args);
	}

	@Bean
	@Scope("prototype")
	public Employee employee(){
		return new Employee();
	}

	@Bean
	@Scope("prototype")
	public Project project(){
		return new Project();
	}

	@Bean
	@Scope("prototype")
	public ProjectPhase projectPhase(){
		return new ProjectPhase();
	}

	@Bean
	@Scope("prototype")
	public ProjectAssignment assignment(){
		return new ProjectAssignment();
	}



}

package com.bernacki.hrapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects_employees")
public class ProjectAssignment {

    @EmbeddedId
    private ProjectAssignmentId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "role")
    private String role;

    public ProjectAssignment(Employee employee, Project project, String role) {
        this.employee = employee;
        this.project = project;
        this.role = role;
    }
}

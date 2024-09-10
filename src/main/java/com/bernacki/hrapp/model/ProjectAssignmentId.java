package com.bernacki.hrapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProjectAssignmentId implements Serializable {

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "project_id")
    private int projectId;
}

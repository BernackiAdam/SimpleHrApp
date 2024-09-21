package com.bernacki.hrapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAssignmentId implements Serializable {

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "project_id")
    private int projectId;
}

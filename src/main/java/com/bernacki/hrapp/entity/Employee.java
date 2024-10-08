package com.bernacki.hrapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Collection;
import java.util.Date;

@Entity
@Data
//@ToString
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "tel_nr")
    private String telephoneNumber;

    @Column(name = "join_date")
    @CreationTimestamp
    private Date joinDate;

    @Column(name="seniority")
    private String seniority;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Collection<EmployeeActivity> employeeActivities;

    @OneToMany(mappedBy = "employee")
    private Collection<ProjectAssignment> projectAssignments;


    public Employee(String firstName, String lastName, String email, String telephoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
    }
}

package com.bernacki.hrapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "employee_activity")
@Data
@NoArgsConstructor
@ToString(exclude = "employee")
public class EmployeeActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "active")
    private boolean active;

    @Column(name = "date")
    @CreationTimestamp
    private Date date;

    @Column(name = "reactivation_date")
    private Date reactivationDate;

    @Column(name = "deactivation_reason")
    private String deactivationReason;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

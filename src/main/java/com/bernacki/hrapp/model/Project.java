package com.bernacki.hrapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@ToString(exclude = "client")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "project_type")
    private String projectType;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private ProjectConsultant projectConsultant;

    @OneToMany(mappedBy = "project")
    private Collection<ProjectAssignment> projectAssignments;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectPhase> phases;

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }
}

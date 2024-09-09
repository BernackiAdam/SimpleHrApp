package com.bernacki.hrapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "project_phase")
public class ProjectPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "phase")
    private String phase;

    @Column(name = "date")
    @CreationTimestamp
    private Date date;

    public ProjectPhase(Project project, String phase, Date date) {
        this.project = project;
        this.phase = phase;
        this.date = date;
    }
}

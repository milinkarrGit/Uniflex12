package com.uniflex.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "class_groups")
public class ClassGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 9)
    private String academicYear;

    @Column(nullable = false)
    private boolean active = true;

    // Getters / Setters
    public Long getId()                           { return id; }
    public String getName()                       { return name; }
    public void setName(String name)              { this.name = name; }
    public String getAcademicYear()               { return academicYear; }
    public void setAcademicYear(String y)         { this.academicYear = y; }
    public boolean isActive()                     { return active; }
    public void setActive(boolean active)         { this.active = active; }
}
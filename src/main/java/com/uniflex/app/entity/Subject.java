package com.uniflex.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private double coefficient = 1.0;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_group_id")
    private ClassGroup classGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // Getters / Setters
    public Long getId()                               { return id; }
    public String getName()                           { return name; }
    public void setName(String name)                  { this.name = name; }
    public double getCoefficient()                    { return coefficient; }
    public void setCoefficient(double coefficient)    { this.coefficient = coefficient; }
    public boolean isActive()                         { return active; }
    public void setActive(boolean active)             { this.active = active; }
    public ClassGroup getClassGroup()                 { return classGroup; }
    public void setClassGroup(ClassGroup classGroup)  { this.classGroup = classGroup; }
    public Teacher getTeacher()                       { return teacher; }
    public void setTeacher(Teacher teacher)           { this.teacher = teacher; }
}
package com.uniflex.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false, length = 2)
    private String term; // S1 ou S2

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Getters / Setters
    public Long getId()                       { return id; }
    public double getValue()                  { return value; }
    public void setValue(double value)        { this.value = value; }
    public String getTerm()                   { return term; }
    public void setTerm(String term)          { this.term = term; }
    public Student getStudent()               { return student; }
    public void setStudent(Student student)   { this.student = student; }
    public Subject getSubject()               { return subject; }
    public void setSubject(Subject subject)   { this.subject = subject; }
}
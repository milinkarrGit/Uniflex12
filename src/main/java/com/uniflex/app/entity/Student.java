package com.uniflex.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_group_id")
    private ClassGroup classGroup;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters / Setters
    public Long getId()                               { return id; }
    public String getCode()                           { return code; }
    public void setCode(String code)                  { this.code = code; }
    public String getFirstName()                      { return firstName; }
    public void setFirstName(String firstName)        { this.firstName = firstName; }
    public String getLastName()                       { return lastName; }
    public void setLastName(String lastName)          { this.lastName = lastName; }
    public boolean isActive()                         { return active; }
    public void setActive(boolean active)             { this.active = active; }
    public ClassGroup getClassGroup()                 { return classGroup; }
    public void setClassGroup(ClassGroup classGroup)  { this.classGroup = classGroup; }
    public User getUser()                             { return user; }
    public void setUser(User user)                    { this.user = user; }
}
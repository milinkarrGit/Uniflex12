package com.uniflex.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String firstName;

    @Column(nullable = false, length = 60)
    private String lastName;

    @Column(length = 30)
    private String phone;

    @Column(length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean active = true;

    // Getters / Setters
    public Long getId()                         { return id; }
    public String getFirstName()                { return firstName; }
    public void setFirstName(String firstName)  { this.firstName = firstName; }
    public String getLastName()                 { return lastName; }
    public void setLastName(String lastName)    { this.lastName = lastName; }
    public String getPhone()                    { return phone; }
    public void setPhone(String phone)          { this.phone = phone; }
    public String getEmail()                    { return email; }
    public void setEmail(String email)          { this.email = email; }
    public boolean isActive()                   { return active; }
    public void setActive(boolean active)       { this.active = active; }
}
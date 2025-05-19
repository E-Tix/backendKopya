package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;


@Entity
@Table(name = "Admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminID;

    @Email
    @Column(name = "email",unique = true,nullable = false,updatable = false)
    private String email;

    @Column(name = "sifre")
    private String sifre;

    public AdminEntity(Long adminID, String email, String sifre) {
        this.adminID = adminID;
        this.email = email;
        this.sifre = sifre;
    }

    public AdminEntity() {
    }

    public Long getAdminID() {
        return adminID;
    }

    public void setAdminID(Long adminID) {
        this.adminID = adminID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}

package com.example.demo.Dto.Request;

public class OrgLoginDto {
    private String email;
    private String sifre;

    public OrgLoginDto(String email, String sifre) {
        this.email = email;
        this.sifre = sifre;
    }

    public OrgLoginDto() {
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

package com.example.demo.Dto.Response;

public class AdminProfiliDto {

    private String email;

    public AdminProfiliDto(String email) {
        this.email = email;
    }

    public AdminProfiliDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

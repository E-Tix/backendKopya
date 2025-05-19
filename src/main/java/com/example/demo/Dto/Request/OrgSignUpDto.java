package com.example.demo.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrgSignUpDto {

    @NotEmpty(message = "Ad soyad boş olamaz")
    private String adSoyad;

    @NotEmpty(message = "Vergi numarası boş olamaz")
    @Size(min = 10, max = 10, message = "Vergi numarası 10 haneli olmalıdır")
    @Pattern(regexp = "\\d+", message = "Vergi numarası sadece rakamlardan oluşmalıdır")
    private String vergiNo;

    @NotEmpty(message = "Email boş olamaz")
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email;

    @NotEmpty(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    private String sifre;

    @NotEmpty(message = "IBAN boş olamaz")
    @Pattern(regexp = "TR\\d{24}", message = "IBAN formatı geçersiz. Örnek: TR000000000000000000000000")
    private String iban;

    @NotEmpty(message = "Şirket adresi boş olamaz")
    private String sirketAdresi;

    @NotEmpty(message = "TCKN boş olamaz")
    @Size(min = 11, max = 11, message = "TCKN 11 haneli olmalıdır")
    @Pattern(regexp = "\\d+", message = "TCKN sadece rakamlardan oluşmalıdır")
    private String tckNo;

    @NotEmpty(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^\\d{10,15}$", message = "Telefon numarası sadece rakamlardan oluşmalı ve 10-15 hane olmalıdır")
    private String telefonNumarasi;

    public OrgSignUpDto() {}

    public OrgSignUpDto(String adSoyad, String vergiNo, String email, String sifre, String iban, String sirketAdresi, String tckNo, String telefonNumarasi) {
        this.adSoyad = adSoyad;
        this.vergiNo = vergiNo;
        this.email = email;
        this.sifre = sifre;
        this.iban = iban;
        this.sirketAdresi = sirketAdresi;
        this.tckNo = tckNo;
        this.telefonNumarasi = telefonNumarasi;
    }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getVergiNo() { return vergiNo; }
    public void setVergiNo(String vergiNo) { this.vergiNo = vergiNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public String getSirketAdresi() { return sirketAdresi; }
    public void setSirketAdresi(String sirketAdresi) { this.sirketAdresi = sirketAdresi; }

    public String getTckNo() { return tckNo; }
    public void setTckNo(String tckNo) { this.tckNo = tckNo; }

    public String getTelefonNumarasi() { return telefonNumarasi; }
    public void setTelefonNumarasi(String telefonNumarasi) { this.telefonNumarasi = telefonNumarasi; }
}

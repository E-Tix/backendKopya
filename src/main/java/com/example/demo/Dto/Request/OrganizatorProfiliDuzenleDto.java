package com.example.demo.Dto.Request;

public class OrganizatorProfiliDuzenleDto {
    private String adSoyad;

    private String email;

    private String iban;

    private String sirketAdresi;

    private String telefonNumarasi;

    public OrganizatorProfiliDuzenleDto(String adSoyad, String email, String iban, String sirketAdresi, String telefonNumarasi) {
        this.adSoyad = adSoyad;
        this.email = email;
        this.iban = iban;
        this.sirketAdresi = sirketAdresi;
        this.telefonNumarasi = telefonNumarasi;
    }

    public OrganizatorProfiliDuzenleDto() {
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSirketAdresi() {
        return sirketAdresi;
    }

    public void setSirketAdresi(String sirketAdresi) {
        this.sirketAdresi = sirketAdresi;
    }

    public String getTelefonNumarasi() {
        return telefonNumarasi;
    }

    public void setTelefonNumarasi(String telefonNumarasi) {
        this.telefonNumarasi = telefonNumarasi;
    }
}

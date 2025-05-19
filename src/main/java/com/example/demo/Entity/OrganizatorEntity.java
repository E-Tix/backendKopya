package com.example.demo.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Organizator")
public class OrganizatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizatorID;

    @Column(name = "adSoyad",nullable = false)
    private String adSoyad;

    @Column(name = "vergiNo",nullable = false)
    private String vergiNo;

    @Column(name = "email",nullable = false ,unique = true)
    private String email;

    @Column(name = "sifre",nullable = false)
    private String sifre;

    @Column(name = "iban",nullable = false ,unique = true)
    private String iban;

    @Column(name = "sirketAdresi",nullable = false)
    private String sirketAdresi;

    @Column(name = "tckNo",nullable = false ,unique = true)
    private String tckNo;

    @Column(name = "telefonNumarasi",nullable = false ,unique = true)
    private String telefonNumarasi;

    public OrganizatorEntity(Long organizatorID, String adSoyad, String vergiNo, String email, String sifre, String iban, String sirketAdresi, String tckNo, String telefonNumarasi) {
        this.organizatorID = organizatorID;
        this.adSoyad = adSoyad;
        this.vergiNo = vergiNo;
        this.email = email;
        this.sifre = sifre;
        this.iban = iban;
        this.sirketAdresi = sirketAdresi;
        this.tckNo = tckNo;
        this.telefonNumarasi = telefonNumarasi;
    }

    public OrganizatorEntity(String adSoyad, String vergiNo, String email, String sifre, String iban, String sirketAdresi, String tckNo, String telefonNumarasi) {
        this.adSoyad = adSoyad;
        this.vergiNo = vergiNo;
        this.email = email;
        this.sifre = sifre;
        this.iban = iban;
        this.sirketAdresi = sirketAdresi;
        this.tckNo = tckNo;
        this.telefonNumarasi = telefonNumarasi;
    }

    public OrganizatorEntity(){}

    public Long getOrganizatorID() {
        return organizatorID;
    }

    public void setOrganizatorID(Long organizatorID) {
        this.organizatorID = organizatorID;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getVergiNo() {
        return vergiNo;
    }

    public void setVergiNo(String vergiNo) {
        this.vergiNo = vergiNo;
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

    public String getTckNo() {
        return tckNo;
    }

    public void setTckNo(String tckNo) {
        this.tckNo = tckNo;
    }

    public String getTelefonNumarasi() {
        return telefonNumarasi;
    }

    public void setTelefonNumarasi(String telefonNumarasi) {
        this.telefonNumarasi = telefonNumarasi;
    }
}

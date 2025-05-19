package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "GeciciKullanici")
public class GeciciKullaniciEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "geciciKullaniciID")
    private Long geciciKullaniciID;

    @Column(name = "adSoyad",nullable = false)
    private String adSoyad;

    @OneToOne
    BiletEntity bilet;

    public GeciciKullaniciEntity(Long geciciKullaniciID, String adSoyad) {
        this.geciciKullaniciID = geciciKullaniciID;
        this.adSoyad = adSoyad;
    }

    public GeciciKullaniciEntity()
    {

    }

    public Long getGeciciKullaniciID() {
        return geciciKullaniciID;
    }

    public void setGeciciKullaniciID(Long geciciKullaniciID) {
        this.geciciKullaniciID = geciciKullaniciID;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }
}

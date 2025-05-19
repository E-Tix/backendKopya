package com.example.demo.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Kullanici")
public class KullaniciEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long kullaniciID;
    @ManyToOne
    private SehirEntity sehir;
    
    @OneToMany(mappedBy = "kullanici")
    private List<KullaniciBiletEntity> kullaniciBiletEntityList;

    @Column(name = "kullaniciAdi",nullable = false,unique = true)
    private String kullaniciAdi;
    @Column(name = "adSoyad",nullable = false)
    private String adSoyad;
    @Column(name = "sifre",nullable = false)
    private String sifre;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "telNo",nullable = false,unique = true)
    private String telNo;

    public KullaniciEntity(Long kullaniciID, SehirEntity sehir, List<KullaniciBiletEntity> kullaniciBiletEntityList, String kullaniciAdi, String adSoyad, String sifre, String email, String telNo) {
        this.kullaniciID = kullaniciID;
        this.sehir = sehir;
        this.kullaniciBiletEntityList = kullaniciBiletEntityList;
        this.kullaniciAdi = kullaniciAdi;
        this.adSoyad = adSoyad;
        this.sifre = sifre;
        this.email = email;
        this.telNo = telNo;
    }

    public KullaniciEntity() {
    }

    //SignUpDto için
    public KullaniciEntity(String kullaniciAdi, String adSoyad, String sifre, String email, String telNo) {
        this.kullaniciAdi = kullaniciAdi;
        this.adSoyad = adSoyad;
        this.sifre = sifre;
        this.email = email;
        this.telNo = telNo;
    }

    public Long getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(Long kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public SehirEntity getSehir() {
        return sehir;
    }

    public void setSehir(SehirEntity sehir) {
        this.sehir = sehir;
    }

    public List<KullaniciBiletEntity> getKullaniciBiletEntityList() {
        return kullaniciBiletEntityList;
    }

    public void setKullaniciBiletEntityList(ArrayList<KullaniciBiletEntity> KullaniciBiletEntityList) {
        this.kullaniciBiletEntityList = KullaniciBiletEntityList;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public void addKullaniciBiletEntityList(KullaniciBiletEntity KullaniciBiletEntityList)
    {
        this.kullaniciBiletEntityList.add(KullaniciBiletEntityList);
    }

    public void deleteKullaniciBiletEntityList(KullaniciBiletEntity KullaniciBiletEntityList)
    {
        this.kullaniciBiletEntityList.remove(KullaniciBiletEntityList);
    }
}

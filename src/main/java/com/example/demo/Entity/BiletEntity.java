package com.example.demo.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "bilet")
public class BiletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long biletID;

    @Column(name = "iptalEdildiMi", nullable = false)
    private boolean iptalEdildiMi = false;

    @Column(name = "odendiMi", nullable = false)
    private Boolean odendiMi = false;

    @Column
    private Float odenenMiktar;

    // 1:1 SeansKoltukBilet → Bilet
    @OneToOne(
            mappedBy = "bilet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private SeansKoltukBiletEntity seansKoltukBilet;

    // 1:1 KullaniciBilet → Bilet
    @OneToOne(
            mappedBy = "bilet",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private KullaniciBiletEntity kullaniciBilet;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp olusturmaZamani;


    public BiletEntity(Long biletID, boolean iptalEdildiMi, Boolean odendiMi, Float odenenMiktar, KullaniciBiletEntity kullaniciBilet, Timestamp olusturmaZamani) {
        this.biletID = biletID;
        this.iptalEdildiMi = iptalEdildiMi;
        this.odendiMi = odendiMi;
        this.odenenMiktar = odenenMiktar;
        this.kullaniciBilet = kullaniciBilet;
        this.olusturmaZamani = olusturmaZamani;
    }

    public BiletEntity(Long biletID, Boolean odendiMi, Float odenenMiktar, Timestamp olusturmaZamani) {
        this.biletID = biletID;
        this.odendiMi = odendiMi;
        this.odenenMiktar = odenenMiktar;
        this.olusturmaZamani = olusturmaZamani;
    }

    public BiletEntity(Long biletID, boolean iptalEdildiMi, Boolean odendiMi, Float odenenMiktar, Timestamp olusturmaZamani) {
        this.biletID = biletID;
        this.iptalEdildiMi = iptalEdildiMi;
        this.odendiMi = odendiMi;
        this.odenenMiktar = odenenMiktar;
        this.olusturmaZamani = olusturmaZamani;
    }

    public BiletEntity(Boolean odendiMi, Float odenenMiktar)
    {
        this.odendiMi=odendiMi;
        this.odenenMiktar=odenenMiktar;
    }

    public BiletEntity()
    {
        //abcde
    }

    // … all-args constructor(s), if you need them …

    public Long getBiletID() {
        return biletID;
    }
    public void setBiletID(Long biletID) {
        this.biletID = biletID;
    }

    public boolean isIptalEdildiMi() {
        return iptalEdildiMi;
    }
    public void setIptalEdildiMi(boolean iptalEdildiMi) {
        this.iptalEdildiMi = iptalEdildiMi;
    }

    public Boolean getOdendiMi() {
        return odendiMi;
    }
    public void setOdendiMi(Boolean odendiMi) {
        this.odendiMi = odendiMi;
    }

    public Float getOdenenMiktar() {
        return odenenMiktar;
    }
    public void setOdenenMiktar(Float odenenMiktar) {
        this.odenenMiktar = odenenMiktar;
    }

    public SeansKoltukBiletEntity getSeansKoltukBilet() {
        return seansKoltukBilet;
    }
    public void setSeansKoltukBilet(SeansKoltukBiletEntity seansKoltukBilet) {
        this.seansKoltukBilet = seansKoltukBilet;
    }

    public KullaniciBiletEntity getKullaniciBilet() {
        return kullaniciBilet;
    }
    public void setKullaniciBilet(KullaniciBiletEntity kullaniciBilet) {
        this.kullaniciBilet = kullaniciBilet;
    }

    public Timestamp getOlusturmaZamani() {
        return olusturmaZamani;
    }
    public void setOlusturmaZamani(Timestamp olusturmaZamani) {
        this.olusturmaZamani = olusturmaZamani;
    }
}

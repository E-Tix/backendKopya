package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seansKoltukBilet")
public class SeansKoltukBiletEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seansKoltukBiletID;

    @ManyToOne
    private SeansEntity seans;

    @ManyToOne
    private KoltukEntity koltuk;

    @OneToOne
    @JoinColumn(name = "bilet_biletid")
    private BiletEntity bilet;

    @Column(name = "koltukDurumu")
    private boolean koltukdurumu;

    public SeansKoltukBiletEntity(SeansEntity seans, KoltukEntity koltuk, BiletEntity bilet, boolean koltukdurumu) {
        this.seans = seans;
        this.koltuk = koltuk;
        this.bilet = bilet;
        this.koltukdurumu = koltukdurumu;
    }

    public SeansKoltukBiletEntity(SeansEntity seans,KoltukEntity koltuk,boolean koltukdurumu)
    {
        this.seans=seans;
        this.koltuk=koltuk;
        this.koltukdurumu=koltukdurumu;
    }

    public SeansKoltukBiletEntity(Long seansKoltukBiletID, SeansEntity seans, KoltukEntity koltuk, BiletEntity bilet, boolean koltukdurumu) {
        this.seansKoltukBiletID = seansKoltukBiletID;
        this.seans = seans;
        this.koltuk = koltuk;
        this.bilet = bilet;
        this.koltukdurumu = koltukdurumu;
    }

    public SeansKoltukBiletEntity(){}

    public Long getSeansKoltukBiletID() {
        return seansKoltukBiletID;
    }

    public void setSeansKoltukBiletID(Long seansKoltukBiletID) {
        this.seansKoltukBiletID = seansKoltukBiletID;
    }

    public SeansEntity getSeans() {
        return seans;
    }

    public void setSeans(SeansEntity seans) {
        this.seans = seans;
    }

    public KoltukEntity getKoltuk() {
        return koltuk;
    }

    public void setKoltuk(KoltukEntity koltuk) {
        this.koltuk = koltuk;
    }

    public BiletEntity getBilet() {
        return bilet;
    }

    public void setBilet(BiletEntity bilet) {
        this.bilet = bilet;
    }

    public boolean getKoltukdurumu() {
        return koltukdurumu;
    }

    public void setKoltukdurumu(boolean koltukdurumu) {
        this.koltukdurumu = koltukdurumu;
    }
}

package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sinema", uniqueConstraints = @UniqueConstraint(columnNames = {"etkinlik_etkinlikid"}))
public class SinemaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sinemaID;

    @ManyToOne
    private EtkinlikEntity etkinlik;

    @Column(name = "fragmanLinki",nullable = false)
    private String fragmanLinki;

    @Column(name = "imdbPuani")
    private float imdbPuani;

    public SinemaEntity(Long sinemaID, EtkinlikEntity etkinlik, String fragmanLinki, float imdbPuani) {
        this.sinemaID = sinemaID;
        this.etkinlik = etkinlik;
        this.fragmanLinki = fragmanLinki;
        this.imdbPuani = imdbPuani;
    }

    public SinemaEntity(EtkinlikEntity etkinlik, String fragmanLinki) {
        this.etkinlik = etkinlik;
        this.fragmanLinki = fragmanLinki;
    }

    public SinemaEntity(){}

    public Long getSinemaID() {
        return sinemaID;
    }

    public void setSinemaID(Long sinemaID) {
        this.sinemaID = sinemaID;
    }

    public EtkinlikEntity getEtkinlik() {
        return etkinlik;
    }

    public void setEtkinlik(EtkinlikEntity etkinlik) {
        this.etkinlik = etkinlik;
    }

    public String getFragmanLinki() {
        return fragmanLinki;
    }

    public void setFragmanLinki(String fragmanLinki) {
        this.fragmanLinki = fragmanLinki;
    }

    public float getImdbPuani() {
        return imdbPuani;
    }

    public void setImdbPuani(float imdbPuani) {
        this.imdbPuani = imdbPuani;
    }
}

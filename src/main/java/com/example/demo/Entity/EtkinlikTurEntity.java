package com.example.demo.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "etkinlikTur")
public class EtkinlikTurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etkinlikTurID;

    @Column
    private String etkinlikTurAdi;

    public EtkinlikTurEntity(Long etkinlikTurID, String etkinlikTurAdi) {
        this.etkinlikTurID = etkinlikTurID;
        this.etkinlikTurAdi = etkinlikTurAdi;
    }

    public EtkinlikTurEntity(){}

    public Long getEtkinlikTurID() {
        return etkinlikTurID;
    }

    public void setEtkinlikTurID(Long etkinlikTurID) {
        this.etkinlikTurID = etkinlikTurID;
    }

    public String getEtkinlikTurAdi() {
        return etkinlikTurAdi;
    }

    public void setEtkinlikTurAdi(String etkinlikTurAdi) {
        this.etkinlikTurAdi = etkinlikTurAdi;
    }
}

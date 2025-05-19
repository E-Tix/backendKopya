package com.example.demo.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "salon")
public class SalonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salonID;

    //@OneToMany(mappedBy = "salon")
    //private List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntityList;

    @ManyToOne
    private SehirEntity sehirEntity;

    @Column(name = "salonAdi",nullable = false)
    private String salonAdi;

    @Column(name = "adres",nullable = false)
    private String adres;

    public SalonEntity(){}

    public SalonEntity(Long salonID, SehirEntity sehirEntity, String salonAdi, String adres) {
        this.salonID = salonID;
        this.sehirEntity = sehirEntity;
        this.salonAdi = salonAdi;
        this.adres = adres;
    }

    public SalonEntity(SehirEntity sehirEntity, String salonAdi, String adres) {
        this.sehirEntity = sehirEntity;
        this.salonAdi = salonAdi;
        this.adres = adres;
    }

    public Long getSalonID() {
        return salonID;
    }

    public void setSalonID(Long salonID) {
        this.salonID = salonID;
    }

    public String getSalonAdi() {
        return salonAdi;
    }

    public void setSalonAdi(String salonAdi) {
        this.salonAdi = salonAdi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public SehirEntity getSehirEntity() {
        return sehirEntity;
    }

    public void setSehirEntity(SehirEntity sehirEntity) {
        this.sehirEntity = sehirEntity;
    }
}
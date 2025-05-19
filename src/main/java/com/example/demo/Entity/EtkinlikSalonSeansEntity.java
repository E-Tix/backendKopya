package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "EtkinlikSalonSeans",uniqueConstraints = @UniqueConstraint(columnNames = {"seans_seansid"}))
public class EtkinlikSalonSeansEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long etkinlikSalonSeansID;

    @ManyToOne
    private EtkinlikEntity etkinlik;

    @ManyToOne
    private SalonEntity salon;

    @ManyToOne
    @JoinColumn(unique = true)
    private SeansEntity seans;


    public EtkinlikSalonSeansEntity(Long etkinlikSalonSeansID, EtkinlikEntity etkinlik, SalonEntity salon, SeansEntity seans) {
        this.etkinlikSalonSeansID = etkinlikSalonSeansID;
        this.etkinlik = etkinlik;
        this.salon = salon;
        this.seans = seans;
    }

    public EtkinlikSalonSeansEntity(EtkinlikEntity etkinlik, SalonEntity salon, SeansEntity seans) {
        this.etkinlik = etkinlik;
        this.salon = salon;
        this.seans = seans;
    }

    public EtkinlikSalonSeansEntity(){}

    public Long getEtkinlikSalonSeansID() {
        return etkinlikSalonSeansID;
    }

    public void setEtkinlikSalonSeansID(Long etkinlikSalonSeansID) {
        this.etkinlikSalonSeansID = etkinlikSalonSeansID;
    }

    public EtkinlikEntity getEtkinlik() {
        return etkinlik;
    }

    public void setEtkinlik(EtkinlikEntity etkinlik) {
        this.etkinlik = etkinlik;
    }

    public SalonEntity getSalon() {
        return salon;
    }

    public void setSalon(SalonEntity salon) {
        this.salon = salon;
    }

    public SeansEntity getSeans() {
        return seans;
    }

    public void setSeans(SeansEntity seans) {
        this.seans = seans;
    }
}

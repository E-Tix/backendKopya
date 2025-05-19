package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Koltuk")
public class KoltukEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long koltukID;

    @ManyToOne
    private SalonEntity salon;
    
    @Column
    private int koltukNumarasi;


    public KoltukEntity(Long koltukID, SalonEntity salon, int koltukNumarasi) {
        this.koltukID = koltukID;
        this.salon = salon;
        this.koltukNumarasi = koltukNumarasi;
    }

    public KoltukEntity(SalonEntity salon,int koltukNumarasi)
    {
        this.salon = salon;
        this.koltukNumarasi = koltukNumarasi;
    }

    public KoltukEntity(){}

    public Long getKoltukID() {
        return koltukID;
    }

    public void setKoltukID(Long koltukID) {
        this.koltukID = koltukID;
    }

    public SalonEntity getSalon() {
        return salon;
    }

    public void setSalon(SalonEntity salon) {
        this.salon = salon;
    }

    public int getKoltukNumarasi() {
        return koltukNumarasi;
    }

    public void setKoltukNumarasi(int koltukNumarasi) {
        this.koltukNumarasi = koltukNumarasi;
    }
}

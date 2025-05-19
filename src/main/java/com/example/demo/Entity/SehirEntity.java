package com.example.demo.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "sehir")
public class SehirEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plakaKodu")
    private Long plakaKodu;

    @Column(name = "sehirAdi")
    private String sehirAdi;

    //@OneToMany(mappedBy = "sehir")
    //List<EtkinlikEntity> etkinlikEntityList;

    public SehirEntity(Long plakaKodu, String sehirAdi/*, List<EtkinlikEntity> etkinlikEntityList*/) {
        this.plakaKodu = plakaKodu;
        this.sehirAdi = sehirAdi;
        //this.etkinlikEntityList = etkinlikEntityList;
    }
    public SehirEntity(String sehirAdi)
    {
        this.sehirAdi=sehirAdi;
    }

    public SehirEntity(){

    }

    public Long getPlakaKodu() {
        return plakaKodu;
    }

    public void setPlakaKodu(Long plakaKodu) {
        this.plakaKodu = plakaKodu;
    }

    public String getSehirAdi() {
        return sehirAdi;
    }

    public void setSehirAdi(String sehirAdi) {
        this.sehirAdi = sehirAdi;
    }

    /*public List<EtkinlikEntity> getEtkinlikEntityList() {
        return etkinlikEntityList;
    }

    public void setEtkinlikEntityList(List<EtkinlikEntity> etkinlikEntityList) {
        this.etkinlikEntityList = etkinlikEntityList;
    }*/
}

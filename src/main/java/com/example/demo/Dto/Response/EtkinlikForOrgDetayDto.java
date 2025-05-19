package com.example.demo.Dto.Response;

import com.example.demo.Entity.EtkinlikSalonSeansEntity;
import com.example.demo.Entity.EtkinlikTurEntity;
import com.example.demo.Entity.OrganizatorEntity;

import java.sql.Timestamp;
import java.util.List;

public class EtkinlikForOrgDetayDto {
    private Long etkinlikID;
    private String etkinlikAdi;
    private double biletFiyati;
    private int yasSiniri;
    private EtkinlikTurEntity etkinlikTur;
    private List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntities; //seans listesi lazım
    private int etkinlikSuresi;
    private String etkinlikAciklamasi;
    private OrganizatorEntity organizator;

    public EtkinlikForOrgDetayDto(Long etkinlikID, String etkinlikAdi, double biletFiyati, int yasSiniri, EtkinlikTurEntity etkinlikTur, List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntities, int etkinlikSuresi, String etkinlikAciklamasi, OrganizatorEntity organizator) {
        this.etkinlikID = etkinlikID;
        this.etkinlikAdi = etkinlikAdi;
        this.biletFiyati = biletFiyati;
        this.yasSiniri = yasSiniri;
        this.etkinlikTur = etkinlikTur;
        this.etkinlikSalonSeansEntities = etkinlikSalonSeansEntities;
        this.etkinlikSuresi = etkinlikSuresi;
        this.etkinlikAciklamasi = etkinlikAciklamasi;
        this.organizator = organizator;
    }

    public EtkinlikForOrgDetayDto() {
    }

    public Long getEtkinlikID() {
        return etkinlikID;
    }

    public void setEtkinlikID(Long etkinlikID) {
        this.etkinlikID = etkinlikID;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        this.etkinlikAdi = etkinlikAdi;
    }

    public double getBiletFiyati() {
        return biletFiyati;
    }

    public void setBiletFiyati(double biletFiyati) {
        this.biletFiyati = biletFiyati;
    }

    public int getYasSiniri() {
        return yasSiniri;
    }

    public void setYasSiniri(int yasSiniri) {
        this.yasSiniri = yasSiniri;
    }

    public EtkinlikTurEntity getEtkinlikTur() {
        return etkinlikTur;
    }

    public void setEtkinlikTur(EtkinlikTurEntity etkinlikTur) {
        this.etkinlikTur = etkinlikTur;
    }

    public List<EtkinlikSalonSeansEntity> getEtkinlikSalonSeansEntities() {
        return etkinlikSalonSeansEntities;
    }

    public void setEtkinlikSalonSeansEntities(List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntities) {
        this.etkinlikSalonSeansEntities = etkinlikSalonSeansEntities;
    }

    public int getEtkinlikSuresi() {
        return etkinlikSuresi;
    }

    public void setEtkinlikSuresi(int etkinlikSuresi) {
        this.etkinlikSuresi = etkinlikSuresi;
    }

    public String getEtkinlikAciklamasi() {
        return etkinlikAciklamasi;
    }

    public void setEtkinlikAciklamasi(String etkinlikAciklamasi) {
        this.etkinlikAciklamasi = etkinlikAciklamasi;
    }

    public OrganizatorEntity getOrganizator() {
        return organizator;
    }

    public void setOrganizator(OrganizatorEntity organizator) {
        this.organizator = organizator;
    }
}

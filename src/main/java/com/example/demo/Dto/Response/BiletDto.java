package com.example.demo.Dto.Response;

import com.example.demo.Entity.SeansEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BiletDto {
    private Long biletId;
    private int koltukNo;
    private String etkinlikAdi;
    private SehirDto sehirDto;
    private SalonDto salonDto;
    //burayı düzelt
    private SeansEntity seansEntity1;
    @JsonProperty("seansEntity")
    private SeansDto seansEntity;
    private Float odenenMiktar;

    public BiletDto(Long biletId,Float odenenMiktar, int koltukNo, String etkinlikAdi, SehirDto sehirDto, SalonDto salonDto, SeansEntity seansEntity1) {
        this.biletId = biletId;
        this.odenenMiktar = odenenMiktar;
        this.koltukNo = koltukNo;
        this.etkinlikAdi = etkinlikAdi;
        this.sehirDto = sehirDto;
        this.salonDto = salonDto;
        this.seansEntity1 = seansEntity1;
    }

    public BiletDto(Long biletId, int koltukNo, String etkinlikAdi, SehirDto sehirDto, SalonDto salonDto, SeansDto seansEntity, Float odenenMiktar) {
        this.biletId = biletId;
        this.koltukNo = koltukNo;
        this.etkinlikAdi = etkinlikAdi;
        this.sehirDto = sehirDto;
        this.salonDto = salonDto;
        this.seansEntity = seansEntity;
        this.odenenMiktar = odenenMiktar;
    }

    public BiletDto() {
    }

    public Long getBiletId() {
        return biletId;
    }

    public void setBiletId(Long biletId) {
        this.biletId = biletId;
    }

    public int getKoltukNo() {
        return koltukNo;
    }

    public void setKoltukNo(int koltukNo) {
        this.koltukNo = koltukNo;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        this.etkinlikAdi = etkinlikAdi;
    }

    public SehirDto getSehirDto() {
        return sehirDto;
    }

    public void setSehirDto(SehirDto sehirDto) {
        this.sehirDto = sehirDto;
    }

    public SalonDto getSalonDto() {
        return salonDto;
    }

    public void setSalonDto(SalonDto salonDto) {
        this.salonDto = salonDto;
    }

    public SeansEntity getSeansEntity1() {
        return seansEntity1;
    }

    public void setSeansEntity1(SeansEntity seansEntity1) {
        this.seansEntity1 = seansEntity1;
    }

    public Float getOdenenMiktar() {
        return odenenMiktar;
    }

    public void setOdenenMiktar(Float odenenMiktar) {
        this.odenenMiktar = odenenMiktar;
    }

    public SeansDto getSeansEntity() {
        return seansEntity;
    }

    public void setSeansEntity(SeansDto seansEntity) {
        this.seansEntity = seansEntity;
    }
}

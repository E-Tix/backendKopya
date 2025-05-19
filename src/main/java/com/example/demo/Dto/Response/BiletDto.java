package com.example.demo.Dto.Response;

import com.example.demo.Dto.Request.SeansDto;
import com.example.demo.Entity.SeansEntity;
import com.fasterxml.jackson.annotation.JsonProperty;


import com.fasterxml.jackson.annotation.JsonProperty;

public class BiletDto {
    private Long biletId;
    private Integer koltukNo;
    private String etkinlikAdi;
    private SehirDto sehirDto;
    private SalonDto salonDto;
    private SeansEntity seans;

    // JSON’da "seansEntity" olarak gelsin diye burayı isimlendiriyoruz:
    @JsonProperty("seansEntity")
    private SeansDto seansEntity;

    private Float odenenMiktar;

    public BiletDto(
            Long biletId,
            Integer koltukNo,
            String etkinlikAdi,
            SehirDto sehirDto,
            SalonDto salonDto,
            SeansDto seansEntity,
            Float odenenMiktar
    ) {
        this.biletId = biletId;
        this.koltukNo = koltukNo;
        this.etkinlikAdi = etkinlikAdi;
        this.sehirDto = sehirDto;
        this.salonDto = salonDto;
        this.seansEntity = seansEntity;
        this.odenenMiktar = odenenMiktar;
    }

    public BiletDto(Long biletId, Integer koltukNo, String etkinlikAdi, SehirDto sehirDto, SalonDto salonDto, SeansEntity seans, Float odenenMiktar) {
        this.biletId = biletId;
        this.koltukNo = koltukNo;
        this.etkinlikAdi = etkinlikAdi;
        this.sehirDto = sehirDto;
        this.salonDto = salonDto;
        this.seans = seans;
        this.odenenMiktar = odenenMiktar;
    }

    public SeansEntity getSeans() {
        return seans;
    }

    public void setSeans(SeansEntity seans) {
        this.seans = seans;
    }

    public Long getBiletId() {
        return biletId;
    }

    public Integer getKoltukNo() {
        return koltukNo;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public SehirDto getSehirDto() {
        return sehirDto;
    }

    public SalonDto getSalonDto() {
        return salonDto;
    }

    // Sadece bu tek getter kalsın:
    public SeansDto getSeansEntity() {
        return seansEntity;
    }

    public Float getOdenenMiktar() {
        return odenenMiktar;
    }
}

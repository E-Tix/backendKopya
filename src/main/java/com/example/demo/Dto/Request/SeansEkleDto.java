package com.example.demo.Dto.Request;

import java.sql.Timestamp;

public class SeansEkleDto {
    private Timestamp tarih;

    public SeansEkleDto(Timestamp tarih) {
        this.tarih = tarih;
    }

    public SeansEkleDto() {
    }

    public Timestamp getTarih() {
        return tarih;
    }

    public void setTarih(Timestamp tarih) {
        this.tarih = tarih;
    }
}

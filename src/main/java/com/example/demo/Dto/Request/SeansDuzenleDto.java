package com.example.demo.Dto.Request;

import java.sql.Timestamp;

public class SeansDuzenleDto {
    private Long seansId;
    private Timestamp tarih;

    public SeansDuzenleDto(Long seansId, Timestamp tarih) {
        this.seansId = seansId;
        this.tarih = tarih;
    }

    public SeansDuzenleDto() {
    }

    public Long getSeansId() {
        return seansId;
    }

    public void setSeansId(Long seansId) {
        this.seansId = seansId;
    }

    public Timestamp getTarih() {
        return tarih;
    }

    public void setTarih(Timestamp tarih) {
        this.tarih = tarih;
    }
}

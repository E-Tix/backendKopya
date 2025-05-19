package com.example.demo.Dto.Request;

public class BiletSilDto {
    private long biletId;

    public BiletSilDto(long biletId) {
        this.biletId = biletId;
    }

    public BiletSilDto() {
    }

    public long getBiletId() {
        return biletId;
    }

    public void setBiletId(long biletId) {
        this.biletId = biletId;
    }
}

package com.example.demo.Dto.Response;

import com.example.demo.Entity.KullaniciEntity;
import com.example.demo.Entity.SeansEntity;

public class SilinecekBiletDto {
    private BiletDto biletDto;

    private KullaniciDtoForSilinecekBiletDto kullaniciDtoForSilinecekBiletDto;

    public SilinecekBiletDto(KullaniciDtoForSilinecekBiletDto kullaniciDtoForSilinecekBiletDto, BiletDto biletDto) {
        this.kullaniciDtoForSilinecekBiletDto=kullaniciDtoForSilinecekBiletDto;
        this.biletDto = biletDto;
    }

    public SilinecekBiletDto() {
    }

    public KullaniciDtoForSilinecekBiletDto getKullaniciDtoForSilinecekBiletDto() {
        return kullaniciDtoForSilinecekBiletDto;
    }

    public void setKullaniciDtoForSilinecekBiletDto(KullaniciDtoForSilinecekBiletDto kullaniciDtoForSilinecekBiletDto) {
        this.kullaniciDtoForSilinecekBiletDto = kullaniciDtoForSilinecekBiletDto;
    }

    public BiletDto getBiletDto() {
        return biletDto;
    }

    public void setBiletDto(BiletDto biletDto) {
        this.biletDto = biletDto;
    }
}

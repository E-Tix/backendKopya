package com.example.demo.Dto.Response;

public class SehirDto {
    private Long plakaKodu;
    private String sehirAdi;

    public SehirDto(Long plakaKodu, String sehirAdi) {
        this.plakaKodu = plakaKodu;
        this.sehirAdi = sehirAdi;
    }

    public SehirDto() {
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
}

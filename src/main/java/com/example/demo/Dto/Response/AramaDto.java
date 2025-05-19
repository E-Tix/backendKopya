package com.example.demo.Dto.Response;

public class AramaDto {
    private Long etkinlikId;
    private String etkinlikAdi;
    private String kapakFotografi;
    private String sehirAdi;
    private String etkinlikTurAdi;

    public AramaDto(Long etkinlikId, String etkinlikAdi, String sehirAdi, String etkinlikTurAdi, String kapakFotografi) {
        this.etkinlikId = etkinlikId;
        this.etkinlikAdi = etkinlikAdi;
        this.kapakFotografi = kapakFotografi;
        this.sehirAdi = sehirAdi;
        this.etkinlikTurAdi = etkinlikTurAdi;
    }

    public AramaDto() {
    }

    public Long getEtkinlikId() {
        return etkinlikId;
    }

    public void setEtkinlikId(Long etkinlikId) {
        this.etkinlikId = etkinlikId;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        this.etkinlikAdi = etkinlikAdi;
    }

    public String getKapakFotografi() {
        return kapakFotografi;
    }

    public void setKapakFotografi(String kapakFotografi) {
        this.kapakFotografi = kapakFotografi;
    }

    public String getSehirAdi() {
        return sehirAdi;
    }

    public void setSehirAdi(String sehirAdi) {
        this.sehirAdi = sehirAdi;
    }

    public String getEtkinlikTurAdi() {
        return etkinlikTurAdi;
    }

    public void setEtkinlikTurAdi(String etkinlikTurAdi) {
        this.etkinlikTurAdi = etkinlikTurAdi;
    }
}

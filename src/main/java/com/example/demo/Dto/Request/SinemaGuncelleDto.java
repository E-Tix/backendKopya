package com.example.demo.Dto.Request;

public class SinemaGuncelleDto {
    private Long sinemaId;
    private EtkinlikGuncelleDto etkinlikGuncelleDto;
    private String fragmanLinki;

    public SinemaGuncelleDto(Long sinemaId, EtkinlikGuncelleDto etkinlikGuncelleDto, String fragmanLinki) {
        this.sinemaId = sinemaId;
        this.etkinlikGuncelleDto = etkinlikGuncelleDto;
        this.fragmanLinki = fragmanLinki;
    }

    public SinemaGuncelleDto() {}

    public Long getSinemaId() {
        return sinemaId;
    }

    public void setSinemaId(Long sinemaId) {
        this.sinemaId = sinemaId;
    }

    public EtkinlikGuncelleDto getEtkinlikGuncelleDto() {
        return etkinlikGuncelleDto;
    }

    public void setEtkinlikGuncelleDto(EtkinlikGuncelleDto etkinlikGuncelleDto) {
        this.etkinlikGuncelleDto = etkinlikGuncelleDto;
    }

    public String getFragmanLinki() {
        return fragmanLinki;
    }

    public void setFragmanLinki(String fragmanLinki) {
        this.fragmanLinki = fragmanLinki;
    }
}

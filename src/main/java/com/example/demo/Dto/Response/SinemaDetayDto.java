package com.example.demo.Dto.Response;

public class SinemaDetayDto {
    private Long sinemaId;
    private EtkinlikDetayDto etkinlikDetayDto;
    private String fragmanLinki;
    private float imdbPuani;

    public SinemaDetayDto(Long sinemaId, EtkinlikDetayDto etkinlikDetayDto, String fragmanLinki, float imdbPuani) {
        this.sinemaId = sinemaId;
        this.etkinlikDetayDto = etkinlikDetayDto;
        this.fragmanLinki = fragmanLinki;
        this.imdbPuani = imdbPuani;
    }

    public SinemaDetayDto() {
    }

    public Long getSinemaId() {
        return sinemaId;
    }

    public void setSinemaId(Long sinemaId) {
        this.sinemaId = sinemaId;
    }

    public EtkinlikDetayDto getEtkinlikDetayDto() {
        return etkinlikDetayDto;
    }

    public void setEtkinlikDetayDto(EtkinlikDetayDto etkinlikDetayDto) {
        this.etkinlikDetayDto = etkinlikDetayDto;
    }

    public String getFragmanLinki() {
        return fragmanLinki;
    }

    public void setFragmanLinki(String fragmanLinki) {
        this.fragmanLinki = fragmanLinki;
    }

    public float getImdbPuani() {
        return imdbPuani;
    }

    public void setImdbPuani(float imdbPuani) {
        this.imdbPuani = imdbPuani;
    }
}

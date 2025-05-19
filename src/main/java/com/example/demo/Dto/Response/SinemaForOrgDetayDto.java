package com.example.demo.Dto.Response;

public class SinemaForOrgDetayDto {
    private Long sinemaId;
    private EtkinlikForOrgDetayDto etkinlikForOrgDetayDto;
    private String fragmanLinki;
    private float imdbPuani;

    public SinemaForOrgDetayDto(Long sinemaId, EtkinlikForOrgDetayDto etkinlikForOrgDetayDto, String fragmanLinki, float imdbPuani) {
        this.sinemaId = sinemaId;
        this.etkinlikForOrgDetayDto = etkinlikForOrgDetayDto;
        this.fragmanLinki = fragmanLinki;
        this.imdbPuani = imdbPuani;
    }

    public SinemaForOrgDetayDto(){}

    public Long getSinemaId() {
        return sinemaId;
    }

    public void setSinemaId(Long sinemaId) {
        this.sinemaId = sinemaId;
    }

    public EtkinlikForOrgDetayDto getEtkinlikForOrgDetayDto() {
        return etkinlikForOrgDetayDto;
    }

    public void setEtkinlikForOrgDetayDto(EtkinlikForOrgDetayDto etkinlikForOrgDetayDto) {
        this.etkinlikForOrgDetayDto = etkinlikForOrgDetayDto;
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

package com.example.demo.Dto.Request;

public class SinemaEkleDto {
    private EtkinlikEkleDto etkinlikEkleDto;// her seans için ayrı salon olabilmesi gerekiyor onu meryeme söylemeyi unutma
    private String fragmanLinki;

    public SinemaEkleDto(EtkinlikEkleDto etkinlikEkleDto,String fragmanLinki)
    {
        this.etkinlikEkleDto = etkinlikEkleDto;
        this.fragmanLinki = fragmanLinki;
    }

    public SinemaEkleDto(){}

    public EtkinlikEkleDto getEtkinlikEkleDto() {
        return etkinlikEkleDto;
    }

    public void setEtkinlikEkleDto(EtkinlikEkleDto etkinlikEkleDto) {
        this.etkinlikEkleDto = etkinlikEkleDto;
    }

    public String getFragmanLinki() {
        return fragmanLinki;
    }

    public void setFragmanLinki(String fragmanLinki) {
        this.fragmanLinki = fragmanLinki;
    }

    @Override
    public String toString() {
        return "SinemaEkleDto{" +
                "etkinlikEkleDto=" + etkinlikEkleDto +
                ", fragmanLinki='" + fragmanLinki + '\'' +
                '}';
    }
}

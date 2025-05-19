package com.example.demo.Dto.Request;

public class BiletAlDto {
    private Long seansId;
    private Long koltukId;
    private Float odenenMiktar;
    private Boolean odendiMi;

    public BiletAlDto(Long seansId, Long koltukId, Float odenenMiktar, Boolean odendiMi) {
        this.seansId = seansId;
        this.koltukId = koltukId;
        this.odenenMiktar = odenenMiktar;
        this.odendiMi = odendiMi;
    }

    public BiletAlDto(){}

    public Long getSeansId() {
        return seansId;
    }

    public void setSeansId(Long seansId) {
        this.seansId = seansId;
    }

    public Long getKoltukId() {
        return koltukId;
    }

    public void setKoltukId(Long koltukId) {
        this.koltukId = koltukId;
    }

    public Float getOdenenMiktar() {
        return odenenMiktar;
    }

    public void setOdenenMiktar(Float odenenMiktar) {
        this.odenenMiktar = odenenMiktar;
    }

    public boolean isOdendiMi() {
        return odendiMi;
    }

    public void setOdendiMi(Boolean odendiMi) {
        this.odendiMi = odendiMi;
    }
}

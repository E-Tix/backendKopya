package com.example.demo.Dto.Request;

import java.sql.Timestamp;

/**
 * Data Transfer Object for SeansEntity
 */
public class SeansDto {
    private Long seansID;
    private Timestamp tarih;
    private Timestamp bitisTarih;
    private boolean tarihiGectiMi;
    private Timestamp olusturulmaTarihi;

    public SeansDto() {
    }

    public SeansDto(Long seansID, Timestamp tarih, Timestamp bitisTarih, boolean tarihiGectiMi, Timestamp olusturulmaTarihi) {
        this.seansID = seansID;
        this.tarih = tarih;
        this.bitisTarih = bitisTarih;
        this.tarihiGectiMi = tarihiGectiMi;
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public Long getSeansID() {
        return seansID;
    }

    public void setSeansID(Long seansID) {
        this.seansID = seansID;
    }

    public Timestamp getTarih() {
        return tarih;
    }

    public void setTarih(Timestamp tarih) {
        this.tarih = tarih;
    }

    public Timestamp getBitisTarih() {
        return bitisTarih;
    }

    public void setBitisTarih(Timestamp bitisTarih) {
        this.bitisTarih = bitisTarih;
    }

    public boolean isTarihiGectiMi() {
        return tarihiGectiMi;
    }

    public void setTarihiGectiMi(boolean tarihiGectiMi) {
        this.tarihiGectiMi = tarihiGectiMi;
    }

    public Timestamp getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(Timestamp olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    @Override
    public String toString() {
        return "SeansDto{" +
                "seansID=" + seansID +
                ", tarih=" + tarih +
                ", bitisTarih=" + bitisTarih +
                ", tarihiGectiMi=" + tarihiGectiMi +
                ", olusturulmaTarihi=" + olusturulmaTarihi +
                '}';
    }
}

package com.example.demo.Dto.Response;

import java.sql.Timestamp;

public class SeansDto {
    private Long seansId;
    private Timestamp tarih;
    private Timestamp bitisTarih;
    private boolean tarihiGectiMi;
    private Timestamp olusturulmaTarihi;

    public SeansDto() {}

    public SeansDto(Long seansId,
                    Timestamp tarih,
                    Timestamp bitisTarih,
                    boolean tarihiGectiMi,
                    Timestamp olusturulmaTarihi) {
        this.seansId = seansId;
        this.tarih = tarih;
        this.bitisTarih = bitisTarih;
        this.tarihiGectiMi = tarihiGectiMi;
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public Long getSeansId() {
        return seansId;
    }

    public void setSeansId(Long seansId) {
        this.seansId = seansId;
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
}

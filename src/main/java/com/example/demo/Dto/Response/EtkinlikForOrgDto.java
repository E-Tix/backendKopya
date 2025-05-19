package com.example.demo.Dto.Response;

import com.example.demo.Entity.OrganizatorEntity;

import java.sql.Timestamp;

public class EtkinlikForOrgDto {
    private Long id;
    private String etkinlikAdi; //Etkinlik adını silmedim sadece yukarı aldım
    private Timestamp olusturulmaTarihi;
    private int etkinlikSuresi;
    private int yasSiniri;
    private String kapakFotografi;
    private String etkinlikTurAdi;
    private Long sinemaId;
    private boolean tarihiGectiMi;
    //Sadece etkinlik adı ve kapak fotoğrafı vardı ancak etkinlik kartlarında gösterilemsi için
    //bu bilgilerin hepsi lazım. O nedenle ekledim

    public EtkinlikForOrgDto(Long id, String etkinlikAdi, Timestamp olusturulmaTarihi, int etkinlikSuresi, int yasSiniri, String kapakFotografi, String etkinlikTurAdi, Long sinemaId, boolean tarihiGectiMi) {
        this.id = id;
        this.etkinlikAdi = etkinlikAdi;
        this.olusturulmaTarihi = olusturulmaTarihi;
        this.etkinlikSuresi = etkinlikSuresi;
        this.yasSiniri = yasSiniri;
        this.kapakFotografi = kapakFotografi;
        this.etkinlikTurAdi = etkinlikTurAdi;
        this.sinemaId = sinemaId;
        this.tarihiGectiMi = tarihiGectiMi;
    }

    public boolean isTarihiGectiMi() {
        return tarihiGectiMi;
    }

    public void setTarihiGectiMi(boolean tarihiGectiMi) {
        this.tarihiGectiMi = tarihiGectiMi;
    }

    public EtkinlikForOrgDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtkinlikAdi() {
        return etkinlikAdi;
    }

    public void setEtkinlikAdi(String etkinlikAdi) {
        this.etkinlikAdi = etkinlikAdi;
    }

    public Timestamp getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(Timestamp olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public int getEtkinlikSuresi() {
        return etkinlikSuresi;
    }

    public void setEtkinlikSuresi(int etkinlikSuresi) {
        this.etkinlikSuresi = etkinlikSuresi;
    }

    public int getYasSiniri() {
        return yasSiniri;
    }

    public void setYasSiniri(int yasSiniri) {
        this.yasSiniri = yasSiniri;
    }

    public String getKapakFotografi() {
        return kapakFotografi;
    }

    public void setKapakFotografi(String kapakFotografi) {
        this.kapakFotografi = kapakFotografi;
    }

    public String getEtkinlikTurAdi() {
        return etkinlikTurAdi;
    }

    public void setEtkinlikTurAdi(String etkinlikTurAdi) {
        this.etkinlikTurAdi = etkinlikTurAdi;
    }

    public Long getSinemaId() {
        return sinemaId;
    }

    public void setSinemaId(Long sinemaId) {
        this.sinemaId = sinemaId;
    }
}

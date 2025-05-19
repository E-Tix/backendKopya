package com.example.demo.Dto.Request;

public class ChangePasswordDto {
    private String eskiSifre;
    private String yeniSifre;
    private String yeniSifreTekrar;

    public ChangePasswordDto(String eskiSifre, String yeniSifre, String yeniSifreTekrar) {
        this.eskiSifre = eskiSifre;
        this.yeniSifre = yeniSifre;
        this.yeniSifreTekrar = yeniSifreTekrar;
    }

    public ChangePasswordDto() {
    }

    public String getEskiSifre() {
        return eskiSifre;
    }

    public void setEskiSifre(String eskiSifre) {
        this.eskiSifre = eskiSifre;
    }

    public String getYeniSifre() {
        return yeniSifre;
    }

    public void setYeniSifre(String yeniSifre) {
        this.yeniSifre = yeniSifre;
    }

    public String getYeniSifreTekrar() {
        return yeniSifreTekrar;
    }

    public void setYeniSifreTekrar(String yeniSifreTekrar) {
        this.yeniSifreTekrar = yeniSifreTekrar;
    }
}

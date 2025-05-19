package com.example.demo.Dto.Response;

public class KullaniciDtoForSilinecekBiletDto {
    private String kullaniciAdi;
    private String email;

    public KullaniciDtoForSilinecekBiletDto(String kullaniciAdi, String email) {
        this.kullaniciAdi = kullaniciAdi;
        this.email = email;
    }

    public KullaniciDtoForSilinecekBiletDto() {
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

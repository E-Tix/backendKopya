package com.example.demo.Dto.Response;

public class SalonDto {
    private Long salonID;
    private String salonAdi;
    private String salonAdresi;

    public SalonDto(Long salonID, String salonAdi, String salonAdresi) {
        this.salonID = salonID;
        this.salonAdi = salonAdi;
        this.salonAdresi = salonAdresi;
    }

    public SalonDto() {
    }

    public Long getSalonID() {
        return salonID;
    }

    public void setSalonID(Long salonID) {
        this.salonID = salonID;
    }

    public String getSalonAdi() {
        return salonAdi;
    }

    public void setSalonAdi(String salonAdi) {
        this.salonAdi = salonAdi;
    }

    public String getSalonAdresi() {
        return salonAdresi;
    }

    public void setSalonAdresi(String salonAdresi) {
        this.salonAdresi = salonAdresi;
    }
}

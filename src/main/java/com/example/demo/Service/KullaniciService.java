package com.example.demo.Service;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.KullaniciProfiliDto;
import com.example.demo.Dto.Response.BiletDto;
import com.example.demo.Dto.Response.SalonDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KullaniciService {


    private final KullaniciRepository kullaniciRepository;
    private final BiletRepository biletRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SehirRepository sehirRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;
    private final KullaniciBiletRepository kullaniciBiletRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;

    @Autowired
    public KullaniciService(BCryptPasswordEncoder passwordEncoder, SehirRepository sehirRepository,EtkinlikSalonSeansRepository etkinlikSalonSeansRepository,SeansKoltukBiletRepository seansKoltukBiletRepository,KullaniciRepository kullaniciRepository,BiletRepository biletRepository,KullaniciBiletRepository kullaniciBiletRepository){
        this.passwordEncoder=passwordEncoder;
        this.sehirRepository=sehirRepository;
        this.etkinlikSalonSeansRepository=etkinlikSalonSeansRepository;
        this.seansKoltukBiletRepository=seansKoltukBiletRepository;
        this.kullaniciRepository=kullaniciRepository;
        this.kullaniciBiletRepository=kullaniciBiletRepository;
        this.biletRepository=biletRepository;
    }

    public boolean kullaniciEkle(KullaniciEntity kullanici)
    {
        kullaniciRepository.save(kullanici);
        return true;
    }


    public boolean changePassword(ChangePasswordDto changePasswordDto, Long id) {
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(id);
        if (kullanici != null) {
            System.out.println("Kullanıcı bulundu: " + kullanici.getKullaniciAdi());

            if (passwordEncoder.matches(changePasswordDto.getEskiSifre(), kullanici.getSifre())) {
                System.out.println("Eski şifre doğru");

                if (changePasswordDto.getYeniSifre().equals(changePasswordDto.getYeniSifreTekrar())) {
                    kullanici.setSifre(passwordEncoder.encode(changePasswordDto.getYeniSifre()));
                    kullaniciRepository.save(kullanici);
                    System.out.println("Şifre başarıyla güncellendi");
                    return true;
                } else {
                    System.out.println("Yeni şifreler uyuşmuyor");
                    return false;
                }
            } else {
                System.out.println("Eski şifre hatalı");
                return false;
            }
        } else {
            System.out.println("Kullanıcı bulunamadı");
            return false;
        }
    }

    public List<BiletDto> getBiletler(Long kullaniciId)
    {
        List<BiletDto> biletDtoList = new ArrayList<>();
        List<BiletEntity> kullaniciyaAitBiletler = kullaniciBiletRepository.findBiletlerByKullanici(kullaniciId);

        SeansKoltukBiletEntity seansKoltukBilet;
        EtkinlikSalonSeansEntity etkinlikSalonSeans;

        for (BiletEntity b:kullaniciyaAitBiletler)
        {
            if (!b.isIptalEdildiMi()||!kullaniciBiletRepository.findByBilet_BiletID(b.getBiletID()).isIptalIstendiMi())
            {
                seansKoltukBilet=seansKoltukBiletRepository.findSeansKoltukBiletEntityByBilet(b);
                etkinlikSalonSeans=etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(seansKoltukBilet.getSeans());

                biletDtoList.add(new BiletDto(
                        b.getBiletID(),
                        b.getOdenenMiktar(),
                        seansKoltukBilet.getKoltuk().getKoltukNumarasi(),
                        etkinlikSalonSeans.getEtkinlik().getEtkinlikAdi(),
                        new SehirDto(etkinlikSalonSeans.getEtkinlik().getSehir().getPlakaKodu(),etkinlikSalonSeans.getEtkinlik().getSehir().getSehirAdi()),
                        new SalonDto(etkinlikSalonSeans.getSalon().getSalonID(),etkinlikSalonSeans.getSalon().getSalonAdi(),etkinlikSalonSeans.getSalon().getAdres()),
                        etkinlikSalonSeans.getSeans()
                ));
            }
        }

        return biletDtoList;
    }

    public KullaniciProfiliDto getKullaniciProfili(Long id)
    {
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(id);
        if (kullanici==null){
            throw new EntityNotFoundException("kullanıcı bulunamadı");
        }
        return new KullaniciProfiliDto(kullanici.getAdSoyad(), kullanici.getKullaniciAdi(), kullanici.getEmail(), kullanici.getSehir(), kullanici.getTelNo());
    }
    public long getUserIdByUsername(String username){
        return kullaniciRepository.getUserIdByKullaniciAdi(username);
    }

    public boolean kullaniciProfiliDuzenle(KullaniciProfiliDto kullaniciProfiliDto,Long id)
    {
        KullaniciEntity kullanici=kullaniciRepository.findByKullaniciID(id);
        if (kullanici==null)
        {
            return false;
        }else {
            kullanici.setAdSoyad(kullaniciProfiliDto.getAdSoyad());
            kullanici.setEmail(kullaniciProfiliDto.getEmail());
            kullanici.setSehir(kullaniciProfiliDto.getSehir());
            kullanici.setTelNo(kullaniciProfiliDto.getTelNo());
            kullaniciRepository.save(kullanici);

            return true;
        }
    }

    @Transactional
    public boolean kullaniciSehirDuzenle(Long userId, SehirDto sehirDto){
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(userId);
        kullanici.setSehir(sehirRepository.findByPlakaKodu(sehirDto.getPlakaKodu()));
        kullaniciRepository.save(kullanici);
        return true;
    }
}

package com.example.demo.Service;

import com.example.demo.Dto.KullaniciProfiliDto;
import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Response.BiletDto;
import com.example.demo.Dto.Response.SalonDto;
import com.example.demo.Dto.Response.SeansDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.BiletRepository;
import com.example.demo.Repository.EtkinlikSalonSeansRepository;
import com.example.demo.Repository.KullaniciBiletRepository;
import com.example.demo.Repository.SeansKoltukBiletRepository;
import com.example.demo.Repository.KullaniciRepository;
import com.example.demo.Repository.SehirRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final SehirRepository sehirRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;
    private final KullaniciBiletRepository kullaniciBiletRepository;
    private final BiletRepository biletRepository;

    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository,
                            SehirRepository sehirRepository,
                            EtkinlikSalonSeansRepository etkinlikSalonSeansRepository,
                            SeansKoltukBiletRepository seansKoltukBiletRepository,
                            KullaniciBiletRepository kullaniciBiletRepository,
                            BiletRepository biletRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.sehirRepository = sehirRepository;
        this.etkinlikSalonSeansRepository = etkinlikSalonSeansRepository;
        this.seansKoltukBiletRepository = seansKoltukBiletRepository;
        this.kullaniciBiletRepository = kullaniciBiletRepository;
        this.biletRepository = biletRepository;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean kullaniciEkle(KullaniciEntity kullanici) {
        kullaniciRepository.save(kullanici);
        return true;
    }

    public boolean changePassword(ChangePasswordDto dto, Long id) {
        KullaniciEntity k = kullaniciRepository.findByKullaniciID(id);
        if (k == null) throw new EntityNotFoundException("Kullanıcı bulunamadı: " + id);

        if (!passwordEncoder.matches(dto.getEskiSifre(), k.getSifre())) {
            return false; // eski şifre yanlış
        }
        if (!dto.getYeniSifre().equals(dto.getYeniSifreTekrar())) {
            return false; // yeni şifreler uyuşmuyor
        }
        k.setSifre(passwordEncoder.encode(dto.getYeniSifre()));
        kullaniciRepository.save(k);
        return true;
    }

    public List<BiletDto> getBiletler(Long kullaniciId) {
        List<BiletDto> dtos = new ArrayList<>();

        // iptal edilmemiş biletler
        List<BiletEntity> bilekler = kullaniciBiletRepository.findBiletlerByKullanici(kullaniciId);

        for (BiletEntity b : bilekler) {
            if (b.isIptalEdildiMi()) continue;

            // SeansKoltukBilet ilişkisini al
            Optional<SeansKoltukBiletEntity> skbOpt = seansKoltukBiletRepository.findByBilet(b);
            if (skbOpt.isEmpty()) {
                System.err.println("Uyarı: SeansKoltukBilet bulunamadı for BiletID=" + b.getBiletID());
                continue;
            }
            SeansKoltukBiletEntity skb = skbOpt.get();

            // EtkinlikSalonSeans ilişkisini al
            EtkinlikSalonSeansEntity ess = etkinlikSalonSeansRepository
                    .findEtkinlikSalonSeansEntityBySeans(skb.getSeans());
            if (ess == null) {
                System.err.println("Uyarı: EtkinlikSalonSeans bulunamadı for SeansID=" +
                        skb.getSeans().getSeansID());
                continue;
            }

            // DTO oluştur
            SeansDto seansDto = new SeansDto(
                    ess.getSeans().getSeansID(),
                    ess.getSeans().getTarih(),
                    ess.getSeans().getBitisTarih(),
                    ess.getSeans().isTarihiGectiMi(),
                    ess.getSeans().getOlusturulmaTarihi()
            );
            BiletDto dto = new BiletDto(
                                b.getBiletID(),
                    skb.getKoltuk().getKoltukNumarasi(),
                    ess.getEtkinlik().getEtkinlikAdi(),
                    new SehirDto(
                                        ess.getEtkinlik().getSehir().getPlakaKodu(),
                                        ess.getEtkinlik().getSehir().getSehirAdi()
                                ),
                    new SalonDto(
                                        ess.getSalon().getSalonID(),
                                        ess.getSalon().getSalonAdi(),
                                        ess.getSalon().getAdres()
                                ),
                    seansDto,
                    b.getOdenenMiktar()
                        );
            dtos.add(dto);
        }
        return dtos;
    }

    public KullaniciProfiliDto getKullaniciProfili(Long id) {
        KullaniciEntity k = kullaniciRepository.findByKullaniciID(id);
        if (k == null) throw new EntityNotFoundException("Kullanıcı bulunamadı: " + id);
        return new KullaniciProfiliDto(
                k.getAdSoyad(),
                k.getKullaniciAdi(),
                k.getEmail(),
                k.getSehir(),
                k.getTelNo()
        );
    }

    public boolean kullaniciProfiliDuzenle(KullaniciProfiliDto dto, Long id) {
        KullaniciEntity k = kullaniciRepository.findByKullaniciID(id);
        if (k == null) return false;
        k.setAdSoyad(dto.getAdSoyad());
        k.setEmail(dto.getEmail());
        k.setSehir(dto.getSehir());
        k.setTelNo(dto.getTelNo());
        kullaniciRepository.save(k);
        return true;
    }

    @Transactional
    public boolean kullaniciSehirDuzenle(Long userId, SehirDto sehirDto) {
        KullaniciEntity k = kullaniciRepository.findByKullaniciID(userId);
        if (k == null) return false;
        k.setSehir(sehirRepository.findByPlakaKodu(sehirDto.getPlakaKodu()));
        kullaniciRepository.save(k);
        return true;
    }

    public long getUserIdByUsername(String username) {
        return kullaniciRepository.getUserIdByKullaniciAdi(username);
    }
}

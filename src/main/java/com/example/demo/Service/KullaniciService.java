package com.example.demo.Service;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Request.SeansDto;
import com.example.demo.Dto.KullaniciProfiliDto;
import com.example.demo.Dto.Response.BiletDto;
import com.example.demo.Dto.Response.SalonDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
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
    private final BiletRepository biletRepository;
    private final SehirRepository sehirRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;
    private final KullaniciBiletRepository kullaniciBiletRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;

    @Autowired
    public KullaniciService(
            SehirRepository sehirRepository,
            EtkinlikSalonSeansRepository etkinlikSalonSeansRepository,
            SeansKoltukBiletRepository seansKoltukBiletRepository,
            KullaniciRepository kullaniciRepository,
            BiletRepository biletRepository,
            KullaniciBiletRepository kullaniciBiletRepository
    ) {
        this.sehirRepository = sehirRepository;
        this.etkinlikSalonSeansRepository = etkinlikSalonSeansRepository;
        this.seansKoltukBiletRepository = seansKoltukBiletRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.kullaniciBiletRepository = kullaniciBiletRepository;
        this.biletRepository = biletRepository;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean changePassword(ChangePasswordDto dto, Long id) {
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(id);
        if (kullanici == null) return false;

        if (!passwordEncoder.matches(dto.getEskiSifre(), kullanici.getSifre())) return false;
        if (!dto.getYeniSifre().equals(dto.getYeniSifreTekrar())) return false;

        kullanici.setSifre(passwordEncoder.encode(dto.getYeniSifre()));
        kullaniciRepository.save(kullanici);
        return true;
    }

    public List<BiletDto> getBiletler(Long kullaniciId) {
        List<BiletDto> biletDtoList = new ArrayList<>();

        List<BiletEntity> biletler = kullaniciBiletRepository.findBiletlerByKullanici(kullaniciId);
        for (BiletEntity b : biletler) {
            // skip if canceled or cancellation requested
            if (b.isIptalEdildiMi()) continue;
            Optional<KullaniciBiletEntity> kbOpt = kullaniciBiletRepository.findByBilet(b);
            if (kbOpt.isEmpty() || kbOpt.get().isIptalIstendiMi()) continue;

            // find the SeansKoltukBilet
            Optional<SeansKoltukBiletEntity> skbOpt = seansKoltukBiletRepository.findByBilet(b);
            if (skbOpt.isEmpty()) {
                System.err.println("Uyarı: SeansKoltukBiletEntity bulunamadı for BiletID=" + b.getBiletID());
                continue;
            }
            SeansKoltukBiletEntity skb = skbOpt.get();

            // find the event/session mapping
            EtkinlikSalonSeansEntity ess =
                    etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(skb.getSeans());
            if (ess == null) {
                System.err.println("Uyarı: EtkinlikSalonSeansEntity bulunamadı for SeansID=" +
                        skb.getSeans().getSeansID());
                continue;
            }

            // build SeansDto
            SeansEntity seansEnt = ess.getSeans();
            SeansDto seansDto = new SeansDto(
                    seansEnt.getSeansID(),
                    seansEnt.getTarih(),
                    seansEnt.getBitisTarih(),
                    seansEnt.isTarihiGectiMi(),
                    seansEnt.getOlusturulmaTarihi()
            );

            // assemble BiletDto
            biletDtoList.add(new BiletDto(
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
            ));
        }

        return biletDtoList;
    }

    public KullaniciProfiliDto getKullaniciProfili(Long id) {
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(id);
        if (kullanici == null) {
            throw new EntityNotFoundException("Kullanıcı bulunamadı");
        }
        return new KullaniciProfiliDto(
                kullanici.getAdSoyad(),
                kullanici.getKullaniciAdi(),
                kullanici.getEmail(),
                kullanici.getSehir(),
                kullanici.getTelNo()
        );
    }

    public long getUserIdByUsername(String username) {
        return kullaniciRepository.getUserIdByKullaniciAdi(username);
    }

    @Transactional
    public boolean kullaniciSehirDuzenle(Long userId, SehirDto sehirDto) {
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(userId);
        kullanici.setSehir(sehirRepository.findByPlakaKodu(sehirDto.getPlakaKodu()));
        kullaniciRepository.save(kullanici);
        return true;
    }
}

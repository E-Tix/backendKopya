package com.example.demo.Service;

import com.example.demo.Dto.Request.BiletAlDto;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BiletAlService {

    BiletRepository biletRepository;
    KullaniciRepository kullaniciRepository;
    KullaniciBiletRepository kullaniciBiletRepository;
    SeansRepository seansRepository;
    KoltukRepository koltukRepository;
    SeansKoltukBiletRepository seansKoltukBiletRepository;
    SatinAlService satinAlService;

    @Autowired
    public BiletAlService(SatinAlService satinAlService,KoltukRepository koltukRepository,SeansRepository seansRepository,BiletRepository biletRepository, KullaniciRepository kullaniciRepository, KullaniciBiletRepository kullaniciBiletRepository, SeansKoltukBiletRepository seansKoltukBiletRepository) {
        this.satinAlService=satinAlService;
        this.biletRepository = biletRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.kullaniciBiletRepository = kullaniciBiletRepository;
        this.seansKoltukBiletRepository = seansKoltukBiletRepository;
        this.seansRepository = seansRepository;
        this.koltukRepository = koltukRepository;
    }

    @Transactional
    public boolean biletAl(BiletAlDto dto, Long kullaniciId) {
        // 1) Seans ve Koltuk nesnelerini al
        SeansEntity seans = seansRepository.findById(dto.getSeansId())
                .orElseThrow(() -> new EntityNotFoundException("Seans bulunamadı"));
        KoltukEntity koltuk = koltukRepository.findById(dto.getKoltukId())
                .orElseThrow(() -> new EntityNotFoundException("Koltuk bulunamadı"));

        // 2) Seans-Koltuk tablosundaki kaydı bul
        SeansKoltukBiletEntity skb = seansKoltukBiletRepository
                .findBySeansAndKoltuk(seans, koltuk)
                .orElseThrow(() -> new EntityNotFoundException("Bu seans+koltuk kaydı yok"));

        // zaten dolu mu?
        if (skb.getBilet() != null) {
            throw new IllegalStateException("Bu koltuk zaten satın alınmış");
        }

        // 3) Yeni BiletEntity oluşturup ilişkilendir
        BiletEntity bilet = new BiletEntity(dto.isOdendiMi(), dto.getOdenenMiktar());
        skb.setBilet(bilet);
        skb.setKoltukdurumu(true);
        bilet.setSeansKoltukBilet(skb);

        // 4) Kullanıcı–Bilet ilişkisini oluştur
        KullaniciEntity user = kullaniciRepository.findById(kullaniciId)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı"));
        KullaniciBiletEntity kb = new KullaniciBiletEntity(user, bilet, false);
        bilet.setKullaniciBilet(kb);
        user.getKullaniciBiletEntityList().add(kb);

        // 5) Kaydet: cascade persist ayarınız varsa sadece biletRepo.save yeterli.
        biletRepository.save(bilet);

        // 6) Son adım: dış servisi çağırın
        satinAlService.satinAl();

        return true;
    }

    public boolean biletSil(Long userId, Long biletId)
    {
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciID(userId);
        Optional<BiletEntity> optionalBilet = biletRepository.findByBiletID(biletId);
        if (optionalBilet.isPresent())
        {
            BiletEntity bilet=optionalBilet.get();
            Optional<KullaniciBiletEntity> optKb = kullaniciBiletRepository.findByBilet(bilet);
            KullaniciBiletEntity kullaniciBiletEntity = optKb.get();
            if (kullanici==kullaniciBiletEntity.getKullanici())
            {
                kullaniciBiletEntity.setIptalIstendiMi(true);
                kullaniciBiletRepository.save(kullaniciBiletEntity);
                return true;
            }
            return false;
        }
        return false;
    }

}

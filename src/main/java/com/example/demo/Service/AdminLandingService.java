package com.example.demo.Service;

import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.BiletRepository;
import com.example.demo.Repository.EtkinlikSalonSeansRepository;
import com.example.demo.Repository.KullaniciBiletRepository;
import com.example.demo.Repository.SeansKoltukBiletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminLandingService {
    private final KullaniciBiletRepository kullaniciBiletRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;
    private final BiletRepository biletRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;
    private final MailService mailService;

    public AdminLandingService(
            MailService mailService,
            EtkinlikSalonSeansRepository etkinlikSalonSeansRepository,
            KullaniciBiletRepository kullaniciBiletRepository,
            SeansKoltukBiletRepository seansKoltukBiletRepository,
            BiletRepository biletRepository
    ) {
        this.mailService = mailService;
        this.etkinlikSalonSeansRepository = etkinlikSalonSeansRepository;
        this.kullaniciBiletRepository = kullaniciBiletRepository;
        this.biletRepository = biletRepository;
        this.seansKoltukBiletRepository = seansKoltukBiletRepository;
    }

    public List<SilinecekBiletDto> getSilinecekBiletler() {
        List<SilinecekBiletDto> silinecekBiletDtoList = new ArrayList<>();
        List<KullaniciBiletEntity> kullaniciBiletEntityList =
                kullaniciBiletRepository.findByIptalIstendiMiTrue();

        for (KullaniciBiletEntity kb : kullaniciBiletEntityList) {
            // 1) Bilet’e ait SeansKoltukBiletEntity’yi al
            Optional<SeansKoltukBiletEntity> optSkb =
                    seansKoltukBiletRepository.findByBilet(kb.getBilet());
            if (optSkb.isEmpty()) {
                System.err.println("Uyarı: SeansKoltukBiletEntity bulunamadı for BiletID="
                        + kb.getBilet().getBiletID());
                continue;
            }
            SeansKoltukBiletEntity skb = optSkb.get();

            // 2) Seans’a ait EtkinlikSalonSeansEntity’yi al
            SeansEntity seans = skb.getSeans();
            if (seans == null) {
                System.err.println("Uyarı: SeansEntity null! SeansKoltukBiletID="
                        + skb.getSeansKoltukBiletID());
                continue;
            }

            EtkinlikSalonSeansEntity ess =
                    etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(seans);
            if (ess == null) {
                System.err.println("Uyarı: EtkinlikSalonSeansEntity bulunamadı for SeansID="
                        + seans.getSeansID());
                continue;
            }

            // 3) DTO oluştur
            silinecekBiletDtoList.add(new SilinecekBiletDto(
                    new KullaniciDtoForSilinecekBiletDto(
                            kb.getKullanici().getKullaniciAdi(),
                            kb.getKullanici().getEmail()
                    ),
                    new BiletDto(
                            kb.getBilet().getBiletID(),
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
                            seans,
                            kb.getBilet().getOdenenMiktar()
                    )
            ));
        }

        return silinecekBiletDtoList;
    }

    @Transactional
    public boolean biletSil(Long biletId) {
        Optional<BiletEntity> optionalBilet = biletRepository.findByBiletID(biletId);
        if (optionalBilet.isEmpty()) {
            return false;
        }

        BiletEntity bilet = optionalBilet.get();
        // işaretle: iptal edildi
        bilet.setIptalEdildiMi(true);

        // koltuğu serbest bırak
        Optional<SeansKoltukBiletEntity> optSkb =
                seansKoltukBiletRepository.findByBilet(bilet);
        optSkb.ifPresent(skb -> {
            skb.setKoltukdurumu(false);
            skb.setBilet(null);
            seansKoltukBiletRepository.save(skb);
        });

        biletRepository.save(bilet);

        // mail gönder
        kullaniciBiletRepository.findByBilet(bilet)
                .ifPresent(kb -> mailService.biletIptaliSendMail(
                        kb.getKullanici().getEmail(),
                        biletId
                ));

        return true;
    }
}

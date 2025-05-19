package com.example.demo.Service;

import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.BiletRepository;
import com.example.demo.Repository.EtkinlikSalonSeansRepository;
import com.example.demo.Repository.KullaniciBiletRepository;
import com.example.demo.Repository.SeansKoltukBiletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public AdminLandingService(MailService mailService,
                               EtkinlikSalonSeansRepository etkinlikSalonSeansRepository,
                               KullaniciBiletRepository kullaniciBiletRepository,
                               SeansKoltukBiletRepository seansKoltukBiletRepository,
                               BiletRepository biletRepository) {
        this.mailService = mailService;
        this.etkinlikSalonSeansRepository = etkinlikSalonSeansRepository;
        this.kullaniciBiletRepository = kullaniciBiletRepository;
        this.seansKoltukBiletRepository = seansKoltukBiletRepository;
        this.biletRepository = biletRepository;
    }

    public List<SilinecekBiletDto> getSilinecekBiletler() {
        List<SilinecekBiletDto> dtos = new ArrayList<>();
        List<KullaniciBiletEntity> iptalIsteyenler =
                kullaniciBiletRepository.findByIptalIstendiMiTrue();

        for (KullaniciBiletEntity kb : iptalIsteyenler) {
            BiletEntity bilet = kb.getBilet();

            // 1) SeansKoltukBiletEntity
            Optional<SeansKoltukBiletEntity> skbOpt =
                    seansKoltukBiletRepository.findByBilet(bilet);
            if (skbOpt.isEmpty()) {
                System.err.println("Uyarı: SeansKoltukBilet bulunamadı for BiletID=" + bilet.getBiletID());
                continue;
            }
            SeansKoltukBiletEntity skb = skbOpt.get();

            // 2) EtkinlikSalonSeansEntity
            EtkinlikSalonSeansEntity ess =
                    etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(skb.getSeans());
            if (ess == null) {
                System.err.println("Uyarı: EtkinlikSalonSeans bulunamadı for SeansID=" +
                        skb.getSeans().getSeansID());
                continue;
            }

            // 3) DTO’ya dök
            SilinecekBiletDto dto = new SilinecekBiletDto(
                    new KullaniciDtoForSilinecekBiletDto(
                            kb.getKullanici().getKullaniciAdi(),
                            kb.getKullanici().getEmail()
                    ),
                    new BiletDto(
                            bilet.getBiletID(),
                            bilet.getOdenenMiktar(),
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
                            ess.getSeans()
                    )
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public boolean biletSil(Long biletId) {
        // 1) KullaniciBiletEntity
        Optional<KullaniciBiletEntity> kbOpt =
                Optional.ofNullable(kullaniciBiletRepository.findByBilet_BiletID(biletId));
        if (kbOpt.isEmpty()) {
            return false;
        }
        KullaniciBiletEntity kb = kbOpt.get();
        BiletEntity bilet = kb.getBilet();

        // 2) SeansKoltukBilet durumu güncelle
        seansKoltukBiletRepository.findByBilet(bilet).ifPresent(skb -> {
            skb.setKoltukdurumu(false);
            skb.setBilet(null);
            seansKoltukBiletRepository.save(skb);
        });

        // 3) Bileti işaretle, mail gönder
        bilet.setIptalEdildiMi(true);
        biletRepository.save(bilet);
        mailService.biletIptaliSendMail(kb.getKullanici().getEmail(), biletId);

        // 4) KullaniciBilet kaydını sil
        kullaniciBiletRepository.delete(kb);
        return true;
    }
}

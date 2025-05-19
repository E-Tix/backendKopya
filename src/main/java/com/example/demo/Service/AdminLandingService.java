package com.example.demo.Service;

import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.BiletRepository;
import com.example.demo.Repository.EtkinlikSalonSeansRepository;
import com.example.demo.Repository.KullaniciBiletRepository;
import com.example.demo.Repository.SeansKoltukBiletRepository;
import jakarta.persistence.Transient;
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

    public AdminLandingService(MailService mailService,EtkinlikSalonSeansRepository etkinlikSalonSeansRepository, KullaniciBiletRepository kullaniciBiletRepository, SeansKoltukBiletRepository seansKoltukBiletRepository, BiletRepository biletRepository)
    {
        this.mailService=mailService;
        this.etkinlikSalonSeansRepository=etkinlikSalonSeansRepository;
        this.kullaniciBiletRepository=kullaniciBiletRepository;
        this.biletRepository=biletRepository;
        this.seansKoltukBiletRepository=seansKoltukBiletRepository;
    }


    public List<SilinecekBiletDto> getSilinecekBiletler() {
        List<SilinecekBiletDto> silinecekBiletDtoList = new ArrayList<>();
        List<KullaniciBiletEntity> kullaniciBiletEntityList =
                kullaniciBiletRepository.findByIptalIstendiMiTrue();

<<<<<<< Updated upstream

        SeansKoltukBiletEntity seansKoltukBilet;
        EtkinlikSalonSeansEntity etkinlikSalonSeans;

        for(KullaniciBiletEntity kb:kullaniciBiletEntityList)
        {
            if (!kb.getBilet().isIptalEdildiMi())
            {
                seansKoltukBilet=seansKoltukBiletRepository.findSeansKoltukBiletEntityByBilet(kb.getBilet());
                etkinlikSalonSeans=etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(seansKoltukBilet.getSeans());

                silinecekBiletDtoList.add(new SilinecekBiletDto(
                        new KullaniciDtoForSilinecekBiletDto(kb.getKullanici().getKullaniciAdi(),kb.getKullanici().getEmail()),
                        new BiletDto(
                                kb.getBilet().getBiletID(),
                                kb.getBilet().getOdenenMiktar(),
                                seansKoltukBilet.getKoltuk().getKoltukNumarasi(),
                                etkinlikSalonSeans.getEtkinlik().getEtkinlikAdi(),
                                new SehirDto(etkinlikSalonSeans.getEtkinlik().getSehir().getPlakaKodu(),etkinlikSalonSeans.getEtkinlik().getSehir().getSehirAdi()),
                                new SalonDto(etkinlikSalonSeans.getSalon().getSalonID(),etkinlikSalonSeans.getSalon().getSalonAdi(),etkinlikSalonSeans.getSalon().getAdres()),
                                etkinlikSalonSeans.getSeans()
                        )
                ));
            }
            }
=======
        for (KullaniciBiletEntity kb : kullaniciBiletEntityList) {
            // 1) Bilet’e ait SeansKoltukBiletEntity’yi al
            Optional<SeansKoltukBiletEntity> opt =
                    seansKoltukBiletRepository.findByBilet(kb.getBilet());
            if (opt.isEmpty()) {
                // log uyarı
                continue;
            }
            SeansKoltukBiletEntity seansKoltukBilet = opt.get();
            if (seansKoltukBilet == null) {
                // log basıp bu kaydı atla
                System.err.println("Uyarı: SeansKoltukBiletEntity bulunamadı for BiletID="
                        + kb.getBilet().getBiletID());
                continue;
            }

            // 2) Seans bilgisini al ve kontrol et
            SeansEntity seans = seansKoltukBilet.getSeans();
            if (seans == null) {
                System.err.println("Uyarı: SeansEntity null! SeansKoltukBiletID="
                        + seansKoltukBilet.getSeansKoltukBiletID());
                continue;
            }

            // 3) Seans’a ait EtkinlikSalonSeansEntity’yi al
            EtkinlikSalonSeansEntity etkinlikSalonSeans =
                    etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntityBySeans(seans);
            if (etkinlikSalonSeans == null) {
                System.err.println("Uyarı: EtkinlikSalonSeansEntity bulunamadı for SeansID="
                        + seans.getSeansID());
                continue;
            }

            // 4) Tüm gerekli veriler hazır, DTO’yu oluştur
            silinecekBiletDtoList.add(new SilinecekBiletDto(
                    new KullaniciDtoForSilinecekBiletDto(
                            kb.getKullanici().getKullaniciAdi(),
                            kb.getKullanici().getEmail()
                    ),
                    new BiletDto(
                                                kb.getBilet().getBiletID(),
                            seansKoltukBilet.getKoltuk().getKoltukNumarasi(),
                            etkinlikSalonSeans.getEtkinlik().getEtkinlikAdi(),
                            new SehirDto(
                                                        etkinlikSalonSeans.getEtkinlik().getSehir().getPlakaKodu(),
                                                        etkinlikSalonSeans.getEtkinlik().getSehir().getSehirAdi()
                                                ),
                            new SalonDto(
                                                        etkinlikSalonSeans.getSalon().getSalonID(),
                                                        etkinlikSalonSeans.getSalon().getSalonAdi(),
                                                        etkinlikSalonSeans.getSalon().getAdres()
                                                ),
                            seans,
                            kb.getBilet().getOdenenMiktar()  // SeansEntity doğrudan DTO’ya geçiyor
                                        )
            ));
        }
>>>>>>> Stashed changes

        return silinecekBiletDtoList;
    }

<<<<<<< Updated upstream
    public boolean biletSil(Long biletId) {
        Optional<BiletEntity> optionalBilet = biletRepository.findByBiletID(biletId);

        if (optionalBilet.isPresent()) {
            BiletEntity bilet = optionalBilet.get();

            bilet.setIptalEdildiMi(true);
            SeansKoltukBiletEntity seansKoltukBilet=seansKoltukBiletRepository.findSeansKoltukBiletEntityByBilet(bilet);
            seansKoltukBilet.setKoltukdurumu(false);
            seansKoltukBilet.setBilet(null);
            seansKoltukBiletRepository.save(seansKoltukBilet);
            biletRepository.save(bilet);

            KullaniciBiletEntity kullaniciBiletEntity=kullaniciBiletRepository.findByBilet(bilet);

            mailService.biletIptaliSendMail(kullaniciBiletEntity.getKullanici().getEmail(), biletId);

            return true;
        }

        return false;
=======

    @Transactional
    public boolean biletSil(Long biletId) {
        if (!biletRepository.existsById(biletId)) {
            return false;
        }
        biletRepository.deleteById(biletId);
        return true;
>>>>>>> Stashed changes
    }




}

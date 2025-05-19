package com.example.demo.Service;

import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import net.bytebuddy.matcher.FilterableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LandingService {

    private final EtkinlikRepository etkinlikRepository;
    private final EtkinlikTurRepository etkinlikTurRepository;
    private final SehirRepository sehirRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;
    private final SinemaRepository sinemaRepository;
    private final KoltukRepository koltukRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;

    @Autowired
    public LandingService(SinemaRepository sinemaRepository,SeansKoltukBiletRepository seansKoltukBiletRepository,EtkinlikRepository etkinlikRepository, EtkinlikSalonSeansRepository etkinlikSalonSeansRepository, SehirRepository sehirRepository, EtkinlikTurRepository etkinlikTurRepository, KoltukRepository koltukRepository) {
        this.sinemaRepository = sinemaRepository;
        this.seansKoltukBiletRepository=seansKoltukBiletRepository;
        this.etkinlikSalonSeansRepository = etkinlikSalonSeansRepository;
        this.sehirRepository = sehirRepository;
        this.etkinlikTurRepository = etkinlikTurRepository;
        this.etkinlikRepository = etkinlikRepository;
        this.koltukRepository = koltukRepository;
    }

    // Sade şekilde Etkinlikleri getir
//    public List<EtkinlikEntity> getEtkinlikler(String etkinlikTurAdi, String sehirAdi) {
//        if (sehirAdi == null && etkinlikTurAdi == null) {
//            return etkinlikRepository.findAll(); // Tüm etkinlikleri getir
//        } else if (sehirAdi == null) {
//            EtkinlikTurEntity etkinlikTur = etkinlikTurRepository.findByEtkinlikTurAdi(etkinlikTurAdi);
//            return etkinlikRepository.findByEtkinlikTur(etkinlikTur); // Etkinlik türüne göre filtrele
//        } else {
//            EtkinlikTurEntity etkinlikTur = etkinlikTurRepository.findByEtkinlikTurAdi(etkinlikTurAdi);
//            SehirEntity sehir = sehirRepository.findBySehirAdi(sehirAdi);
//            return etkinlikRepository.findBySehirAndEtkinlikTur(sehir, etkinlikTur); // Hem şehir hem tür ile filtrele
//        }
//    }

    //Pagination ile Etkinlikleri Getir
    public Page<EtkinlikEntity> getEtkinlikler(String etkinlikTurAdi, String sehirAdi, int page,int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("olusturulmaTarihi").descending());

        if(etkinlikTurAdi==null && sehirAdi==null){
            return etkinlikRepository.findAllByTarihiGectiMi(false, pageable);
        } else if (sehirAdi==null) {
            EtkinlikTurEntity etkinlikTur = etkinlikTurRepository.findByEtkinlikTurAdi(etkinlikTurAdi);
            return etkinlikRepository.findByEtkinlikTurAndTarihiGectiMi(etkinlikTur,false, pageable);
        } else if (etkinlikTurAdi==null) {
            SehirEntity sehir = sehirRepository.findBySehirAdi(sehirAdi);
            return etkinlikRepository.findBySehirAndTarihiGectiMi(sehir,false,pageable);
        }else {
            EtkinlikTurEntity etkinlikTur = etkinlikTurRepository.findByEtkinlikTurAdi(etkinlikTurAdi);
            SehirEntity sehir = sehirRepository.findBySehirAdi(sehirAdi);
            return etkinlikRepository.findBySehirAndEtkinlikTurAndTarihiGectiMi(sehir, etkinlikTur,false, pageable);
        }
    }


    // Etkinlik detaylarını getir
    public EtkinlikDetayDto getEtkinlik(Long id) {
        Optional<EtkinlikEntity> etkinlikOptional = etkinlikRepository.findById(id);

        if (etkinlikOptional.isPresent()) {
            EtkinlikEntity etkinlik = etkinlikOptional.get();
            List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntities = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);

            return new EtkinlikDetayDto(
                    etkinlik.getEtkinlikID(),
                    etkinlik.getEtkinlikAdi(),
                    etkinlik.getBiletFiyati(),
                    etkinlik.getYasSiniri(),
                    etkinlik.getEtkinlikTur(),
                    etkinlikSalonSeansEntities,
                    etkinlik.getEtkinlikSuresi(),
                    etkinlik.getEtkinlikAciklamasi(),
                    etkinlik.getOrganizator(),
                    etkinlik.getKapakFotografi(),
                    etkinlik.isTarihiGectiMi()
            );
        } else {
            return null;
        }
    }

    public List<SehirDto> getSehirler(){
        List<SehirEntity> sehirler = sehirRepository.findAll();
        List<SehirDto> sehirlerDto = new ArrayList<>();
        for (SehirEntity sehir:sehirler){
            sehirlerDto.add(new SehirDto(sehir.getPlakaKodu(),sehir.getSehirAdi()));
        }
        return sehirlerDto;
    }

    public List<KoltukDurumDto> getKoltukDurumu(Long seansId) {
        // Seansa ait koltukları al
        EtkinlikSalonSeansEntity etkinlikSalonSeansEntity = etkinlikSalonSeansRepository.findById(seansId).orElse(null);

        if (etkinlikSalonSeansEntity == null) {
            return List.of();
        }

        List<KoltukEntity> koltuklar = new ArrayList<>();
        List<SeansKoltukBiletEntity> seansKoltukBiletEntityList = seansKoltukBiletRepository.findSeansKoltukBiletEntitiesBySeans(etkinlikSalonSeansEntity.getSeans());
        List<KoltukDurumDto> koltukDurumDtoList = new ArrayList<>();

        for (SeansKoltukBiletEntity seansKoltukBilet:seansKoltukBiletEntityList)
        {
            koltukDurumDtoList.add(new KoltukDurumDto(seansKoltukBilet.getKoltuk().getKoltukID(),seansKoltukBilet.getKoltuk().getKoltukNumarasi(),seansKoltukBilet.getKoltukdurumu()));
        }

        return koltukDurumDtoList;
    }

    public SinemaDetayDto getSinema(Long eventId)
    {
        EtkinlikEntity etkinlik = etkinlikRepository.findByEtkinlikID(eventId);
        SinemaEntity sinema = sinemaRepository.findByEtkinlik(etkinlik);
        List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntities = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);

        return new SinemaDetayDto(sinema.getSinemaID(), new EtkinlikDetayDto(etkinlik.getEtkinlikID(), etkinlik.getEtkinlikAdi(), etkinlik.getBiletFiyati(),etkinlik.getYasSiniri(),etkinlik.getEtkinlikTur(),etkinlikSalonSeansEntities,etkinlik.getEtkinlikSuresi(), etkinlik.getEtkinlikAciklamasi(), etkinlik.getOrganizator(),etkinlik.getKapakFotografi(), etkinlik.isTarihiGectiMi()), sinema.getFragmanLinki(), sinema.getImdbPuani());
    }

    public List<AramaDto> etkinlikAra(String arananEtkinlikAdi)
    {
        List<EtkinlikEntity> etkinlikEntityList = etkinlikRepository.etkinlikAra(arananEtkinlikAdi);
        List<AramaDto> aramaDtoList = new ArrayList<>();

        if (!etkinlikEntityList.isEmpty())
        {
            for (EtkinlikEntity e:etkinlikEntityList)
            {
                aramaDtoList.add(new AramaDto(e.getEtkinlikID(),e.getEtkinlikAdi(),e.getEtkinlikTur().getEtkinlikTurAdi(),e.getSehir().getSehirAdi(),e.getKapakFotografi()));
            }
        }
        return aramaDtoList;
    }

}

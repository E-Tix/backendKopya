package com.example.demo.Service;

import com.example.demo.Dto.Request.EtkinlikEkleDto;
import com.example.demo.Dto.Request.EtkinlikGuncelleDto;
import com.example.demo.Dto.Request.SeansDuzenleDto;
import com.example.demo.Dto.Request.SeansEkleDto;
import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizatorLandingService {

    private final OrganizatorRepository organizatorRepository;
    private final EtkinlikRepository etkinlikRepository;
    private final SeansRepository seansRepository;
    private final SalonRepository salonRepository;
    private final EtkinlikSalonSeansRepository etkinlikSalonSeansRepository;
    private final SehirRepository sehirRepository;
    private final KoltukRepository koltukRepository;
    private final SeansKoltukBiletRepository seansKoltukBiletRepository;
    private final SinemaRepository sinemaRepository;
    private final KullaniciBiletRepository kullaniciBiletRepository;
    private final BiletRepository biletRepository;
    private final EtkinlikService etkinlikService;

    @Autowired
    public OrganizatorLandingService(BiletRepository biletRepository, KullaniciBiletRepository kullaniciBiletRepository, SeansKoltukBiletRepository seansKoltukBiletRepository,KoltukRepository koltukRepository,OrganizatorRepository organizatorRepository, EtkinlikRepository etkinlikRepository, SeansRepository seansRepository, SalonRepository salonRepository, EtkinlikSalonSeansRepository etkinlikSalonSeansRepository, SehirRepository sehirRepository, SinemaRepository sinemaRepository, EtkinlikService etkinlikService) {
        this.kullaniciBiletRepository=kullaniciBiletRepository;
        this.biletRepository=biletRepository;
        this.seansKoltukBiletRepository=seansKoltukBiletRepository;
        this.koltukRepository=koltukRepository;
        this.organizatorRepository = organizatorRepository;
        this.etkinlikRepository = etkinlikRepository;
        this.seansRepository = seansRepository;
        this.salonRepository = salonRepository;
        this.etkinlikSalonSeansRepository = etkinlikSalonSeansRepository;
        this.sehirRepository = sehirRepository;
        this.sinemaRepository = sinemaRepository;
        this.etkinlikService = etkinlikService;
    }


    public EtkinlikEntity etkinlikEkle(EtkinlikEkleDto etkinlikEkleDto, Long id){
        System.out.println("Etkinlik ekle metoduna geldin"); //Hata tespit amaçlı println fonksiyonları ekledim
        OrganizatorEntity organizator=organizatorRepository.findByOrganizatorID(id);

        if (organizator==null){
            throw new EntityNotFoundException("Organizatör bulunamadı: ID=" + id);
        }

        EtkinlikEntity etkinlik=new EtkinlikEntity(
                organizator,
                etkinlikEkleDto.getEtkinlikTur(),
                etkinlikEkleDto.getSehir(),
                etkinlikEkleDto.getEtkinlikAdi(),
                etkinlikEkleDto.getKapakFotografi(),//açıklama ile fotoğrafın yerini değiştirdim
                etkinlikEkleDto.getEtkinlikAciklamasi(),
                etkinlikEkleDto.getYasSiniri(),
                etkinlikEkleDto.getEtkinlikSuresi(),
                etkinlikEkleDto.getBiletFiyati()
        );

        etkinlik=etkinlikRepository.save(etkinlik);
        System.out.println("Etkinlik kaydedildi");
        SalonEntity salon=etkinlikEkleDto.getSalon();
        System.out.println("Salon alındı");
        SeansEntity seans;
        System.out.println("Seans alındı");

        List<SeansEkleDto> seansEkleDtoList = etkinlikEkleDto.getSeansEkleDtoList();

        for (SeansEkleDto seansEkleDto:seansEkleDtoList)
        {
            seans=seansRepository.save(new SeansEntity(seansEkleDto.getTarih()));
            etkinlikSalonSeansRepository.save(new EtkinlikSalonSeansEntity(etkinlik,salon,seans));
        }

        List<KoltukEntity> koltukEntityList=koltukRepository.findBySalon(salon);
        List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntityList = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);
        List<SeansEntity> seansEntityList = new ArrayList<>();

        for (EtkinlikSalonSeansEntity etkinlikSalonSeans:etkinlikSalonSeansEntityList)
        {
            seansEntityList.add(etkinlikSalonSeans.getSeans());
        }

        for (SeansEntity s:seansEntityList)
        {
            for (KoltukEntity k:koltukEntityList)
            {
                seansKoltukBiletRepository.save(new SeansKoltukBiletEntity(s,k,false));
            }
        }
        etkinlikService.scheduleTarihiGecti(etkinlik.getEtkinlikID());

        return etkinlik;
    }

    public EtkinlikGuncelleDto getEtkinlikGuncelleDto(Long orgId,Long eventId){

        OrganizatorEntity organizator = organizatorRepository.findByOrganizatorID(orgId);
        EtkinlikEntity etkinlik = etkinlikRepository.findByEtkinlikID(eventId);
        List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntity = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);
        SalonEntity salon = etkinlikSalonSeansEntity.getFirst().getSalon();
        List<SeansDuzenleDto> seansDuzenleDtoList = new ArrayList<>();

        while (!etkinlikSalonSeansEntity.isEmpty())
        {
            seansDuzenleDtoList.add(new SeansDuzenleDto(etkinlikSalonSeansEntity.getLast().getSeans().getSeansID(),etkinlikSalonSeansEntity.getLast().getSeans().getTarih()));
            etkinlikSalonSeansEntity.removeLast();
        }

        if (organizator!=null){
            return new EtkinlikGuncelleDto(etkinlik.getEtkinlikID(),salon,seansDuzenleDtoList,etkinlik.getEtkinlikTur(),etkinlik.getSehir(), etkinlik.getEtkinlikAdi(), etkinlik.getKapakFotografi(), etkinlik.getEtkinlikAciklamasi(), etkinlik.getYasSiniri(),etkinlik.getEtkinlikSuresi(),etkinlik.getBiletFiyati());
        }else {
            throw new EntityNotFoundException("organizator bulunamadı");
        }
    }

    public EtkinlikEntity etkinlikGuncelle(Long orgId,EtkinlikGuncelleDto etkinlikGuncelleDto){
        OrganizatorEntity organizator=organizatorRepository.findByOrganizatorID(orgId);
        EtkinlikEntity etkinlik=etkinlikRepository.findByEtkinlikID(etkinlikGuncelleDto.getEtkinlikId());

        if (!etkinlik.getOrganizator().equals(organizator))
        {
            //burada hata dönmesi gerekiyor.
            return new EtkinlikEntity();
        }

        List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntityList = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);
        SalonEntity salon=etkinlikSalonSeansEntityList.getFirst().getSalon();

        while (!etkinlikGuncelleDto.getSeansDuzenleDtoList().isEmpty())
        {
            SeansEntity seans=seansRepository.findBySeansID(etkinlikGuncelleDto.getSeansDuzenleDtoList().getLast().getSeansId());
            seans.setTarih(etkinlikGuncelleDto.getSeansDuzenleDtoList().getLast().getTarih());
            seansRepository.save(seans);
            etkinlikGuncelleDto.getSeansDuzenleDtoList().removeLast();
        }

        List<SeansEntity> seansEntityList = new ArrayList<>();

        while (!etkinlikGuncelleDto.getSeansEkleDtoList().isEmpty())
        {
            SeansEntity seans=new SeansEntity(etkinlikGuncelleDto.getSeansEkleDtoList().getLast().getTarih());
            SeansEntity seans1 = seansRepository.save(seans);
            seansEntityList.add(seans1);
            etkinlikSalonSeansRepository.save(new EtkinlikSalonSeansEntity(etkinlik, salon, seans1));
            etkinlikGuncelleDto.getSeansEkleDtoList().removeLast();
        }

        List<KoltukEntity> koltukEntityList = koltukRepository.findBySalon(salon);

        for(SeansEntity s : seansEntityList)
        {
            for (KoltukEntity k: koltukEntityList)
            {
                seansKoltukBiletRepository.save(new SeansKoltukBiletEntity(s,k,false));
            }
        }
        etkinlik.setBiletFiyati(etkinlikGuncelleDto.getBiletFiyati());
        etkinlik.setEtkinlikAdi(etkinlikGuncelleDto.getEtkinlikAdi());
        etkinlik.setEtkinlikTur(etkinlikGuncelleDto.getEtkinlikTur());
        etkinlik.setSehir(etkinlikGuncelleDto.getSehir());
        etkinlik.setEtkinlikAciklamasi(etkinlikGuncelleDto.getEtkinlikAciklamasi());
        etkinlik.setKapakFotografi(etkinlikGuncelleDto.getKapakFotografi());
        etkinlik.setEtkinlikSuresi(etkinlikGuncelleDto.getEtkinlikSuresi());
        etkinlik.setYasSiniri(etkinlikGuncelleDto.getYasSiniri());

        etkinlikRepository.save(etkinlik);
        etkinlikService.scheduleTarihiGecti(etkinlik.getEtkinlikID());

        return etkinlik;
    }

    @Transactional
    public boolean etkinlikSil(Long eventId){
        EtkinlikEntity etkinlik = etkinlikRepository.findByEtkinlikID(eventId);
        List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntityList = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);

        List<SeansEntity> seansEntityList=new ArrayList<>();


        for (EtkinlikSalonSeansEntity etkinlikSalonSeansEntity: etkinlikSalonSeansEntityList)
        {
            seansEntityList.add(etkinlikSalonSeansEntity.getSeans());
        }

        List<SeansKoltukBiletEntity> seansKoltukBiletEntityList;

        for (SeansEntity s: seansEntityList)
        {
            seansKoltukBiletEntityList=seansKoltukBiletRepository.findSeansKoltukBiletEntitiesBySeans(s);
            for (SeansKoltukBiletEntity seansKoltukBiletEntity: seansKoltukBiletEntityList)
            {
                if (seansKoltukBiletEntity.getBilet()!=null) {
                    KullaniciBiletEntity kullaniciBiletEntity = kullaniciBiletRepository.findByBilet(seansKoltukBiletEntity.getBilet());
                    kullaniciBiletRepository.delete(kullaniciBiletEntity);
                    biletRepository.delete(seansKoltukBiletEntity.getBilet());
                }
            }

        }

        for (EtkinlikSalonSeansEntity etkinlikSalonSeansEntity: etkinlikSalonSeansEntityList)
        {
            etkinlikSalonSeansRepository.delete(etkinlikSalonSeansEntity);
            seansKoltukBiletRepository.deleteAll(seansKoltukBiletRepository.findSeansKoltukBiletEntitiesBySeans(etkinlikSalonSeansEntity.getSeans()));
            seansRepository.delete(etkinlikSalonSeansEntity.getSeans());
        }

        etkinlikRepository.delete(etkinlik);
        return true;
    }

    public EtkinlikForOrgDetayDto getEtkinlik(Long eventId){
        Optional<EtkinlikEntity> etkinlikOptional = etkinlikRepository.findById(eventId);
        if (etkinlikOptional.isPresent()) {
            EtkinlikEntity etkinlik = etkinlikOptional.get();
            List<EtkinlikSalonSeansEntity> etkinlikSalonSeansEntities = etkinlikSalonSeansRepository.findEtkinlikSalonSeansEntitiesByEtkinlik(etkinlik);

            return new EtkinlikForOrgDetayDto(
                    etkinlik.getEtkinlikID(),
                    etkinlik.getEtkinlikAdi(),
                    etkinlik.getBiletFiyati(),
                    etkinlik.getYasSiniri(),
                    etkinlik.getEtkinlikTur(),
                    etkinlikSalonSeansEntities,
                    etkinlik.getEtkinlikSuresi(),
                    etkinlik.getEtkinlikAciklamasi(),
                    etkinlik.getOrganizator()
            );
        }else {
            throw new EntityNotFoundException("etkinlik bulunamadı");
        }
    }

    public Page<EtkinlikForOrgDto> getEtkinlikler(Long orgId, int page, int size){
        //Sıralama Parametresi Oluşturma
        Pageable pageable = PageRequest.of(page, size, Sort.by("olusturulmaTarihi").descending());

        //Organizatorü Elimizde Tutma
        OrganizatorEntity organizator=organizatorRepository.findByOrganizatorID(orgId);

        //Her sayfa başı çıkacak sonuç
        Page<EtkinlikEntity> etkinlikEntities = etkinlikRepository.findByOrganizator(organizator, pageable);


        return etkinlikEntities.map(etkinlikEntity -> {
            String etkinlikTurAdi = (etkinlikEntity.getEtkinlikTur() != null)
                    ? etkinlikEntity.getEtkinlikTur().getEtkinlikTurAdi()
                    : null;
            Long mappedSinemaId = null;

            if (etkinlikTurAdi != null && "Sinema".equalsIgnoreCase(etkinlikTurAdi)) {
                // SinemaRepository'deki findByEtkinlik_EtkinlikID metodunu kullanıyoruz
                Optional<SinemaEntity> sinemaOptional = sinemaRepository.findByEtkinlik_EtkinlikID(etkinlikEntity.getEtkinlikID());
                if (sinemaOptional.isPresent()) {
                    mappedSinemaId = sinemaOptional.get().getSinemaID();
                } else {
                    System.err.println("Uyarı: Etkinlik ID " + etkinlikEntity.getEtkinlikID() +
                            " (türü Sinema) için Sinema tablosunda eşleşen kayıt bulunamadı.");
                }
            }

            // EtkinlikForOrgDto constructor'ını yeni sinemaId parametresini alacak şekilde güncellediğinizi varsayıyorum
            return new EtkinlikForOrgDto(
                    etkinlikEntity.getEtkinlikID(),
                    etkinlikEntity.getEtkinlikAdi(),
                    etkinlikEntity.getOlusturulmaTarihi(),
                    etkinlikEntity.getEtkinlikSuresi(),
                    etkinlikEntity.getYasSiniri(),
                    etkinlikEntity.getKapakFotografi(),
                    etkinlikTurAdi,
                    mappedSinemaId, // <<< YENİ: sinemaId DTO'ya aktarılıyor >>>
                    etkinlikEntity.isTarihiGectiMi()
            );
        });
    }

    public List<SalonDto> getSalonsForSehir(SehirDto sehirDto){
        SehirEntity sehir = sehirRepository.findByPlakaKodu(sehirDto.getPlakaKodu());
        List<SalonEntity> bulunanSalonlar = salonRepository.findSalonEntitiesBySehirEntity(sehir);
        List<SalonDto> salonDtoList=new ArrayList<>();
        while (!bulunanSalonlar.isEmpty())
        {
            salonDtoList.add(new SalonDto(bulunanSalonlar.getLast().getSalonID(), bulunanSalonlar.getLast().getSalonAdi(), bulunanSalonlar.getLast().getAdres()));
            bulunanSalonlar.removeLast();
        }
        return salonDtoList;
    }

    public List<SehirDto> getSehirs()
    {
        List<SehirEntity> sehirEntityList = sehirRepository.findAll();
        List<SehirDto> sehirDtoList = new ArrayList<>();

        while (!sehirEntityList.isEmpty()){
            sehirDtoList.add(new SehirDto(sehirEntityList.getLast().getPlakaKodu(),sehirEntityList.getLast().getSehirAdi()));
            sehirEntityList.removeLast();
        }

        return sehirDtoList;
    }

    //Muhtemelen etkinlik getirmekte sıkıntı yaşadığım için sizin kodlarınızı değiştirmemek için ekledim
    //İşe yaramıyorlarsa silerim
    public List<EtkinlikForOrgDto> getEtkinliklerByOrganizer(Long orgId) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<EtkinlikEntity> etkinlikler = etkinlikRepository.findTop10ByOrganizatorIdOrderByOlusturulmaTarihiDesc(orgId, pageRequest);
        return etkinlikler.stream().map(this::mapToEtkinlikForOrgDto).toList();
    }


    private EtkinlikForOrgDto mapToEtkinlikForOrgDto(EtkinlikEntity e) {
        String etkinlikTuruAdi = (e.getEtkinlikTur() != null) ? e.getEtkinlikTur().getEtkinlikTurAdi() : null;
        Long mappedSinemaId = null;
        if (etkinlikTuruAdi != null && "Sinema".equalsIgnoreCase(etkinlikTuruAdi)) {
            Optional<SinemaEntity> sinemaOptional = sinemaRepository.findByEtkinlik_EtkinlikID(e.getEtkinlikID());
            if (sinemaOptional.isPresent()) {
                mappedSinemaId = sinemaOptional.get().getSinemaID();
            }
        }
        // EtkinlikForOrgDto constructor'ını çağırmak veya setter'ları kullanmak:
        return new EtkinlikForOrgDto(
            e.getEtkinlikID(),
            e.getEtkinlikAdi(),
            e.getOlusturulmaTarihi(),
            e.getEtkinlikSuresi(),
            e.getYasSiniri(),
            e.getKapakFotografi(),
            etkinlikTuruAdi,
            mappedSinemaId,
                e.isTarihiGectiMi()
        );
    }

}

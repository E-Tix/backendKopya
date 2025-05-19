package com.example.demo.Initializer;

import com.example.demo.Entity.KoltukEntity;
import com.example.demo.Entity.SalonEntity;
import com.example.demo.Entity.SehirEntity;
import com.example.demo.Repository.KoltukRepository;
import com.example.demo.Repository.SalonRepository;
import com.example.demo.Repository.SehirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SalonDataInitializer implements CommandLineRunner {

    @Autowired
    private SalonRepository salonRepository;
    @Autowired
    private KoltukRepository koltukRepository;
    @Autowired
    private SehirRepository sehirRepository;

    // Salon adı ve adresini birlikte tutan yardımcı sınıf
    public static class SalonBilgisi {
        String ad;
        String adres;

        public SalonBilgisi(String ad, String adres) {
            this.ad = ad;
            this.adres = adres;
        }
    }

    // Salon bilgileri (ad ve adres)
    //Salon ve şehir problemini çözene kadar salonu bağımsız olarak etkinlik eklemeyi deneme amacıyla oluşturdum
    private static final List<SalonBilgisi> salonlar = Arrays.asList(
            new SalonBilgisi("Salon 1", "İnönü Cd. No:1"),
            new SalonBilgisi("Salon 2", "Atatürk Blv. No:5"),
            new SalonBilgisi("Salon 3", "Cumhuriyet Mh. No:10"),
            new SalonBilgisi("Salon 4", "Kazım Karabekir Cd. No:3"),
            new SalonBilgisi("Salon 5", "Gazi Cd. No:7")
    );

    private static final List<String> sehirler = Arrays.asList(
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın",
            "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı",
            "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep",
            "Giresun", "Gümüşhane", "Hakkâri", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir", "Kars",
            "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa",
            "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun",
            "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van",
            "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın",
            "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"
    );

    public static List<String> getSalonAdlari() {
        return salonlar.stream().map(salon -> salon.ad).toList();
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<SehirEntity> sehirEntityList=new ArrayList<>();
        for (int i = 0; i < sehirler.size(); i++) {
            String sehirAdi = sehirler.get(i);
            if (!sehirRepository.existsBySehirAdi(sehirAdi)) {
                SehirEntity sehir = new SehirEntity();
                sehir.setSehirAdi(sehirAdi);
                sehirEntityList.add(sehirRepository.save(sehir));
            }
        }

        SalonEntity salon1;
        for (SehirEntity sehirEntity:sehirEntityList) {
            for (SalonBilgisi s:salonlar)
            {
                salon1=salonRepository.save(new SalonEntity(sehirEntity,s.ad,s.adres));
                for (int j=1;j<=39;j++)
                {
                    koltukRepository.save(new KoltukEntity(salon1,j));
                }
            }
        }
    }
}

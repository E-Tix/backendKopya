package com.example.demo.Initializer;/*package com.example.demo.Initializer;

import com.example.demo.Entity.SehirEntity;
import com.example.demo.Repository.SehirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class SehirDataInitializer implements CommandLineRunner {

    @Autowired
    private SehirRepository sehirRepository;

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

    public static List<String> getSehirAdlari() {
        return sehirler;
    }

    @Override
    @Transactional
    public void run(String... args) {
        for (int i = 0; i < sehirler.size(); i++) {
            String sehirAdi = sehirler.get(i);
            if (!sehirRepository.existsBySehirAdi(sehirAdi)) {
                SehirEntity sehir = new SehirEntity();
                sehir.setSehirAdi(sehirAdi);
                sehirRepository.save(sehir);
            }
        }
    }
}*/

package com.example.demo.Initializer;

import com.example.demo.Entity.EtkinlikTurEntity;
import com.example.demo.Repository.EtkinlikTurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class EtkinlikTurDataInitializer implements CommandLineRunner {

    @Autowired
    private EtkinlikTurRepository etkinlikTurRepository;

    private static final List<String> turler = Arrays.asList(
            "Sinema", "Bale", "Tiyatro", "Konferans", "Spor"
    );

    public static List<String> getTurAdlari(){
        return turler;
    }

    @Override
    @Transactional
    public void run(String... args) {
        for (int i = 0; i<turler.size(); i++){
            String turAdi = turler.get(i);
            if (!etkinlikTurRepository.existsByEtkinlikTurAdi(turAdi)){
                EtkinlikTurEntity etkinlikTurEntity = new EtkinlikTurEntity();
                etkinlikTurEntity.setEtkinlikTurAdi(turAdi);
                etkinlikTurRepository.save(etkinlikTurEntity);
            }
        }
    }
}

package com.example.demo.Service;

import com.example.demo.Entity.KoltukEntity;
import com.example.demo.Repository.KoltukRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoltukService {

    private final KoltukRepository koltukRepository;

    public KoltukService(KoltukRepository koltukRepository) {
        this.koltukRepository = koltukRepository;
    }

    public List<KoltukEntity> getKoltuklarBySalonId(Long salonId) {
        return koltukRepository.findBySalon_SalonID(salonId);
    }
}


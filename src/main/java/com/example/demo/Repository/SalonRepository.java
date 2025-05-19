package com.example.demo.Repository;


import com.example.demo.Entity.SalonEntity;
import com.example.demo.Entity.SehirEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalonRepository extends JpaRepository<SalonEntity,Long> {

    Optional<SalonEntity> findBySalonID(Long salonId);

    Optional<SalonEntity> findBySalonAdi(String salonAdi);

    List<SalonEntity> findSalonEntitiesBySehirEntity(SehirEntity sehirEntity);

    //initializer'da veri tabanına kayıt sırasında zaten varsa kaydetmemesi için kontrol ekledim
    boolean existsBySalonAdi(String salonAdi);
}

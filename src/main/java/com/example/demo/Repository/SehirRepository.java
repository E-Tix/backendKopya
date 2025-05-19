package com.example.demo.Repository;


import com.example.demo.Entity.SehirEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SehirRepository extends JpaRepository<SehirEntity,Long> {

    SehirEntity findByPlakaKodu(Long plakaKodu);
    SehirEntity findBySehirAdi(String sehirAdi);

    //initializer'da veri tabanına kayıt sırasında zaten varsa kaydetmemesi için kontrol ekledim
    boolean existsBySehirAdi(String sehirAdi);
}

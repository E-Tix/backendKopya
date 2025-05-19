package com.example.demo.Repository;

import com.example.demo.Entity.EtkinlikTurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtkinlikTurRepository extends JpaRepository<EtkinlikTurEntity,Long> {

    Optional<EtkinlikTurEntity> findByEtkinlikTurID(Long etkinlikTurId);
    EtkinlikTurEntity findByEtkinlikTurAdi(String etkinlikTurAdi);
    //initializer'da veri tabanına kayıt sırasında zaten varsa kaydetmemesi için kontrol ekledim
    boolean existsByEtkinlikTurAdi(String etkinlikTurAdi);

}

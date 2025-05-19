package com.example.demo.Repository;

import com.example.demo.Entity.BiletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface BiletRepository extends JpaRepository<BiletEntity, Long> {

    
    Optional<BiletEntity> findByOdendiMi(Boolean odendiMi);

    Optional<BiletEntity> findByBiletID(Long biletID);

    //Belirli bir tarihten sonra oluşturulmuş biletler
    List<BiletEntity> findByOlusturmaZamaniAfter(Timestamp zaman);

    //Belirli bir tarihten önce oluşturulmuş biletler
    List<BiletEntity> findByOlusturmaZamaniBefore(Timestamp zaman);

    //Belli tarihler arası
    List<BiletEntity> findByOlusturmaZamaniBetween(Timestamp baslangic, Timestamp bitis);
}

package com.example.demo.Repository;

import com.example.demo.Entity.SeansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeansRepository extends JpaRepository<SeansEntity, Long> {

    SeansEntity findBySeansID(Long seansID);
    Optional<SeansEntity> findByTarih(Timestamp tarih);
    Optional<SeansEntity> findByTarihiGectiMi(Boolean tarihiGectiMi);

    //Belirli bir tarihten sonra oluşturulmuş seanslar
    List<SeansEntity> findByOlusturulmaTarihiAfter(Timestamp zaman);

    //Belirli bir tarihten önce oluşturulmuş seanslar
    List<SeansEntity> findByOlusturulmaTarihiBefore(Timestamp zaman);

    //Belli tarihler arası
    List<SeansEntity> findByOlusturulmaTarihiBetween(Timestamp baslangic, Timestamp bitis);

}

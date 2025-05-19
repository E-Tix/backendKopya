package com.example.demo.Repository;


import com.example.demo.Entity.BiletEntity;
import com.example.demo.Entity.KullaniciBiletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KullaniciBiletRepository extends JpaRepository<KullaniciBiletEntity, Long> {

    /**
     * Kullanıcının, iptal talebi olmayan biletlerini getirir.
     */

    Optional<KullaniciBiletEntity> findByBilet(BiletEntity biletEntity);

    @Query("""
      SELECT kb.bilet 
      FROM KullaniciBiletEntity kb 
      WHERE kb.kullanici.kullaniciID = :kullaniciId
        AND kb.iptalIstendiMi = false
    """)
    List<BiletEntity> findBiletlerByKullanici(@Param("kullaniciId") Long kullaniciId);

    /**
     * Bir biletId’ye karşılık gelen KullaniciBiletEntity’yi döner.
     * -> Admin iptal akışında önce bu entity’yi bulup iptalIstendiMi flag’ini set ediyoruz.
     */
    Optional<KullaniciBiletEntity> findByBilet_BiletID(Long biletID);

    /**
     * Sadece iptal talebi olan kayıtları listeler.
     * -> AdminLandingService.getSilinecekBiletler() için gerekli.
     */
    List<KullaniciBiletEntity> findByIptalIstendiMiTrue();
}

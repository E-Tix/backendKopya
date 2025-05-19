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

    @Query("""
    SELECT kb.bilet FROM KullaniciBiletEntity kb
    WHERE kb.kullanici.kullaniciID = :kullaniciId
""")
    List<BiletEntity> findBiletlerByKullanici(@Param("kullaniciId") Long kullaniciId);
    //BiletEntity findBiletByKullaniciAndBilet(Long kullaniciId, Long biletId);

    @Query("""
    SELECT kb.bilet, ess.etkinlik, ess.salon, ess.seans
    FROM KullaniciBiletEntity kb
    JOIN EtkinlikSalonSeansEntity ess
    WHERE kb.kullanici.kullaniciID = :kullaniciId
""")
    List<Object[]> findBiletlerWithEtkinlikSalonSeansByKullanici(@Param("kullaniciId") Long kullaniciId);

    KullaniciBiletEntity findByBilet(BiletEntity bilet);
    @Query("SELECT kb FROM KullaniciBiletEntity kb WHERE kb.iptalIstendiMi = true")
    List<KullaniciBiletEntity> findAllIptalIstendi();

    KullaniciBiletEntity findByBilet_BiletID(Long biletId);

    List<KullaniciBiletEntity> findByIptalIstendiMiTrue();

    //KullaniciBiletEntity findKullaniciBiletEntityByKullaniciAndBilet(Long kullaniciId, BiletEntity bilet);
}

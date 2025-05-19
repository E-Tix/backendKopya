package com.example.demo.Repository;

import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Entity.EtkinlikTurEntity;
import com.example.demo.Entity.OrganizatorEntity;
import com.example.demo.Entity.SehirEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface EtkinlikRepository extends JpaRepository<EtkinlikEntity,Long> {

    EtkinlikEntity findByEtkinlikID(Long id);
    @Query("SELECT e.etkinlikID FROM EtkinlikEntity e")
    List<Long> findAllIds();
    List<EtkinlikEntity> findByOrganizator(OrganizatorEntity organizator);

    List<EtkinlikEntity> findBySehirAndEtkinlikTur(SehirEntity sehir,EtkinlikTurEntity etkinlikTur);

    List<EtkinlikEntity> findByEtkinlikTur(EtkinlikTurEntity etkinlikTur);

    Optional<EtkinlikEntity> findByTarihiGectiMi(boolean tarihiGectiMi);

    Optional<EtkinlikEntity> findByYasSiniri(int yas);

    List<EtkinlikEntity> findBySehir(SehirEntity sehir);

    List<EtkinlikEntity> findByOlusturulmaTarihi(Timestamp sure);

    Optional<EtkinlikEntity> findByEtkinlikAdi(String etkinlikAdi);

    //Ana sayfada bütün etkinlikleri göstermek yerine 10 tanesini göstermek için. Henüz denenmedi
    @Query("SELECT e FROM EtkinlikEntity e WHERE e.organizator.organizatorID = :orgId ORDER BY e.olusturulmaTarihi DESC")
    List<EtkinlikEntity> findTop10ByOrganizatorIdOrderByOlusturulmaTarihiDesc(@Param("orgId") Long orgId, Pageable pageable);

    //Pageleme için
    Page<EtkinlikEntity> findAll(Pageable pageable);
    Page<EtkinlikEntity> findAllByTarihiGectiMi(Boolean tarihiGectiMi, Pageable pageable);
    Page<EtkinlikEntity> findByEtkinlikTur(EtkinlikTurEntity etkinlikTur, Pageable pageable);
    Page<EtkinlikEntity> findByEtkinlikTurAndTarihiGectiMi(EtkinlikTurEntity etkinlikTur, Boolean tarihiGectiMi, Pageable pageable);
    Page<EtkinlikEntity> findBySehir(SehirEntity sehir, Pageable pageable);
    Page<EtkinlikEntity> findBySehirAndTarihiGectiMi(SehirEntity sehir, Boolean tarihiGectiMi, Pageable pageable);
    Page<EtkinlikEntity> findBySehirAndEtkinlikTur(SehirEntity sehir, EtkinlikTurEntity etkinlikTur, Pageable pageable);
    Page<EtkinlikEntity> findBySehirAndEtkinlikTurAndTarihiGectiMi(SehirEntity sehir, EtkinlikTurEntity etkinlikTur, Boolean tarihiGectiMi, Pageable pageable);
    Page<EtkinlikEntity> findByOrganizator(OrganizatorEntity organizator, Pageable pageable);
    Page<EtkinlikEntity> findByOrganizatorAndTarihiGectiMi(OrganizatorEntity organizator, Boolean tarihiGectiMi, Pageable pageable);

    @Query("SELECT e FROM EtkinlikEntity e WHERE LOWER(e.etkinlikAdi) LIKE LOWER(CONCAT('%',:query,'%'))")
    List<EtkinlikEntity> etkinlikAra(@Param("query") String query);

}

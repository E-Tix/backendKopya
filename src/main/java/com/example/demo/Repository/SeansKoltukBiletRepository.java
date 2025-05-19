package com.example.demo.Repository;


import com.example.demo.Entity.BiletEntity;
import com.example.demo.Entity.KoltukEntity;
import com.example.demo.Entity.SeansEntity;
import com.example.demo.Entity.SeansKoltukBiletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeansKoltukBiletRepository extends JpaRepository<SeansKoltukBiletEntity, Long> {
    Optional<SeansKoltukBiletEntity> findBySeansAndKoltuk(SeansEntity seans, KoltukEntity koltuk);

    @Query("""
        SELECT skb.bilet FROM SeansKoltukBiletEntity skb
        WHERE skb.seans.seansID = :seansId
    """)
    List<BiletEntity> findBiletlerBySeans(@Param("seansId") Long seansId);
    Optional<SeansKoltukBiletEntity> findByBilet(BiletEntity bilet);
    Optional<SeansKoltukBiletEntity> findByBilet_BiletID(Long biletID);

    @Query("""
        SELECT DISTINCT s FROM SeansEntity s
        JOIN SalonEntity sa
        WHERE sa.salonID = :salonId
    """)
    List<SeansEntity> findSeanslarBySalon(@Param("salonId") Long salonId);

    /*@Query("""
        SELECT skb.koltuk FROM SeansKoltukBiletEntity skb
        WHERE skb.seans.seansID = :seandsId
    """)
    List<SeansEntity> findKoltukBySeanslar(@Param("seandsId") Long seansId);*/


    @Query("""
        SELECT skb.koltuk FROM SeansKoltukBiletEntity skb
        WHERE skb.seans.seansID = :seandsId
    """)
    List<SeansEntity> findKoltuklarStatusBySeanslar(@Param("seandsId") Long seansId);

    List<SeansKoltukBiletEntity> findSeansKoltukBiletEntitiesBySeans(SeansEntity seans);

    SeansKoltukBiletEntity findSeansKoltukBiletEntityBySeansAndKoltuk(SeansEntity seans, KoltukEntity koltuk);
    SeansKoltukBiletEntity findSeansKoltukBiletEntityByBilet(BiletEntity bilet);
}

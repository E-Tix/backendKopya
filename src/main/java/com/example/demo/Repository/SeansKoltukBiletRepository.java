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

@Repository
public interface SeansKoltukBiletRepository extends JpaRepository<SeansKoltukBiletEntity, Long> {

    @Query("""
        SELECT skb.bilet FROM SeansKoltukBiletEntity skb
        WHERE skb.seans.seansID = :seansId
    """)
    List<BiletEntity> findBiletlerBySeans(@Param("seansId") Long seansId);

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

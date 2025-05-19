package com.example.demo.Repository;

import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Entity.SinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SinemaRepository extends JpaRepository<SinemaEntity,Long> {

    SinemaEntity findByEtkinlik(EtkinlikEntity etkinlik);
    Optional<SinemaEntity> findByImdbPuani(float IMDBPuani);
    SinemaEntity findBySinemaID(Long sinemaID);
    Optional<SinemaEntity> findByEtkinlik_EtkinlikID(Long etkinlikId);
}

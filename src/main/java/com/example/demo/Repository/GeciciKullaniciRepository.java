package com.example.demo.Repository;

import com.example.demo.Entity.GeciciKullaniciEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface GeciciKullaniciRepository extends JpaRepository<GeciciKullaniciEntity, Long> {

    Optional<GeciciKullaniciEntity> findByGeciciKullaniciID(Long geciciKullaniciID);

}

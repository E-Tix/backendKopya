package com.example.demo.Repository;

import com.example.demo.Entity.OrganizatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OrganizatorRepository extends JpaRepository<OrganizatorEntity,Long> {
    OrganizatorEntity findByEmailAndSifre(String email,String sifre);
    OrganizatorEntity findByOrganizatorID(Long organizatorID);
    Optional<OrganizatorEntity> findByAdSoyad(String adSoyad);
    Optional<OrganizatorEntity> findByEmail(String email);//tek bir org döndürmesi lazım
    Optional<OrganizatorEntity> findBySifre(String sifre);
    Long findOrganizatorIDByEmail(String username);

    //Organizatör giriş yaptığında tokenda mail bilgisi geliyor. Mail ile organizatörün id'sini almak için ekledim ama henüz denemedim
    @Query("SELECT o.organizatorID FROM OrganizatorEntity o WHERE o.email = :email")
    Long getOrganizatorIdByEmail(@Param("email") String email);
}

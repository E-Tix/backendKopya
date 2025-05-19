package com.example.demo.Repository;

import com.example.demo.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
    Optional<AdminEntity> findByEmail(String email);
    AdminEntity findByAdminID(Long adminId);
    @Query("SELECT a.email FROM AdminEntity a WHERE a.email = :email")
    Optional<String> findAdminIdWithEmail(@Param("email") String email);
}

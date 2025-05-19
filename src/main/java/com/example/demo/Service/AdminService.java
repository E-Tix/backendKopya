package com.example.demo.Service;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Response.AdminProfiliDto;
import com.example.demo.Entity.AdminEntity;
import com.example.demo.Repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminProfiliDto getAdminProfili(Long adminId) {
        AdminEntity admin = adminRepository.findByAdminID(adminId);
        if (admin == null) {
            throw new EntityNotFoundException("Admin bulunamadı: ID " + adminId);
        }
        return new AdminProfiliDto(admin.getEmail()); // Sadece email ile DTO oluşturuluyor
    }

    public boolean sifreDegistir(Long adminId, ChangePasswordDto changePasswordDto) {
        AdminEntity admin = adminRepository.findByAdminID(adminId);
        if (admin == null) {
            System.out.println("Admin bulunamadı: ID " + adminId);
            return false;
        }

        // Mevcut şifreyi kontrol et
        if (!passwordEncoder.matches(changePasswordDto.getEskiSifre(), admin.getSifre())) {
            System.out.println("Eski şifre hatalı.");
            return false;
        }

        // Yeni şifrelerin eşleşip eşleşmediğini kontrol et
        if (!changePasswordDto.getYeniSifre().equals(changePasswordDto.getYeniSifreTekrar())) {
            System.out.println("Yeni şifreler uyuşmuyor.");
            return false;
        }

        // Yeni şifreyi hash'leyip kaydet
        admin.setSifre(passwordEncoder.encode(changePasswordDto.getYeniSifre()));
        adminRepository.save(admin);
        System.out.println("Admin şifresi başarıyla güncellendi.");
        return true;
    }

    // AdminController'daki getCurrentAdminId için yardımcı metot
    public Long getAdminIdByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Admin bulunamadı: Email " + email))
                .getAdminID();
    }
}

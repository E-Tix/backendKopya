package com.example.demo.Initializer;

import com.example.demo.Entity.AdminEntity;
import com.example.demo.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataInitializer implements CommandLineRunner{
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Varsayılan admin e-postası ve şifresi
        String adminEmail = "admin@admin.com";
        String adminPassword = "adminPassword123";

        // Eğer bu e-posta ile bir admin yoksa oluştur
        if (adminRepository.findByEmail(adminEmail).isEmpty()) {
            AdminEntity defaultAdmin = new AdminEntity();
            defaultAdmin.setEmail(adminEmail);
            defaultAdmin.setSifre(passwordEncoder.encode(adminPassword));

            adminRepository.save(defaultAdmin);
            System.out.println("Varsayılan admin kullanıcısı oluşturuldu: " + adminEmail);
        } else {
            System.out.println("Varsayılan admin kullanıcısı zaten mevcut: " + adminEmail);
        }
    }
}
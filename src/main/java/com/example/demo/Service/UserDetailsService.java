package com.example.demo.Service;

import com.example.demo.Entity.AdminEntity;
import com.example.demo.Entity.KullaniciEntity;
import com.example.demo.Entity.OrganizatorEntity;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Repository.KullaniciRepository;
import com.example.demo.Repository.OrganizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    @Autowired
    private OrganizatorRepository organizatorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kullanıcıyı ara
        KullaniciEntity user = kullaniciRepository.findByKullaniciAdi(username).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getKullaniciAdi(),
                    user.getSifre(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        // Organizatorü ara
        OrganizatorEntity organizator = organizatorRepository.findByEmail(username).orElse(null);
        if (organizator != null) {
            return new org.springframework.security.core.userdetails.User(
                    organizator.getEmail(),
                    organizator.getSifre(),
                    List.of(new SimpleGrantedAuthority("ROLE_ORGANIZATOR"))
            );
        }

        AdminEntity admin = adminRepository.findByEmail(username).orElse(null);
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                    admin.getEmail(),
                    admin.getSifre(),
                    List.of(new SimpleGrantedAuthority("ADMIN"))
            );
        }

        // Hiçbiri bulunamadıysa
        throw new UsernameNotFoundException("Kullanıcı veya organizatör bulunamadı: " + username);
    }
}
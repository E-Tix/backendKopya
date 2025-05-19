package com.example.demo.Service;

import com.example.demo.Dto.Request.OrgSignUpDto;
import com.example.demo.Dto.Request.SignUpDto;
import com.example.demo.Entity.KullaniciEntity;
import com.example.demo.Entity.OrganizatorEntity;
import com.example.demo.Repository.KullaniciRepository;
import com.example.demo.Repository.OrganizatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    KullaniciRepository kullaniciRepository;
    OrganizatorRepository organizatorRepository;
    JwtService jwtService;
    BCryptPasswordEncoder passwordEncoder;

    public SignUpService(){}
    @Autowired
    public SignUpService(KullaniciRepository kullaniciRepository, OrganizatorRepository organizatorRepository, JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.kullaniciRepository = kullaniciRepository;
        this.organizatorRepository = organizatorRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String signUp(SignUpDto signUpDto) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getSifre());
        KullaniciEntity kullanici = kullaniciRepository.save(new KullaniciEntity(
                signUpDto.getKullaniciAdi(),
                signUpDto.getAdSoyad(),
                encodedPassword,
                signUpDto.getEmail(),
                signUpDto.getTelNo()));
        return jwtService.generateToken(kullanici);
    }

    public String signUp(OrgSignUpDto orgSignUpDto) {
        String encodedPassword = passwordEncoder.encode(orgSignUpDto.getSifre());
        OrganizatorEntity organizator = organizatorRepository.save(new OrganizatorEntity(
                orgSignUpDto.getAdSoyad(),
                orgSignUpDto.getVergiNo(),
                orgSignUpDto.getEmail(),
                encodedPassword,
                orgSignUpDto.getIban(),
                orgSignUpDto.getSirketAdresi(),
                orgSignUpDto.getTckNo(),
                orgSignUpDto.getTelefonNumarasi()));
        return jwtService.generateToken(organizator);
    }
}

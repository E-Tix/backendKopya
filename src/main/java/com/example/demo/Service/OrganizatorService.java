package com.example.demo.Service;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Request.OrganizatorProfiliDto;
import com.example.demo.Dto.Request.OrganizatorProfiliDuzenleDto;
import com.example.demo.Entity.OrganizatorEntity;
import com.example.demo.Repository.OrganizatorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrganizatorService {

    private final OrganizatorRepository organizatorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public OrganizatorService(BCryptPasswordEncoder passwordEncoder, OrganizatorRepository organizatorRepository)
    {
        this.passwordEncoder=passwordEncoder;
        this.organizatorRepository=organizatorRepository;
    }

    public OrganizatorProfiliDuzenleDto getOrganizatorProfiliDto(Long id)
    {
        OrganizatorEntity organizator=organizatorRepository.findByOrganizatorID(id);
        if (organizator==null){
            throw new EntityNotFoundException("organizator bulunamadı");
        }else {
            return new OrganizatorProfiliDuzenleDto(organizator.getAdSoyad(), organizator.getEmail(), organizator.getIban(), organizator.getSirketAdresi(), organizator.getTelefonNumarasi());
        }
    }

    public boolean organizatorProfiliDuzenle(OrganizatorProfiliDuzenleDto organizatorProfiliDuzenleDto,Long id)
    {
        OrganizatorEntity organizator = organizatorRepository.findByOrganizatorID(id);
        if (organizator==null){
            return false;
        }else {
            organizator.setAdSoyad(organizatorProfiliDuzenleDto.getAdSoyad());
            organizator.setEmail(organizatorProfiliDuzenleDto.getEmail());
            organizator.setIban(organizatorProfiliDuzenleDto.getIban());
            organizator.setSirketAdresi(organizatorProfiliDuzenleDto.getSirketAdresi());
            organizator.setTelefonNumarasi(organizatorProfiliDuzenleDto.getTelefonNumarasi());

            organizatorRepository.save(organizator);
            return true;
        }
    }

    public Long findOrganizatorIDByUsername(String username){
        return organizatorRepository.findOrganizatorIDByEmail(username);
    }

    //Hem KullaniciService hem de OrganizatorService'e ekledim çünkü şifre veritabanına haslanmış olarak kaydediliyor
    //Ancak eski şifre ile veritabanındaki şifrenin aynı olup olmadığını frontendten gelen plaintext ile veritabanındaki
    //hashlanmiş şifre ile karşılaştırarak kontrol ediyordu. Bu da her seferinde false dönmesine sebep oluyordu.

    public boolean changePassword(ChangePasswordDto changePasswordDto,Long id)
    {
        OrganizatorEntity organizator=organizatorRepository.findByOrganizatorID(id);
        if (organizator!=null){
            System.out.println("Organizatör bulundu");
            if (passwordEncoder.matches(changePasswordDto.getEskiSifre(), organizator.getSifre()))
            {
                System.out.println("Eski şifre doğru");
                if (changePasswordDto.getYeniSifre().equals(changePasswordDto.getYeniSifreTekrar())) {
                    organizator.setSifre(passwordEncoder.encode(changePasswordDto.getYeniSifre())); //Yeni belirlenen şifrenin tekrar hashlenmesi
                    System.out.println("Yeni şifre ve tekrarı eşleşiyor");
                    organizatorRepository.save(organizator);
                    return true;
                }else {
                    System.out.println("Yeni şifreler eşleşmiyor");
                    return false;
                }
            }else {
                System.out.println("Eski şifre yanlış");
                return false;
            }
        }else {
            System.out.println("Organizatör Bulunamadı");
            return false;
        }
    }

    public OrganizatorProfiliDto getOrganizatorProfili(Long id){
        OrganizatorEntity organizator=organizatorRepository.findByOrganizatorID(id);
        if (organizator==null){
            throw new EntityNotFoundException("organizator bulunamadı");
        }else {
            return new OrganizatorProfiliDto(organizator.getAdSoyad(), organizator.getVergiNo(), organizator.getEmail(), organizator.getIban(), organizator.getSirketAdresi(), organizator.getTckNo(), organizator.getTelefonNumarasi());
        }
    }
}

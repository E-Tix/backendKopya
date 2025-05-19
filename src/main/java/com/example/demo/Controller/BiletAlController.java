package com.example.demo.Controller;

import com.example.demo.Dto.Request.BiletAlDto;
import com.example.demo.Service.BiletAlService;
import com.example.demo.Repository.KullaniciRepository;
import com.example.demo.Entity.KullaniciEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biletAl")
public class BiletAlController {
    private final BiletAlService biletAlService;
    private final KullaniciRepository kullaniciRepository;

    @Autowired
    public BiletAlController(BiletAlService biletAlService, KullaniciRepository kullaniciRepository) {
        this.biletAlService = biletAlService;
        this.kullaniciRepository = kullaniciRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> biletAl(@RequestBody BiletAlDto biletAlDto) {
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        boolean success = biletAlService.biletAl(biletAlDto, userId);
        if(success){
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteBilet")
    public ResponseEntity<Boolean> biletsil(@RequestParam Long biletId)
    {
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        boolean success = biletAlService.biletSil(userId,biletId);
        if(success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        KullaniciEntity kullanici = kullaniciRepository.findByKullaniciAdi(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return kullanici.getKullaniciID();
    }

    protected void checkRole(String requiredRole) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch(r -> r.equals(requiredRole))) {
            throw new AccessDeniedException("Gereken yetki: " + requiredRole);
        }
    }

}
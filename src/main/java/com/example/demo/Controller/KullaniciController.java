package com.example.demo.Controller;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.KullaniciProfiliDto;
import com.example.demo.Dto.Response.BiletDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Entity.BiletEntity;
import com.example.demo.Service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/Profile")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @Autowired
    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @PutMapping("/ChangePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        boolean success = kullaniciService.changePassword(changePasswordDto, userId);
        if (success) {
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getTickets")
    public ResponseEntity<List<BiletDto>> getBiletler() {
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        List<BiletDto> bilet = kullaniciService.getBiletler(userId);
        if(bilet != null) {
            return ResponseEntity.ok(bilet);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserProfile")
    public ResponseEntity<KullaniciProfiliDto> getKullaniciProfili() {
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        KullaniciProfiliDto kp = kullaniciService.getKullaniciProfili(userId);
        if (kp != null) {
            return ResponseEntity.ok(kp);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateUserInfo")
    public ResponseEntity<Boolean> kullaniciProfiliDuzenle(@RequestBody KullaniciProfiliDto kullaniciProfiliDto) {
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        boolean success = kullaniciService.kullaniciProfiliDuzenle(kullaniciProfiliDto, userId);
        if(success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateCity")
    public ResponseEntity<Boolean> kullaniciSehirDuzenle(@RequestBody SehirDto sehirDto){
        checkRole("ROLE_USER");
        Long userId = getCurrentUserId();
        boolean success = kullaniciService.kullaniciSehirDuzenle(userId,sehirDto);
        if (success){
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    private Long getCurrentUserId() {
        // Bu metodu JWT'den ya da SecurityContext'ten ID çıkarmak için ayarlamalısın
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Kullanıcı doğrulanmamış");
        }
        // Sadece username varsa, repository'den ID çekebilirsin:
        String username = auth.getName();
        return kullaniciService.getUserIdByUsername(username); // Bu metodu serviste yazman gerekir
    }

    protected void checkRole(String requiredRole) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch(r -> r.equals(requiredRole))) {
            throw new AccessDeniedException("Gereken yetki: " + requiredRole);
        }
    }

    //commit deneme

}

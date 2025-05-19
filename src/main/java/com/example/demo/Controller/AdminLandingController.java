package com.example.demo.Controller;

import com.example.demo.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Dto.Response.SilinecekBiletDto;
import com.example.demo.Service.AdminLandingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/adminMainPage")
public class AdminLandingController {

    private final AdminLandingService adminLandingService;

    @Autowired
    public AdminLandingController(AdminLandingService adminLandingService, AdminRepository adminRepository)
    {
        this.adminLandingService=adminLandingService;
    }


    public AdminLandingController(AdminLandingService adminLandingService)
    {
        this.adminLandingService = adminLandingService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SilinecekBiletDto>> getSilinecekBiletler() {
        checkRole("ADMIN");
        List<SilinecekBiletDto> liste = adminLandingService.getSilinecekBiletler();
        if (!liste.isEmpty()) {
            return ResponseEntity.ok(liste);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/biletSil")
    public ResponseEntity<Boolean> biletSil(@RequestParam Long biletId) {
        boolean success = adminLandingService.biletSil(biletId);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
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

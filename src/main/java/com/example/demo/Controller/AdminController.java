package com.example.demo.Controller;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Response.AdminProfiliDto;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminService adminService, AdminRepository adminRepository)
    {
        this.adminService=adminService;
        this.adminRepository = adminRepository;
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Boolean> sifreDegistir(@RequestBody ChangePasswordDto changePasswordDto) {
        checkRole("ADMIN");
        Long adminId = getCurrentAdminId();
        boolean success = adminService.sifreDegistir(adminId, changePasswordDto);
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/getAdminProfile")
    public ResponseEntity<AdminProfiliDto> getAdminProfili() {
        checkRole("ADMIN");
        Long adminId = getCurrentAdminId();
        AdminProfiliDto dto = adminService.getAdminProfili(adminId);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Long getCurrentAdminId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Kullanıcı doğrulanmamış");
        }

        String email = auth.getName(); // Eğer JWT'de "subject" olarak e-mail varsa
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin bulunamadı"))
                .getAdminID();
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

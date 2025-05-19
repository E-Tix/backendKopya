package com.example.demo.Controller;

import com.example.demo.Dto.Request.ChangePasswordDto;
import com.example.demo.Dto.Request.OrganizatorProfiliDto;
import com.example.demo.Dto.Request.OrganizatorProfiliDuzenleDto;
import com.example.demo.Entity.OrganizatorEntity;
import com.example.demo.Repository.OrganizatorRepository;
import com.example.demo.Service.OrganizatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orgProfile")
public class OrganizatorController {

    private final OrganizatorService organizatorService;
    private final OrganizatorRepository organizatorRepository;

    @Autowired
    public OrganizatorController(OrganizatorService organizatorService, OrganizatorRepository organizatorRepository) {
        this.organizatorService = organizatorService;
        this.organizatorRepository = organizatorRepository;
    }

    @PutMapping("/ChangePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        checkRole("ROLE_ORGANIZATOR");
        Long id = getCurrentOrganizatorId();
        boolean success = organizatorService.changePassword(changePasswordDto, id);
        if (success) {
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getOrganizatorProfile")
    public ResponseEntity<OrganizatorProfiliDto> getOrganizatorProfili() {
        checkRole("ROLE_ORGANIZATOR");
        Long id = getCurrentOrganizatorId();
        OrganizatorProfiliDto organizatorProfiliDto = organizatorService.getOrganizatorProfili(id);
        if (organizatorProfiliDto != null) {
            return ResponseEntity.ok(organizatorProfiliDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/updateOrganizatorInfo")
    public ResponseEntity<OrganizatorProfiliDuzenleDto> getOrganizatorProfiliDuzenleDto(){
        checkRole("ROLE_ORGANIZATOR");
        Long id= getCurrentOrganizatorId();
        OrganizatorProfiliDuzenleDto organizatorProfiliDuzenleDto = organizatorService.getOrganizatorProfiliDto(id);
        if (organizatorProfiliDuzenleDto != null) {
            return ResponseEntity.ok(organizatorProfiliDuzenleDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateOrganizatorInfo/save")
    public ResponseEntity<Boolean> organizatorProfiliDuzenle(@RequestBody OrganizatorProfiliDuzenleDto organizatorProfiliDuzenleDto) {
        checkRole("ROLE_ORGANIZATOR");
        Long id = getCurrentOrganizatorId();
        boolean success = organizatorService.organizatorProfiliDuzenle(organizatorProfiliDuzenleDto, id);
        if(success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private Long getCurrentOrganizatorId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Kullanıcı doğrulanmamış");
        }

        String email = auth.getName(); // Eğer JWT'de "subject" olarak e-mail varsa
        return organizatorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizatör bulunamadı"))
                .getOrganizatorID();
    }

    //organizatörün kendisine ait etkinlikleri id ile çekmesi için
    //Bu kısmı henüz kullanmadım. Bugün bakacağım gerekirse değiştiririm
    @GetMapping("/get-id-by-email/{email}")
    public ResponseEntity<Long> getIdByEmail(@PathVariable String email) {
        checkRole("ROLE_ORGANIZATOR");
        Optional<OrganizatorEntity> organizator = organizatorRepository.findByEmail(email);
        return organizator.map(o -> ResponseEntity.ok(o.getOrganizatorID()))
                .orElse(ResponseEntity.notFound().build());
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

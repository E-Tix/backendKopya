package com.example.demo.Controller;

import com.example.demo.Dto.KullaniciProfiliDto;
import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Service.LandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mainPage")
public class LandingController {
    private final LandingService landingService;

    @Autowired
    public LandingController(LandingService landingService) {
        this.landingService = landingService;
    }

    @GetMapping("/getSehir")//bir alttakine entegre edilebilir
    public ResponseEntity<List<SehirDto>> getSehirler(){
        checkRole("ROLE_USER");
        List<SehirDto> sehirDtos = landingService.getSehirler();
        if (!sehirDtos.isEmpty()){
            return ResponseEntity.ok(sehirDtos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Page<EtkinlikEntity>>  getEtkinlikler(
            @RequestParam(required = false) String etkinlikTurAdi,
            @RequestParam(required = false) String sehirAdi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        checkRole("ROLE_USER");
        Page<EtkinlikEntity> etkinlikEntities = landingService.getEtkinlikler(etkinlikTurAdi, sehirAdi, page, size);
        if (etkinlikEntities.getTotalElements() > 0) {
            return ResponseEntity.ok(etkinlikEntities);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EtkinlikDetayDto> getEtkinlik(@PathVariable Long eventId) {
        checkRole("ROLE_USER");
        EtkinlikDetayDto etkinlikDetayDto = landingService.getEtkinlik(eventId); // sadece seansList ve bu seansların salonu dönsün
        if (etkinlikDetayDto != null) {
            return ResponseEntity.ok(etkinlikDetayDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sinema/{eventId}")
    public ResponseEntity<SinemaDetayDto> getSinema(@PathVariable Long eventId)
    {
        checkRole("ROLE_USER");
        SinemaDetayDto sinemaDetayDto = landingService.getSinema(eventId);
        if (sinemaDetayDto != null) {
            return ResponseEntity.ok(sinemaDetayDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<AramaDto>> etkinlikAra(@RequestParam String arananEtkinlikAdi)
    {
        checkRole("ROLE_USER");
        List<AramaDto> aramaDtos = landingService.etkinlikAra(arananEtkinlikAdi);
        if (!aramaDtos.isEmpty()) {
            return ResponseEntity.ok(aramaDtos);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/koltukDurumu/{seansId}")
    public ResponseEntity<List<KoltukDurumDto>> getKoltukDurumu(@PathVariable Long seansId) {
        checkRole("ROLE_USER");
        List<KoltukDurumDto> koltukDurumDtos = landingService.getKoltukDurumu(seansId);
        if (!koltukDurumDtos.isEmpty()) {
            return ResponseEntity.ok(koltukDurumDtos);
        }else{
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

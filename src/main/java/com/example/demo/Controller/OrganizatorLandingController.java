package com.example.demo.Controller;

import com.example.demo.Dto.Request.EtkinlikEkleDto;
import com.example.demo.Dto.Request.EtkinlikGuncelleDto;
import com.example.demo.Dto.Request.SinemaEkleDto;
import com.example.demo.Dto.Request.SinemaGuncelleDto;
import com.example.demo.Dto.Response.EtkinlikForOrgDetayDto;
import com.example.demo.Dto.Response.EtkinlikForOrgDto;
import com.example.demo.Dto.Response.SalonDto;
import com.example.demo.Dto.Response.SehirDto;
import com.example.demo.Dto.Response.*;
import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Repository.OrganizatorRepository;
import com.example.demo.Service.OrganizatorLandingService;
import com.example.demo.Service.SinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizatorMainPage")
public class OrganizatorLandingController {

    private final OrganizatorLandingService organizatorLandingService;
    private final OrganizatorRepository organizatorRepository;
    private final SinemaService sinemaService;

    @Autowired
    public OrganizatorLandingController(OrganizatorLandingService organizatorLandingService, OrganizatorRepository organizatorRepository ,SinemaService sinemaService)
    {
        this.organizatorLandingService = organizatorLandingService;
        this.organizatorRepository = organizatorRepository;
        this.sinemaService = sinemaService;
    }

    @PostMapping("/addEvent/save")
    public ResponseEntity<Boolean> etkinlikEkle(@RequestBody EtkinlikEkleDto etkinlikEkleDto){
        checkRole("ROLE_ORGANIZATOR");
        //orgid changePassworddaki gibi alınacak
        Long id = getCurrentOrganizatorId();
        System.out.println("id" + id);
        EtkinlikEntity etkinlikEntitylik = organizatorLandingService.etkinlikEkle(etkinlikEkleDto,id);
        if (etkinlikEntitylik != null){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/getSalonsForSehir")
    public ResponseEntity<List<SalonDto>> getSalonsForSehir(@RequestBody SehirDto sehirDto)
    {
        checkRole("ROLE_ORGANIZATOR");
        List<SalonDto> salonDtos = organizatorLandingService.getSalonsForSehir(sehirDto);
        if (salonDtos != null){
            return ResponseEntity.ok(salonDtos);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getSehirs")
    public ResponseEntity<List<SehirDto>> getSehirs()
    {
        checkRole("ROLE_ORGANIZATOR");
        List<SehirDto> sehirDtos = organizatorLandingService.getSehirs();
        if (sehirDtos != null){
            return ResponseEntity.ok(sehirDtos);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /*@GetMapping("/addEvent")
    public List<EtkinlikTurEntity> getEtkinlikTursForAddEvent(){
        return organizatorLandingService.getEtkinlikTursAndSalonsForAddAndUpdateEvent();
    }*/

    @GetMapping("/updateEvent/{eventId}")
    public ResponseEntity<EtkinlikGuncelleDto> getEtkinlikGuncelle(@PathVariable Long eventId){
        checkRole("ROLE_ORGANIZATOR");
        Long id = getCurrentOrganizatorId();
        EtkinlikGuncelleDto etkinlikGuncelleDto = organizatorLandingService.getEtkinlikGuncelleDto(id,eventId);
        if (etkinlikGuncelleDto != null){
            return ResponseEntity.ok(etkinlikGuncelleDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateEvent/save")
    public ResponseEntity<Boolean> etkinlikGuncelle(@RequestBody EtkinlikGuncelleDto etkinlikGuncelleDto){
        checkRole("ROLE_ORGANIZATOR");
        Long id = getCurrentOrganizatorId();
        EtkinlikEntity etkinlikEntity= organizatorLandingService.etkinlikGuncelle(id,etkinlikGuncelleDto);
        if (etkinlikEntity != null){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity<Boolean> etkinlikSil(@PathVariable Long eventId)//pathten id alma dtodan al
    {
        checkRole("ROLE_ORGANIZATOR");
        boolean success = organizatorLandingService.etkinlikSil(eventId);
        if (success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getEtkinlik/{eventId}")
    public ResponseEntity<EtkinlikForOrgDetayDto> getEtkinlik(@PathVariable Long eventId)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        EtkinlikForOrgDetayDto etkinlikForOrgDetayDto = organizatorLandingService.getEtkinlik(eventId);
        if (etkinlikForOrgDetayDto != null){
            return ResponseEntity.ok(etkinlikForOrgDetayDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Page<EtkinlikForOrgDto>> getEtkinlikler(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        Page<EtkinlikForOrgDto> etkinlikForOrgDtos = organizatorLandingService.getEtkinlikler(orgId, page, size);
        if (etkinlikForOrgDtos != null){
            return ResponseEntity.ok(etkinlikForOrgDtos);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addCinema/save")
    public ResponseEntity<Boolean> sinemaEkle(@RequestBody SinemaEkleDto sinemaEkleDto)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        boolean success = sinemaService.sinemaEkle(orgId,sinemaEkleDto);
        if (success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteCinema")
    public ResponseEntity<Boolean> sinemaSil(@RequestParam Long sinemaId)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        boolean success = sinemaService.sinemaSil(orgId,sinemaId);
        if (success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //sinema güncelleme formunun doldurulması için.
    @GetMapping("/updateCinema")
    public ResponseEntity<SinemaGuncelleDto> getSinemaGuncelleDto(@RequestParam Long sinemaId)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        SinemaGuncelleDto sinemaGuncelleDto = sinemaService.getSinemaGuncelleDto(orgId,sinemaId);
        if (sinemaGuncelleDto != null){
            return ResponseEntity.ok(sinemaGuncelleDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("updateCinema/save")
    public ResponseEntity<Boolean> sinemaGuncelle(@RequestBody SinemaGuncelleDto sinemaGuncelleDto)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        boolean success = sinemaService.sinemaGuncelle(orgId,sinemaGuncelleDto);
        if (success){
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getCinema")
    public ResponseEntity<SinemaForOrgDetayDto> getSinema(@RequestParam Long sinemaId)
    {
        checkRole("ROLE_ORGANIZATOR");
        Long orgId = getCurrentOrganizatorId();
        SinemaForOrgDetayDto sinemaForOrgDetayDto = sinemaService.getSinema(orgId,sinemaId);
        if (sinemaForOrgDetayDto != null){
            return ResponseEntity.ok(sinemaForOrgDetayDto);
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

        System.out.println("Auth name: " + auth.getName()); //hata bulma amaçlı eklendiler
        System.out.println("Authorities: " + auth.getAuthorities());

        return organizatorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Organizatör bulunamadı"))
                .getOrganizatorID();
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
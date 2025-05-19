package com.example.demo.Controller;

import com.example.demo.Dto.Response.KoltukDurumDto;
import com.example.demo.Entity.KoltukEntity;
import com.example.demo.Service.KoltukService;
import com.example.demo.Service.LandingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/koltuklar")
public class KoltukController {

    private final KoltukService koltukService;
    private final LandingService landingService;

    public KoltukController(KoltukService koltukService, LandingService landingService) {
        this.koltukService = koltukService;
        this.landingService = landingService;
    }

    @GetMapping
    public ResponseEntity<List<KoltukEntity>> getKoltuklarBySalon(@RequestParam Long salonId) {
        List<KoltukEntity> koltuklar = koltukService.getKoltuklarBySalonId(salonId);
        return ResponseEntity.ok(koltuklar);
    }

    //Dolu koltukları almak için
    @GetMapping("/dolu")
    public ResponseEntity<List<KoltukDurumDto>> getDoluKoltuklar(@RequestParam Long seansId) {
        List<KoltukDurumDto> doluKoltuklar = landingService.getKoltukDurumu(seansId);
        return ResponseEntity.ok(doluKoltuklar);
    }
}
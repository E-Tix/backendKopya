package com.example.demo.Controller;

import com.example.demo.Repository.EtkinlikTurRepository;
import com.example.demo.Repository.SalonRepository;
import com.example.demo.Entity.EtkinlikTurEntity;
import com.example.demo.Entity.SalonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/veri")
@CrossOrigin(origins = "http://localhost:3000")
public class SalonSeansTurController {

    private final EtkinlikTurRepository etkinlikTurRepository;

    private final SalonRepository salonRepository;

    @Autowired
    public SalonSeansTurController(EtkinlikTurRepository etkinlikTurRepository, SalonRepository salonRepository) {
        this.etkinlikTurRepository = etkinlikTurRepository;
        this.salonRepository = salonRepository;
    }

    @GetMapping("/etkinlik-turleri")
    public ResponseEntity<List<EtkinlikTurEntity>> getEtkinlikTurleri() {
        return ResponseEntity.ok(etkinlikTurRepository.findAll());
    }

    @GetMapping("/salonlar")
    public ResponseEntity<List<SalonEntity>> getSalonlar() {
        return ResponseEntity.ok(salonRepository.findAll());
    }
}
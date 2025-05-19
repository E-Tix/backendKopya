package com.example.demo.Controller;

import com.example.demo.Service.SehirService;
import com.example.demo.Dto.Response.SehirDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sehir")
public class SehirController {

    //Hem headerda hem de etkinlikte kullanmak için burada endpoint ekledim
    private final SehirService sehirService;

    @Autowired
    public SehirController(SehirService sehirService) {
        this.sehirService = sehirService;
    }

    @GetMapping("/sehirler")
    public ResponseEntity<List<SehirDto>> getAllSehirler() {
        List<SehirDto> sehirDtos = sehirService.getAllSehirler().stream()
                .map(sehir -> new SehirDto(sehir.getPlakaKodu(), sehir.getSehirAdi()))
                .collect(Collectors.toList());
        if (!sehirDtos.isEmpty()) {
            return ResponseEntity.ok(sehirDtos);
        }else {
            return ResponseEntity.noContent().build();
        }
    }

}

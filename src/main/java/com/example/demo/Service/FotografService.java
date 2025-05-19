package com.example.demo.Service;

import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Repository.EtkinlikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FotografService {

    @Autowired
    private EtkinlikRepository etkinlikRepository;

    // Ana dizindeki uploads klasörüne dosya kaydeder
    private final Path uploadPath = Paths.get("uploads");

    public void kapakFotografYukle(Long id, MultipartFile file) throws IOException {
        EtkinlikEntity etkinlik = etkinlikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etkinlik bulunamadı: " + id));

        // uploads klasörü yoksa oluştur
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Eski kapak fotoğrafını sil
        String eskiDosyaAdi = etkinlik.getKapakFotografi();
        if (eskiDosyaAdi != null) {
            Path eskiDosyaPath = uploadPath.resolve(eskiDosyaAdi);
            Files.deleteIfExists(eskiDosyaPath);
        }

        // Yeni dosya adını oluştur
        String yeniDosyaAdi = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path hedefPath = uploadPath.resolve(yeniDosyaAdi);

        // Dosyayı kopyala
        Files.copy(file.getInputStream(), hedefPath, StandardCopyOption.REPLACE_EXISTING);

        // Etkinliği güncelle
        etkinlik.setKapakFotografi(yeniDosyaAdi);
        etkinlikRepository.save(etkinlik);
    }

    public Path getKapakFotografPath(String dosyaAdi) {
        return uploadPath.resolve(dosyaAdi);
    }
}

package com.example.demo.Service;

import com.example.demo.Entity.EtkinlikEntity;
import com.example.demo.Entity.EtkinlikSalonSeansEntity;
import com.example.demo.Entity.SeansEntity;
import com.example.demo.Repository.EtkinlikRepository;
import com.example.demo.Repository.EtkinlikSalonSeansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class EtkinlikService {
    private final EtkinlikRepository etkinlikRepo;
    private final TaskScheduler scheduler;
    private final Map<Long, ScheduledFuture<?>> futures = new ConcurrentHashMap<>();
    private final EtkinlikSalonSeansRepository etkinlikSalonRepo;

    @Autowired
    public EtkinlikService(EtkinlikRepository repo, @Qualifier("taskScheduler") TaskScheduler scheduler, EtkinlikSalonSeansRepository etkinlikSalonRepo) {
        this.etkinlikRepo = repo;
        this.scheduler     = scheduler;
        this.etkinlikSalonRepo = etkinlikSalonRepo;
    }



    public void scheduleTarihiGecti(Long etkinlikId) {
        EtkinlikEntity e = etkinlikRepo.findById(etkinlikId).orElseThrow();
        // 1. max seans başlangıç zamanı
        Timestamp maxSeansStart = etkinlikSalonRepo.findEtkinlikSalonSeansEntitiesByEtkinlik(e).stream()
                .map(EtkinlikSalonSeansEntity::getSeans)
                .map(SeansEntity::getTarih)
                .max(Timestamp::compareTo)
                .orElseThrow();
        Instant bitisInstant = maxSeansStart.toInstant()
                .plus(Duration.ofMinutes(e.getEtkinlikSuresi()));

        Date runDate = Date.from(bitisInstant);
        Date now     = new Date();

        // Eğer zaten geçmişse, hemen expire et
        if (runDate.before(now)) {
            System.out.printf("Etkinlik %d için zaten bitiş zamanı geçmiş, hemen expire ediliyor%n", etkinlikId);
            markAsExpired(etkinlikId);
            return;
        }

        // Önceki task varsa iptal et
        ScheduledFuture<?> prev = futures.remove(etkinlikId);
        if (prev != null) {
            prev.cancel(false);
        }

        // Yeni zamanlı task’ı schedule et
        System.out.printf("Etkinlik %d için task %s tarihinde schedule edildi%n", etkinlikId, runDate);
        ScheduledFuture<?> future = scheduler.schedule(
                () -> {
                    System.out.printf("Etkinlik %d için scheduled task tetiklendi, expire ediliyor%n", etkinlikId);
                    markAsExpired(etkinlikId);
                },
                runDate
        );
        futures.put(etkinlikId, future);
    }

    @Transactional
    public void markAsExpired(Long etkinlikId) {
        EtkinlikEntity e = etkinlikRepo.findById(etkinlikId).orElseThrow();
        if (!e.isTarihiGectiMi()) {
            e.setTarihiGectiMi(true);
            etkinlikRepo.save(e);
            System.out.printf("Etkinlik %d tarihiGectiMi=true olarak güncellendi%n", etkinlikId);
        }
    }

    public List<Long> findAllIds() {
        return etkinlikRepo.findAllIds();
    }
}

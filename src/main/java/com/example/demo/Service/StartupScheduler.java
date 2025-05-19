package com.example.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupScheduler {
    private final EtkinlikService etkinlikService;
    @Autowired
    public StartupScheduler(EtkinlikService es) {
        this.etkinlikService = es;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void scheduleAllEvents() {
        etkinlikService.findAllIds()
                .forEach(etkinlikService::scheduleTarihiGecti);
    }
}
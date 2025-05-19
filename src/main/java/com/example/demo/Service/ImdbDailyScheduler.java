package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ImdbDailyScheduler {

    private final ImdbService imdbService;

    @Autowired
    public ImdbDailyScheduler(ImdbService imdbService) {
        this.imdbService = imdbService;
    }

    @Scheduled(cron = "0 0 21 * * *", zone = "Europe/Istanbul")
    public void fetchAllNewEventsFromImdb() {
        imdbService.imdbPuanCek();
    }
}
package com.example.demo.Service;

import com.example.demo.Entity.SinemaEntity;
import com.example.demo.Repository.SinemaRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Service
public class ImdbService {

    private static final Logger logger = LoggerFactory.getLogger(ImdbService.class);
    private final SinemaRepository sinemaRepository;

    public ImdbService(SinemaRepository sinemaRepository) {
        this.sinemaRepository = sinemaRepository;
    }

    /**
     * Her gün saat 21:00'da çalışacak şekilde zamanlanmış görev.
     */
    @Scheduled(cron = "0 0 21 * * *")
    public void scheduledImdbPuanCek() {
        imdbPuanCek();
    }

    public void imdbPuanCek() {
        List<SinemaEntity> sinemalar = sinemaRepository.findAll();
        if (sinemalar.isEmpty()) {
            logger.info("Güncelleme için sinema kaydı bulunamadı.");
            return;
        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox");
        options.addArguments("user-agent=Mozilla/5.0");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = null;
        try {
            driver = new ChromeDriver(options);
            // Genel implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            for (SinemaEntity s : sinemalar) {
                try {
                    String filmAdi = s.getEtkinlik().getEtkinlikAdi();
                    String searchUrl = "https://www.imdb.com/find?q=" + URLEncoder.encode(filmAdi, StandardCharsets.UTF_8);
                    driver.get(searchUrl);

                    WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("a.ipc-metadata-list-summary-item__t[href^='/title/tt']")
                    ));
                    String href = firstResult.getAttribute("href");
                    String imdbID = href.split("/")[4];

                    driver.get("https://www.imdb.com/title/" + imdbID);
                    WebElement ratingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("span.sc-d541859f-1")
                    ));

                    String sRating = ratingElement.getText()
                            .replace(",", ".")
                            .trim();
                    float rating = Float.parseFloat(sRating);

                    s.setImdbPuani(rating);
                    sinemaRepository.save(s);
                    logger.info("Güncellendi: {} → {}", filmAdi, rating);

                } catch (Exception eInner) {
                    logger.error("Sinema [{}] için puan çekme hatası:", s.getEtkinlik().getEtkinlikAdi(), eInner);
                }
            }

        } catch (Exception e) {
            logger.error("IMDb puan çekme işlemi başlatılamadı:", e);

        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception eQuit) {
                    logger.warn("WebDriver kapatılırken hata:", eQuit);
                }
            }
        }
    }
}

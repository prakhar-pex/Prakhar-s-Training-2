package com.example.WeatherApp.controller;


import com.example.WeatherApp.entity.WeatherForecast;
import com.example.WeatherApp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/forecast/{city}")
    public ResponseEntity<List<WeatherForecast>> getForecast(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.getWeeklyForecast(city));
    }

    @GetMapping("/compare")
    public ResponseEntity<List<WeatherForecast>> compareCities(
            @RequestParam String city1,
            @RequestParam String city2) {
        return ResponseEntity.ok(weatherService.compareCities(city1, city2));
    }
}
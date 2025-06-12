package com.example.June._1.Weather.App.controller;

import com.example.June._1.Weather.App.dto.WeatherResponseDto;
import com.example.June._1.Weather.App.dto.WeatherComparisonDto;
import com.example.June._1.Weather.App.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public WeatherResponseDto getWeather(@PathVariable String city) {
        return weatherService.getForecast(city);
    }

    @GetMapping("/compare")
    public WeatherComparisonDto compareCities(@RequestParam String city1, @RequestParam String city2) {
        return weatherService.compareForecasts(city1, city2);
    }
}

package com.example.June._1.Weather.App.service;

import com.example.June._1.Weather.App.dto.WeatherResponseDto;
import com.example.June._1.Weather.App.dto.WeatherComparisonDto;
import com.example.June._1.Weather.App.model.WeatherForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private static final Map<String, double[]> cityCoordinates = Map.of(
            "london", new double[]{51.5072, -0.1276},
            "paris", new double[]{48.8566, 2.3522},
            "new york", new double[]{40.7128, -74.0060}
    );

    public WeatherResponseDto getForecast(String city) {
        double[] coordinates = cityCoordinates.get(city.toLowerCase());
        if (coordinates == null) throw new RuntimeException("City not found!");

        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&daily=temperature_2m_max,precipitation_sum,wind_speed_10m_max&timezone=auto",
                coordinates[0], coordinates[1]
        );

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, List<Object>> daily = (Map<String, List<Object>>) response.get("daily");

        List<WeatherForecast> forecasts = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            WeatherForecast f = new WeatherForecast();
            f.setDate(LocalDate.parse((String) daily.get("time").get(i)));
            f.setTemperature((Double) daily.get("temperature_2m_max").get(i));
            f.setPrecipitation((Double) daily.get("precipitation_sum").get(i));
            f.setWindSpeed((Double) daily.get("wind_speed_10m_max").get(i));
            forecasts.add(f);
        }

        WeatherResponseDto dto = new WeatherResponseDto();
        dto.setCity(city);
        dto.setForecasts(forecasts);
        return dto;
    }

    public WeatherComparisonDto compareForecasts(String city1, String city2) {
        WeatherResponseDto forecast1 = getForecast(city1);
        WeatherResponseDto forecast2 = getForecast(city2);

        List<String> comparison = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            WeatherForecast f1 = forecast1.getForecasts().get(i);
            WeatherForecast f2 = forecast2.getForecasts().get(i);

            String result = String.format(
                    "On %s: TempDiff=%.1f°C, WindDiff=%.1f m/s, RainDiff=%.1f mm",
                    f1.getDate(),
                    f1.getTemperature() - f2.getTemperature(),
                    f1.getWindSpeed() - f2.getWindSpeed(),
                    f1.getPrecipitation() - f2.getPrecipitation()
            );

            comparison.add(result);
        }

        WeatherComparisonDto dto = new WeatherComparisonDto();
        dto.setCity1(city1);
        dto.setCity2(city2);
        dto.setComparisonResults(comparison);
        return dto;
    }
}

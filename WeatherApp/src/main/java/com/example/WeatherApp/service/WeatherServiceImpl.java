package com.example.WeatherApp.service;

import com.example.WeatherApp.entity.WeatherForecast;
import com.example.WeatherApp.dto.OpenMeteoResponse;
import com.example.WeatherApp.entity.City;
import com.example.WeatherApp.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final CityRepository cityRepository;
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&daily=temperature_2m_max,temperature_2m_min,precipitation_sum,windspeed_10m_max&timezone=auto";

    @Override
    public List<WeatherForecast> getWeeklyForecast(String cityName) {
        City city = cityRepository.getCityByName(cityName);
        OpenMeteoResponse response = restTemplate.getForObject(
                API_URL,
                OpenMeteoResponse.class,
                city.getLatitude(),
                city.getLongitude()
        );

        return mapResponseToForecasts(response);
    }

    @Override
    public List<WeatherForecast> compareCities(String city1, String city2) {
        List<WeatherForecast> forecasts1 = getWeeklyForecast(city1);
        List<WeatherForecast> forecasts2 = getWeeklyForecast(city2);

        // Simple comparison - just return both forecasts
        List<WeatherForecast> comparison = new ArrayList<>();
        comparison.addAll(forecasts1);
        comparison.addAll(forecasts2);

        return comparison;
    }

    private List<WeatherForecast> mapResponseToForecasts(OpenMeteoResponse response) {
        List<WeatherForecast> forecasts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < response.getDaily().getTime().size(); i++) {
            WeatherForecast forecast = new WeatherForecast();
            forecast.setDate(LocalDate.parse(response.getDaily().getTime().get(i), formatter));
            forecast.setMaxTemperature(response.getDaily().getTemperatureMax().get(i));
            forecast.setMinTemperature(response.getDaily().getTemperatureMin().get(i));
            forecast.setPrecipitation(response.getDaily().getPrecipitation().get(i));
            forecast.setWindSpeed(response.getDaily().getWindSpeed().get(i));

            forecasts.add(forecast);
        }

        return forecasts;
    }
}
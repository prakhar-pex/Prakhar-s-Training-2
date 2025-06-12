package com.example.June._1.Weather.App.dto;
import com.example.June._1.Weather.App.model.WeatherForecast;
import java.util.List;

public class WeatherResponseDto {
    private String city;
    private List<WeatherForecast> forecasts;

    // Getters and Setters

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<WeatherForecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<WeatherForecast> forecasts) {
        this.forecasts = forecasts;
    }
}

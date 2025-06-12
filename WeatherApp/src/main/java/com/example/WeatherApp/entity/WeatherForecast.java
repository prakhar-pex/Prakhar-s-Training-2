package com.example.WeatherApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {
    private LocalDate date;
    private double maxTemperature;
    private double minTemperature;
    private double precipitation;
    private double humidity;
    private double windSpeed;
}

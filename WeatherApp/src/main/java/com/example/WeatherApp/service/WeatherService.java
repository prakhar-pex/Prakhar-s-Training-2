package com.example.WeatherApp.service;

import com.example.WeatherApp.entity.WeatherForecast;

import java.util.List;

public interface WeatherService {
    List<WeatherForecast> getWeeklyForecast(String city);
    List<WeatherForecast> compareCities(String city1, String city2);
}
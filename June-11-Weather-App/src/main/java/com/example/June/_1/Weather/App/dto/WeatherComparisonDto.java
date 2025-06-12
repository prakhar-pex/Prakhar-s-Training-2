package com.example.June._1.Weather.App.dto;


import java.util.List;

public class WeatherComparisonDto {
    private String city1;
    private String city2;
    private List<String> comparisonResults;

    // Getters and Setters

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public List<String> getComparisonResults() {
        return comparisonResults;
    }

    public void setComparisonResults(List<String> comparisonResults) {
        this.comparisonResults = comparisonResults;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }
}

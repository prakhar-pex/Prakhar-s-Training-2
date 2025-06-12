package com.example.WeatherApp.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenMeteoResponse {
    private Daily daily;

    @Data
    public static class Daily {
        private List<String> time;

        @JsonProperty("temperature_2m_max")
        private List<Double> temperatureMax;

        @JsonProperty("temperature_2m_min")
        private List<Double> temperatureMin;

        @JsonProperty("precipitation_sum")
        private List<Double> precipitation;

        @JsonProperty("windspeed_10m_max")
        private List<Double> windSpeed;
    }
}
